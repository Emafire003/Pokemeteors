package me.emafire003.dev.pokemeteors.entity;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import me.emafire003.dev.ohmymeteors.OhMyMeteors;
import me.emafire003.dev.ohmymeteors.config.Config;
import me.emafire003.dev.ohmymeteors.entities.MeteorProjectileEntity;
import me.emafire003.dev.ohmymeteors.util.MeteorSizeClass;
import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.util.PokemeteorUtils;
import me.emafire003.dev.structureplacerapi.StructurePlacerAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static me.emafire003.dev.pokemeteors.util.PokemeteorUtils.METEOR_STRUCTURES;

public class PokeMeteorEntity extends MeteorProjectileEntity {

    /// The position at which the meteor will end up
    protected Vec3 targetPos = Vec3.ZERO;
    protected PokemonEntity spawnedPokemon;
    /// If the meteor is a simple spawn meteor no structure will be generated, the pokemon will be the only thing spawning
    protected boolean simpleSpawn = false;

    public PokeMeteorEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public PokeMeteorEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level world, Vec3 targetPos, PokemonEntity spawnedPokemon) {
        super(entityType, world);
        this.targetPos = targetPos;
        this.spawnedPokemon = spawnedPokemon;
    }

    public PokemonEntity getSpawnedPokemon() {
        return spawnedPokemon;
    }

    public void setSpawnedPokemon(PokemonEntity spawnedPokemon) {
        this.spawnedPokemon = spawnedPokemon;
    }

    public Vec3 getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(Vec3 targetPos) {
        this.targetPos = targetPos;
    }


    /**  the filter is used to only check some subfolders for that specific mon.
     * For example if it's "minior" will only check "small/minior/" folder for the structures, or "big/minior/" etc
     */
    @Override
    public ResourceLocation getStructureToPlace(MeteorSizeClass sizeClass, String filter){
        AtomicBoolean hasSpecial = new AtomicBoolean(false);

        if(METEOR_STRUCTURES.isEmpty() && !this.level().isClientSide()){
            PokemeteorUtils.reInitStructures((ServerLevel) this.level());
        }

        //In case there was a problem and the only meteor spawnable is that one
        if(METEOR_STRUCTURES.size() == 1 && METEOR_STRUCTURES.getFirst().getPath().equals("error")){
            return METEOR_STRUCTURES.getFirst();
        }


        List<ResourceLocation> structs = METEOR_STRUCTURES.stream().filter(identifier -> {

            if(!identifier.getPath().startsWith(sizeClass.getSerializedName())){
                return false;
            }

            //also checks to see that it has the filter
            if((filter != null && !filter.isEmpty()) && !identifier.getPath().startsWith(sizeClass.getSerializedName()+"/"+filter)){
                return false;
            }

            if (!hasSpecial.get()) { //saves on checks
                //This allows me to see if this size has at least a special meteor
                if (identifier.getPath().startsWith(sizeClass.getSerializedName()+"/special")) {
                    hasSpecial.set(true);
                    return true;
                }
            }

            return true;
        }).toList();

        if (structs.isEmpty()){
            if(filter != null && filter.isEmpty()){
                PokemeteorsCommon.LOGGER.error("The list of structures for size class '{}' is empty! Check that your structures are valid ones!", sizeClass.getSerializedName());
            }else {
                PokemeteorsCommon.LOGGER.error("The list of structures for size class '{}' and filter '{}'is empty! Check that your structures are valid ones!", sizeClass.getSerializedName(), filter);
            }

            structs = List.of(PokemeteorsCommon.getIdentifier("error"));
        }

        ResourceLocation structure_id = structs.get(this.level().getRandom().nextIntBetweenInclusive(0,structs.size()-1));
        //This is to prevent special structures from spawning "before" they should
        while(structure_id.getPath().startsWith(sizeClass.getSerializedName()+"/special")){
            structure_id = structs.get(this.getRandom().nextIntBetweenInclusive(0,structs.size()-1));
        }

        //If there is at least a special meteor structure, and the chance is hit, the structs list should only have those
        if(hasSpecial.get()){
            int i = random.nextIntBetweenInclusive(0, Config.SPECIAL_METEORS_CHANCE);
            if(i == 1){
                List<ResourceLocation> specials = structs.stream().filter(id -> id.getPath().startsWith(sizeClass.getSerializedName()+"/special")).toList();
                structure_id = specials.get(this.getRandom().nextIntBetweenInclusive(0,specials.size()-1));
            }
        }
        return structure_id;
    }

    //To avoid getting a null
    @Override
    public StructurePlacerAPI getPlacer(MeteorSizeClass sizeClass, String filter) {
        int og_size = this.getSize();
        if(this.getSize() < 2){
            og_size = this.getSize();
            this.setSize(3);
        }
        StructurePlacerAPI placer = super.getPlacer(sizeClass, filter);
        this.setSize(og_size);
        return placer;
    }

    @Override
    public void detonateWithStructure() {
        if(isSimpleSpawn()){
            this.detonateSimple();
        }else{
            super.detonateWithStructure();
        }
    }

    @Override
    public void detonateSimple() {
        super.detonateSimple();
        if(isSimpleSpawn()){
            BlockPos spawnPos = this.blockPosition();
            while(level().getBlockState(spawnPos).isAir()){
                spawnPos = spawnPos.below();
            }
            this.spawnPokemon(spawnPos.above());
            if(level().getBlockState(spawnPos.above()).getBlock().equals(Blocks.FIRE)){
                level().setBlockAndUpdate(spawnPos.above(), Blocks.AIR.defaultBlockState());
            }
        }

    }

    @Override
    public StructurePlacerAPI getPlacer() {
       StructurePlacerAPI placer =
                new StructurePlacerAPI((WorldGenLevel) this.level(), ResourceLocation.fromNamespaceAndPath(OhMyMeteors.MOD_ID, "error"), this.blockPosition(), Mirror.NONE, Rotation.NONE, false, 1f, getOffset(getSizeClass(), ResourceLocation.fromNamespaceAndPath(OhMyMeteors.MOD_ID, "error")));

       boolean aspectFound = false;
        //First check if there is a unique meteor for that specific variant
        if(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getAspectUniqueMeteor(this.spawnedPokemon) != null){
            AtomicReference<String> chosen_aspect_structure = new AtomicReference<>("");
            spawnedPokemon.getAspects().forEach( (aspect) -> {
                if(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getAspectUniqueMeteor(this.spawnedPokemon).containsKey(aspect)){
                    chosen_aspect_structure.set(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getAspectUniqueMeteor(this.spawnedPokemon).get(aspect));
                }
            });
            if(!chosen_aspect_structure.get().isEmpty()){
                placer = new StructurePlacerAPI((WorldGenLevel) this.level(), ResourceLocation.tryParse(chosen_aspect_structure.get()), this.blockPosition(), Mirror.NONE, Rotation.NONE, false, 1f, getOffset(getSizeClass(), ResourceLocation.tryParse(chosen_aspect_structure.get())));
                aspectFound = true;
            }

        }

        //then checks if there is a specific meteor for the pokemon species
        //If the aspectFound is true it means this should not run. It is here because if it isn't found but is declared,
        // there might be a pool of unique meteors to spawn
        if(!aspectFound && PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getUniqueMeteor(this.spawnedPokemon) != null && !PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getUniqueMeteor(this.spawnedPokemon).isEmpty()){
            //this means there is a specific meteor file that is being searched
            //If there is only one specific meteor file, spawn that
            if(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getUniqueMeteor(this.spawnedPokemon).contains(":")){
                placer = new StructurePlacerAPI((WorldGenLevel) this.level(), ResourceLocation.tryParse(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getUniqueMeteor(this.spawnedPokemon)), this.blockPosition(), Mirror.NONE, Rotation.NONE, false, 1f, getOffset(getSizeClass(), ResourceLocation.tryParse(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getUniqueMeteor(this.spawnedPokemon))));
            }else{ //otherwise spawn between the unique meteors for that type
                placer = getPlacer(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getSizeClass(this.spawnedPokemon), PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getUniqueMeteor(this.spawnedPokemon));
            }
        }else if(!aspectFound){
            placer = getPlacer(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getSizeClass(this.spawnedPokemon));

        }


        placer.actionOnBlocksPlacedByStructure(((structureBlockInfo, serverLevelAccessor) -> {
            if(structureBlockInfo.state().getBlock() instanceof SignBlock && structureBlockInfo.nbt() != null){
                ListTag front_messages =  structureBlockInfo.nbt().getCompound("front_text").getList("messages", Tag.TAG_STRING);//(ListTag) structureBlockInfo.nbt().getCompound("front_text").get("messages");
                AtomicBoolean found = new AtomicBoolean(false);
                front_messages.forEach(msg -> {
                    if(msg.getAsString().replaceAll("\"", "").equalsIgnoreCase("pokespawn")){
                       found.set(true);
                    }
                });
                ListTag back_messages =  structureBlockInfo.nbt().getCompound("back_text").getList("messages", Tag.TAG_STRING);
                back_messages.forEach(msg -> {
                    if(msg.getAsString().replaceAll("\"", "").equalsIgnoreCase("pokespawn")){
                        found.set(true);
                    }
                });

                //TODO workout how to spawn multiple pokemon
                if(found.get()){
                    this.spawnPokemon(structureBlockInfo.pos());
                    return new StructureTemplate.StructureBlockInfo(structureBlockInfo.pos(), Blocks.AIR.defaultBlockState(), null);
                }
            } //worldedit can leave the structure void behind soo
            if(structureBlockInfo.state().getBlock().equals(Blocks.STRUCTURE_VOID)){
                BlockEntity blockEntity = this.level().getBlockEntity(structureBlockInfo.pos());
                StructureTemplate.StructureBlockInfo info;
                if (blockEntity != null) {
                    info = new StructureTemplate.StructureBlockInfo(structureBlockInfo.pos(), this.level().getBlockState(structureBlockInfo.pos()), blockEntity.saveWithId(this.level().registryAccess()));
                } else {
                    info = new StructureTemplate.StructureBlockInfo(structureBlockInfo.pos(), this.level().getBlockState(structureBlockInfo.pos()), null);
                }
                return info;
            }
            return structureBlockInfo;
        }), BlockTags.ALL_SIGNS);

        return placer;
        //super.getPlacer();
    }

    /**Spawns a pokemon with some ticks of resistance to damage and fire as well as not inside some blocks*/
    protected void spawnPokemon(BlockPos spawnPos){

        AtomicBoolean suffocates = new AtomicBoolean(true);
        while(suffocates.get()){
            spawnPos = spawnPos.above();
            spawnedPokemon.setPos(spawnPos.getBottomCenter());

            EntityDimensions entityDimensions = spawnedPokemon.getDimensions(spawnedPokemon.getPose());
            AABB box = entityDimensions.makeBoundingBox(spawnPos.getBottomCenter());

            suffocates.set(false);
            this.level().getBlockStates(box).forEach( state -> {
                if(!state.isAir()){
                    suffocates.set(true);
                }
            });
        }


        this.level().addFreshEntity(spawnedPokemon);
        spawnedPokemon.addEffect((new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30, 255, true, false)));
        //5 seconds of fire resistance to avoid fire damage
        spawnedPokemon.addEffect((new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20*5, 1, true, false)));

    }


    /**If the meteor is a simple spawn meteor no structure will be generated, the pokemon will be the only thing spawning
     */
    public boolean isSimpleSpawn() {
        return simpleSpawn;
    }
    /**If the meteor is a simple spawn meteor no structure will be generated, the pokemon will be the only thing spawning
     */
    public void setSimpleSpawn(boolean simpleSpawn) {
        this.simpleSpawn = simpleSpawn;
    }

}

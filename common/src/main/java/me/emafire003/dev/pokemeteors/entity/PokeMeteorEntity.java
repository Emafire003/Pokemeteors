package me.emafire003.dev.pokemeteors.entity;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import me.emafire003.dev.ohmymeteors.OhMyMeteors;
import me.emafire003.dev.ohmymeteors.config.Config;
import me.emafire003.dev.ohmymeteors.entities.MeteorProjectileEntity;
import me.emafire003.dev.ohmymeteors.util.MeteorSizeClass;
import me.emafire003.dev.ohmymeteors.util.scheduler.SchedulerUtils;
import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.util.PokemeteorUtils;
import me.emafire003.dev.structureplacerapi.StructurePlacerAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static me.emafire003.dev.pokemeteors.util.PokemeteorUtils.METEOR_STRUCTURES;

public class PokeMeteorEntity extends MeteorProjectileEntity {

    /// The position at which the meteor will end up
    protected Vec3 targetPos = Vec3.ZERO;
    protected PokemonEntity spawnedPokemon;

    public PokeMeteorEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public PokeMeteorEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level world, Vec3 targetPos, PokemonEntity spawnedPokemon) {
        super(entityType, world);
        this.targetPos = targetPos;
        this.spawnedPokemon = spawnedPokemon;
        this.setSize(this.level().getRandom().nextInt(Math.min(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getMinMeteorSize(spawnedPokemon), PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getMaxMeteorSize(spawnedPokemon)), Math.max(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getMinMeteorSize(spawnedPokemon), PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getMaxMeteorSize(spawnedPokemon))));
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

    @Override
    public void detonateWithStructure() {
        //TODO override the structure stuff to spawn this correctly
        spawnedPokemon.setPos(this.getTargetPos().add(0, 10, 0)); //TODO remove, debug
        this.level().addFreshEntity(spawnedPokemon);
        super.detonateWithStructure();
    }

    /**
     * Returns the ID of the structure that is going to be spawned based the size class
     *
     * @param sizeClass The size of the meteors that we want to spawn, can be "small" "medium" "big" "huge"
     * */
    @Override
    public ResourceLocation getStructureToPlace(MeteorSizeClass sizeClass){
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
            PokemeteorsCommon.LOGGER.error("The list of structures for size class '" + sizeClass.getSerializedName() + "' is empty! Check that your structures are valid ones!");
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

    @Override
    //TODO the structures are going to be divided by the pokemon spawn, instead of small bug etc. aka "minior" will have its own set of meteors
    // and so on. If they aren't found, default to something else
    public StructurePlacerAPI getPlacer() {
        BlockPos m_pos_offset = BlockPos.containing(this.getDeltaMovement()).offset(-1, 0, -1);//new BlockPos(-1, -2, -1);
        StructurePlacerAPI placer =
                new StructurePlacerAPI((WorldGenLevel) this.level(), ResourceLocation.fromNamespaceAndPath(OhMyMeteors.MOD_ID, "pokemeteors/medium_test"), this.blockPosition(), Mirror.NONE, Rotation.NONE, false, 1f, m_pos_offset);

        //TODO add ability to select for folders, and for variants. So miniors will spawn with their color thingy
        if(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getSpecialMeteor(this.spawnedPokemon) != null && !PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getSpecialMeteor(this.spawnedPokemon).isEmpty()){
            placer = new StructurePlacerAPI((WorldGenLevel) this.level(), ResourceLocation.fromNamespaceAndPath(OhMyMeteors.MOD_ID, PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getSpecialMeteor(this.spawnedPokemon)), this.blockPosition(), Mirror.NONE, Rotation.NONE, false, 1f, m_pos_offset);
        }else{
            placer = super.getPlacer(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getSizeClass(this.spawnedPokemon));
        }

        AtomicBoolean spawned = new AtomicBoolean(false);
        placer.actionOnBlocksPlacedByStructure(((structureBlockInfo, serverLevelAccessor) -> {
            if(structureBlockInfo.state().getBlock() instanceof SignBlock && structureBlockInfo.nbt() != null){
                ListTag front_messages =  structureBlockInfo.nbt().getCompound("front_text").getList("messages", Tag.TAG_STRING);//(ListTag) structureBlockInfo.nbt().getCompound("front_text").get("messages");
                AtomicBoolean found = new AtomicBoolean(false);
                front_messages.forEach(msg -> {
                    if(msg.getAsString().replaceAll("\"", "").equalsIgnoreCase("pokespawn")){
                       found.set(true);
                       spawned.set(true);
                    }
                });
                ListTag back_messages =  structureBlockInfo.nbt().getCompound("back_text").getList("messages", Tag.TAG_STRING);
                back_messages.forEach(msg -> {
                    if(msg.getAsString().replaceAll("\"", "").equalsIgnoreCase("pokespawn")){
                        found.set(true);
                        spawned.set(false);
                    }
                });

                //TODO workout how to spawn multiple pokemon
                if(found.get() && !spawned.get()){
                    spawnedPokemon.setPos(structureBlockInfo.pos().getBottomCenter());
                    //runs a tick later so the pokemon isn't damaged by the explosion
                    SchedulerUtils.runLater(5, (server) -> this.level().addFreshEntity(spawnedPokemon));
                    BlockEntity blockEntity = this.level().getBlockEntity(structureBlockInfo.pos());
                    StructureTemplate.StructureBlockInfo info;
                    if (blockEntity != null) {
                        info = new StructureTemplate.StructureBlockInfo(structureBlockInfo.pos(), this.level().getBlockState(structureBlockInfo.pos()), blockEntity.saveWithId(this.level().registryAccess()));
                    } else {
                        info = new StructureTemplate.StructureBlockInfo(structureBlockInfo.pos(), this.level().getBlockState(structureBlockInfo.pos()), null);
                    }
                    return info; //this is actually correct, it is retuning air. And also it actually gets here
                }
            }
            return structureBlockInfo;
        }), BlockTags.ALL_SIGNS);
        return placer;
        //super.getPlacer();
    }
}

package me.emafire003.dev.pokemeteors.common.entity;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import me.emafire003.dev.ohmymeteors.OhMyMeteors;
import me.emafire003.dev.ohmymeteors.entities.MeteorProjectileEntity;
import me.emafire003.dev.ohmymeteors.util.scheduler.SchedulerUtils;
import me.emafire003.dev.pokemeteors.common.PokemeteorsCommon;
import me.emafire003.dev.structureplacerapi.StructurePlacerAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
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

import java.util.concurrent.atomic.AtomicBoolean;

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
        if(this.getSize() > 10){
            this.setSize(10);
        }
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

    @Override
    //TODO the structures are going to be divided by the pokemon spawn, instead of small bug etc. aka "minior" will have its own set of meteors
    // and so on. If they aren't found, default to something else
    public StructurePlacerAPI getPlacer() {
        BlockPos m_pos_offset = BlockPos.containing(this.getDeltaMovement()).offset(-1, 0, -1);//new BlockPos(-1, -2, -1);
        StructurePlacerAPI placer =
                new StructurePlacerAPI((WorldGenLevel) this.level(), ResourceLocation.fromNamespaceAndPath(OhMyMeteors.MOD_ID, "pokemeteors/medium_test"), this.blockPosition(), Mirror.NONE, Rotation.NONE, false, 1f, m_pos_offset);


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

                if(found.get()){
                    spawnedPokemon.setPos(structureBlockInfo.pos().getBottomCenter());
                    //runs a tick later so the pokemon isn't damaged by the explosion
                    SchedulerUtils.runLater(5, (server) -> this.level().addFreshEntity(spawnedPokemon));
                    BlockEntity blockEntity = this.level().getBlockEntity(structureBlockInfo.pos());
                    StructureTemplate.StructureBlockInfo info;
                    PokemeteorsCommon.LOGGER.info("The block found: " + this.level().getBlockState(structureBlockInfo.pos()));
                    PokemeteorsCommon.LOGGER.info("The position: " + structureBlockInfo.pos().getBottomCenter());
                    if (blockEntity != null) {
                        PokemeteorsCommon.LOGGER.info("The block found: " + this.level().getBlockState(structureBlockInfo.pos()));
                        info = new StructureTemplate.StructureBlockInfo(structureBlockInfo.pos(), this.level().getBlockState(structureBlockInfo.pos()), blockEntity.saveWithId(this.level().registryAccess()));
                    } else {
                        PokemeteorsCommon.LOGGER.info("The block found: " + this.level().getBlockState(structureBlockInfo.pos()));
                        info = new StructureTemplate.StructureBlockInfo(structureBlockInfo.pos(), this.level().getBlockState(structureBlockInfo.pos()), null);
                    }
                    return info;
                }
            }
            return structureBlockInfo;
        }), BlockTags.ALL_SIGNS);
        return placer;
        //super.getPlacer();
    }
}

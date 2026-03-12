package me.emafire003.dev.pokemeteors.common.entity;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import me.emafire003.dev.ohmymeteors.entities.MeteorProjectileEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
}

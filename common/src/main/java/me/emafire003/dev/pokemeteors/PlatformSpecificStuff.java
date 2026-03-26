package me.emafire003.dev.pokemeteors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import dev.architectury.injectables.annotations.ExpectPlatform;
import me.emafire003.dev.pokemeteors.entity.PokeMeteorEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

import java.nio.file.Path;

public class PlatformSpecificStuff {

    @ExpectPlatform
    public static PokeMeteorEntity getSinglePokeMeteor(ServerLevel world, Vec3 spawnPos, PokemonEntity spawnedPokemon) {
        //new PokeMeteorEntity(OMMEntities.METEOR_PROJECTILE_ENTITY, world, targetSpawnPos, spawnedPokemon);
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModLoaded(String modId){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Path getConfigPath(){
        throw new AssertionError();
    }
}

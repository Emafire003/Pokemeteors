package me.emafire003.dev.pokemeteors.fabric;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import me.emafire003.dev.pokemeteors.entity.PokeMeteorEntity;
import me.emafire003.dev.pokemeteors.fabric.entity.PKMFabricEntities;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class PlatformSpecificStuffImpl {

    public static PokeMeteorEntity getSinglePokeMeteor(ServerLevel world, Vec3 spawnPos, PokemonEntity spawnedPokemon) {
        return new PokeMeteorEntity(PKMFabricEntities.POKE_METEOR, world, spawnPos, spawnedPokemon);
    }

    public static boolean isModLoaded(String modId){
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static Path getConfigPath(){
        return FabricLoader.getInstance().getConfigDir();
    }
}

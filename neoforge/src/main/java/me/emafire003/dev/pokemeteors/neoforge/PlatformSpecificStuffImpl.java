package me.emafire003.dev.pokemeteors.neoforge;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import me.emafire003.dev.pokemeteors.entity.PokeMeteorEntity;
import me.emafire003.dev.pokemeteors.neoforge.entity.PKMNeoforgeEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class PlatformSpecificStuffImpl {

    public static PokeMeteorEntity getSinglePokeMeteor(ServerLevel world, Vec3 spawnPos, PokemonEntity spawnedPokemon) {
        return new PokeMeteorEntity(PKMNeoforgeEntities.POKEMETEOR_ENTITY.get(), world, spawnPos, spawnedPokemon);
    }

    public static boolean isModLoaded(String modId){
        return ModList.get().isLoaded(modId);
    }

    public static Path getConfigPath(){
        return Path.of(FMLLoader.getGamePath() + "/config/");
    }
}

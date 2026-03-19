package me.emafire003.dev.pokemeteors.fabric;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import me.emafire003.dev.pokemeteors.entity.PokeMeteorEntity;
import me.emafire003.dev.pokemeteors.fabric.entity.PKMFabricEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("unused")
public class PlatformSpecificStuffImpl {

    public static PokeMeteorEntity getSinglePokeMeteor(ServerLevel world, Vec3 spawnPos, PokemonEntity spawnedPokemon) {
        return new PokeMeteorEntity(PKMFabricEntities.POKE_METEOR, world, spawnPos, spawnedPokemon);
    }
}

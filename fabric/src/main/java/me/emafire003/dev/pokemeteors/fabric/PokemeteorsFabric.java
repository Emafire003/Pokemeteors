package me.emafire003.dev.pokemeteors.fabric;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.spawning.position.SpawnablePosition;
import com.cobblemon.mod.common.pokemon.Species;
import me.emafire003.dev.ohmymeteors.util.MeteorUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public final class PokemeteorsFabric implements ModInitializer {
    public static final String MOD_ID = "pokemeteors";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    //TODO migrate to hashmap
    public static Map<Species, Integer> SPECIES_METEOR_CHANCE = Map.of();

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("Hello Fabric world!");
        //TODO use getById
        //SpawningCondition

        CobblemonEvents.POKEMON_ENTITY_SPAWN.subscribe(spawnEvent -> {
            PokemonEntity pokemon = spawnEvent.getEntity();
            SpawnablePosition spawnablePosition = spawnEvent.getSpawnablePosition();
            Species sp = pokemon.getExposedSpecies();//clefairy
            SPECIES_METEOR_CHANCE = Map.of(Objects.requireNonNull(PokemonSpecies.getByName("minior")), 1);

            if(SPECIES_METEOR_CHANCE.containsKey(sp)){
                //TODO remove debug or test
                if(true || spawnablePosition.getWorld().getRandom().nextInt(SPECIES_METEOR_CHANCE.get(sp)) == 0){
                    //TODO actually make the custom meteor and stuff
                    // MeteorProjectileEntity meteor = new MeteorProjectileEntity(OMMEntities.METEOR_PROJECTILE_ENTITY, pokemon.level());
                    if(!pokemon.level().isClientSide()){
                        MeteorUtils.spawnMeteor((ServerLevel) pokemon.level(), pokemon.level().getNearestPlayer(pokemon, 300), false);
                        pokemon.finalizeSpawn(spawnablePosition.getWorld(), spawnablePosition.getWorld().getCurrentDifficultyAt(spawnablePosition.getPosition()), MobSpawnType.NATURAL, null);

                        spawnablePosition.getWorld().addFreshEntity(pokemon);
                    }
                }
            }
            //This is here to prevent
            spawnEvent.cancel();

        });
    }
}

package me.emafire003.dev.pokemeteors.common;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.spawning.position.SpawnablePosition;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Species;
import me.emafire003.dev.ohmymeteors.util.MeteorUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public class PokemeteorsCommon {

    public static final String MOD_ID = "pokemeteors";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    //TODO migrate to hashmap
    public static Map<Species, Integer> SPECIES_METEOR_CHANCE = Map.of();

    public static ResourceLocation getIdentifier(String path){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void init(){
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
                        PokemeteorUtils.spawnMeteor((ServerLevel) pokemon.level(), pokemon.position(), pokemon, false);
                        pokemon.finalizeSpawn(spawnablePosition.getWorld(), spawnablePosition.getWorld().getCurrentDifficultyAt(spawnablePosition.getPosition()), MobSpawnType.NATURAL, null);
                    }
                }
            }
            //This is here to prevent
            spawnEvent.cancel();

        });
    }
}

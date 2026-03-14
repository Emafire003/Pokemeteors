package me.emafire003.dev.pokemeteors.common;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.spawning.position.SpawnablePosition;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Species;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.emafire003.dev.pokemeteors.common.util.PokemeteorUtils;
import me.emafire003.dev.pokemeteors.common.util.SpeciesMeteorChance;
import me.emafire003.dev.pokemeteors.common.util.SpeciesMeteorConfig;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class PokemeteorsCommon {

    public static final String MOD_ID = "pokemeteors";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static SpeciesMeteorConfig SPECIES_CHANCE_CONFIG = new SpeciesMeteorConfig(new SpeciesMeteorChance(ResourceLocation.fromNamespaceAndPath(Cobblemon.MODID, "minior"), 1));

    public static ResourceLocation getIdentifier(String path){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static final TagKey<Block> METEOR_BYPASSES = TagKey.create(Registries.BLOCK, getIdentifier("meteor_bypasses"));
    public static final TagKey<Block> METEOR_BYPASSES_AND_DESTROY = TagKey.create(Registries.BLOCK, getIdentifier("meteor_bypasses_and_destroy"));

    @SuppressWarnings("unused")
    public static void registerTags(){
        HolderSet.Named<Block> METEOR_BYPASSES_TAG = BuiltInRegistries.BLOCK.getOrCreateTag(METEOR_BYPASSES);
        HolderSet.Named<Block> METEOR_BYPASSES_AND_DESTROY_TAG = BuiltInRegistries.BLOCK.getOrCreateTag(METEOR_BYPASSES_AND_DESTROY);

    }

    public static void init(Path configPath){
        //If the file doesn't exists, create the default one
        if(!configPath.resolve("pokemeteors_spawns.json").toFile().exists()){
            generateDefaultFile(configPath);
        }
        SPECIES_CHANCE_CONFIG = readPokemeteorsFile(configPath);

        CobblemonEvents.POKEMON_ENTITY_SPAWN.subscribe(spawnEvent -> {
            PokemonEntity pokemon = spawnEvent.getEntity();
            SpawnablePosition spawnablePosition = spawnEvent.getSpawnablePosition();
            Species sp = pokemon.getExposedSpecies();//clefairy

            if(SPECIES_CHANCE_CONFIG.contains(sp)){
                if(true || spawnablePosition.getWorld().getRandom().nextInt(SPECIES_CHANCE_CONFIG.getChance(sp)) == 0){
                    if(!pokemon.level().isClientSide()){
                        PokemeteorUtils.spawnMeteor((ServerLevel) pokemon.level(), pokemon.position(), pokemon, false);
                        pokemon.finalizeSpawn(spawnablePosition.getWorld(), spawnablePosition.getWorld().getCurrentDifficultyAt(spawnablePosition.getPosition()), MobSpawnType.NATURAL, null);
                    }
                }
            }
            //This is here to prevent a double pokespawn
            spawnEvent.cancel();

        });
    }

    /** Generates the defualt file for the list of pokemons that are going to spawn with a meteor
     * @param path the starting path, usually the config directory.*/
    public static void generateDefaultFile(Path path){
        try (FileWriter fileWriter = new FileWriter(String.valueOf(path.resolve("pokemeteors_spawns.json").toFile()), StandardCharsets.UTF_8);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, 4096)) {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            gson.toJson(SPECIES_CHANCE_CONFIG, bufferedWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**Reads the pokemons that should spawn with meteors from the config file*/
    public static SpeciesMeteorConfig readPokemeteorsFile(Path path){
        try (FileReader fileReader = new FileReader(String.valueOf(path.resolve("pokemeteors_spawns.json").toFile()), StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(fileReader, 4096)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            return gson.fromJson(bufferedReader, SpeciesMeteorConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

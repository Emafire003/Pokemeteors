package me.emafire003.dev.pokemeteors;

import com.cobblemon.mod.common.Cobblemon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.emafire003.dev.ohmymeteors.config.Config;
import me.emafire003.dev.ohmymeteors.util.MeteorSizeClass;
import me.emafire003.dev.pokemeteors.util.SpeciesMeteorChance;
import me.emafire003.dev.pokemeteors.util.SpeciesMeteorConfig;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemeteorsCommon {

    public static final String MOD_ID = "pokemeteors";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static String PREFIX = "§8[§cPoké§fmeteors§8] §r";

    @Deprecated //the default file will be loaded insetad.
    public static final SpeciesMeteorConfig defaultConfigChance = new SpeciesMeteorConfig(
            List.of(
                    new SpeciesMeteorChance(
                            ResourceLocation.fromNamespaceAndPath(Cobblemon.MODID, "minior").toString(),
                            1, Config.MAX_SMALL_METEOR_SIZE, 2,
                            MeteorSizeClass.SMALL, "minior",
                            new HashMap<>(Map.of(
                                    "blue-core", MOD_ID+":small/minior/small_blue",
                                    "indigo-core", MOD_ID+":small/minior/small_indigo",
                                    "yellow-core", MOD_ID+":small/minior/small_yellow",
                                    "green-core", MOD_ID+":small/minior/small_green",
                                    "orange-core", MOD_ID+":small/minior/small_orange",
                                    "red-core", MOD_ID+":small/minior/small_red",
                                    "violet-core", MOD_ID+":small/minior/small_violet"
                            ))
                    ),
                    new SpeciesMeteorChance(
                            ResourceLocation.fromNamespaceAndPath(Cobblemon.MODID, "deoxys").toString(),
                            1,
                            20, 10,
                            MeteorSizeClass.BIG, "pokemeteors:deoxys"
                    )
            )
    );

    public static SpeciesMeteorConfig SPECIES_CHANCE_CONFIG = defaultConfigChance;

    public static ResourceLocation getIdentifier(String path){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    /** Generates the defualt file for the list of pokemons that are going to spawn with a meteor
     * @param path the starting path, usually the config directory.*/
    @Deprecated
    public static void generateDefaultFile(Path path){
        try (FileWriter fileWriter = new FileWriter(String.valueOf(path.resolve("pokemeteors_spawns.json").toFile()), StandardCharsets.UTF_8);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, 4096)) {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            //Map<String, String> newMap = gson.fromJson(json, typeOfHashMap); // This type must match TypeToken


            gson.toJson(SPECIES_CHANCE_CONFIG, bufferedWriter);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**Reads the pokemons that should spawn with meteors from the config file*/
    @Deprecated
    public static SpeciesMeteorConfig readPokemeteorsFile(Path path){
        try (FileReader fileReader = new FileReader(String.valueOf(path.resolve("pokemeteors_spawns.json").toFile()), StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(fileReader, 4096)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            //Type typeOfHashMap = new TypeToken<Map<String, String>>() { }.getType();
            return gson.fromJson(bufferedReader, SpeciesMeteorConfig.class);
        } catch (FileNotFoundException e){
            generateDefaultFile(path);
            return readPokemeteorsFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

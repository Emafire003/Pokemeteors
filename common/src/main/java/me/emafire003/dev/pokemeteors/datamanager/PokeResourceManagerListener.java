package me.emafire003.dev.pokemeteors.datamanager;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.util.SpeciesMeteorChance;
import me.emafire003.dev.pokemeteors.util.SpeciesMeteorConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class PokeResourceManagerListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();
    public static final String RESOURCE_PATH = "spawns";

    public PokeResourceManagerListener() {
        super(GSON, RESOURCE_PATH);
    }

    boolean ignore_default = false;

    ResourceLocation defaultFileId = ResourceLocation.fromNamespaceAndPath(PokemeteorsCommon.MOD_ID, "default_pokemeteors_spawns");

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons,
                         ResourceManager manager,
                         ProfilerFiller profiler) {

        PokemeteorsCommon.LOGGER.debug("LOADING DATAPACKS: {}", jsons.size());
        ignore_default = false; //resets back to false for the restart

        PokemeteorsCommon.SPECIES_CHANCE_CONFIG = null;
        //Map<ResourceLocation, Resource> resources = manager.listResources(RESOURCE_PATH, identifier -> identifier.getPath().endsWith(".json"));

        JsonElement defaultConfigChances = null;
        for (var entry : jsons.entrySet()) {
            ResourceLocation fileId = entry.getKey();
            JsonElement json = entry.getValue();
            if(!fileId.getPath().equalsIgnoreCase(defaultFileId.getPath())){//skip to process it as last thing
                loadChanceEntry(fileId, json);
            }else {
                defaultConfigChances = json;
            }

        }

        //Ensure the default file is loaded last so if any datapack disables it it won't be loaded
        if(jsons.containsKey(defaultFileId)){
            loadChanceEntry(defaultFileId, defaultConfigChances);
        }

    }

    public void loadChanceEntry(ResourceLocation fileId, JsonElement json){
        if(ignore_default && fileId.equals(defaultFileId)){
            PokemeteorsCommon.LOGGER.info("The default spawn config has been completely disabled for an override");
            return;
        }

        SpeciesMeteorConfig result = SpeciesMeteorConfig.CODEC
                .parse(new Dynamic<>(JsonOps.INSTANCE, json))
                .resultOrPartial(error -> PokemeteorsCommon.LOGGER.error("Failed parsing {}: {}", fileId, error))
                .orElse(null);

        if(PokemeteorsCommon.SPECIES_CHANCE_CONFIG == null){
            PokemeteorsCommon.SPECIES_CHANCE_CONFIG = result;
        }

        if(result != null) {
            //removes stuff if //TODO say that it must be the first one the "all_default"
            if(!result.getOverrideFor().isEmpty() && result.getOverrideFor().getFirst().equalsIgnoreCase("all_default") && !ignore_default){
                PokemeteorsCommon.LOGGER.info(fileId + " has disabled the default pokemeteor spawn config");
                ignore_default = true;
            }
            HashMap<String, SpeciesMeteorChance> newChancesMap = new HashMap<>();
            PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getSpeciesMeteorChances().forEach(speciesMeteorChance -> {
                newChancesMap.put(speciesMeteorChance.species(), speciesMeteorChance);
            });

            //removes other overrides
            result.getOverrideFor().forEach(overrideId -> {
                if(overrideId.equalsIgnoreCase("all_default")){
                    return;
                }
                newChancesMap.remove(overrideId);
                //newChances.remove(PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getSpeciesChanceById(overrideId));
            });

            //If there is already a loaded chance for that pokémon species, WITHOUT an override (removed above), don't add it do the list
            result.getSpeciesMeteorChances().forEach( speciesMeteorChance -> {
                if(!newChancesMap.containsKey(speciesMeteorChance.species())){
                    newChancesMap.put(speciesMeteorChance.species(), speciesMeteorChance);
                }
            });

            PokemeteorsCommon.SPECIES_CHANCE_CONFIG.setSpeciesMeteorChances(newChancesMap.values().stream().toList());
        }else{
            PokemeteorsCommon.LOGGER.error("Something went wrong on datapack load!");
        }
    }
}
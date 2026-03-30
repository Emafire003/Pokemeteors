package me.emafire003.dev.pokemeteors.datamanager;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.util.SpeciesMeteorConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class PokeResourceManagerListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();
    public static final String DIR = "spawns";

    public PokeResourceManagerListener() {
        super(GSON, DIR);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons,
                         ResourceManager manager,
                         ProfilerFiller profiler) {

        PokemeteorsCommon.LOGGER.debug("LOADING DATAPACKS FOR DBR: {}", jsons.size());


        for (var entry : jsons.entrySet()) {

            ResourceLocation fileId = entry.getKey();
            JsonElement json = entry.getValue();

            SpeciesMeteorConfig result = SpeciesMeteorConfig.CODEC
                    .parse(new Dynamic<>(JsonOps.INSTANCE, json))
                    .resultOrPartial(error -> PokemeteorsCommon.LOGGER.error("Failed parsing {}: {}", fileId, error))
                    .orElse(null);

            if(result != null) {
                PokemeteorsCommon.SPECIES_CHANCE_CONFIG = result;
            }else{
                PokemeteorsCommon.LOGGER.error("Something went wrong on datapack load!");
            }

        }
    }
}

package me.emafire003.dev.pokemeteors.fabric;

import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.config.ConfigSettings;
import me.emafire003.dev.pokemeteors.util.PokemeteorUtils;
import me.emafire003.dev.pokemeteors.fabric.entity.PKMFabricEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;

import java.io.IOException;
import java.nio.file.Files;

public final class PokemeteorsFabric implements ModInitializer {
    //public static final String MOD_ID = "pokemeteors";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.

    @Override
    public void onInitialize() {
        try {
            Files.createDirectories(FabricLoader.getInstance().getConfigDir().resolve(PokemeteorsCommon.MOD_ID));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //PokemeteorsCommon.init(FabricLoader.getInstance().getConfigDir().resolve(PokemeteorsCommon.MOD_ID));
        PKMFabricEntities.registerEntities();

        //TODO do the same for neoforge
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((minecraftServer, lifecycledResourceManager, b) -> {
            //yes reloads for each dimension
            //TODO maybe just pick one? Datapacks aren't per-dimension right? But multiverse and stuff exists so idk
            minecraftServer.getAllLevels().forEach(PokemeteorUtils::reInitStructures);

            if(FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")){
                ConfigSettings.HANDLER.load();
            }
        });

        // Datapack reload listener
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new PokeResourceManagerFabric());
    }
}

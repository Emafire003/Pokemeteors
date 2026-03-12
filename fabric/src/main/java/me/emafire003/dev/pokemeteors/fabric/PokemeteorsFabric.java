package me.emafire003.dev.pokemeteors.fabric;

import me.emafire003.dev.pokemeteors.common.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.fabric.entity.PKMFabricEntities;
import net.fabricmc.api.ModInitializer;

public final class PokemeteorsFabric implements ModInitializer {
    //public static final String MOD_ID = "pokemeteors";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.

    @Override
    public void onInitialize() {
        PokemeteorsCommon.init();
        PKMFabricEntities.registerEntities();
    }
}

package me.emafire003.dev.pokemeteors.config;

import net.minecraft.client.gui.screens.Screen;

public class YaclScreenMaker {

    public static Screen getScreen(Screen parent){

        return ConfigSettings.HANDLER.generateGui()
                .generateScreen(parent);
    }
}

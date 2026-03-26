package me.emafire003.dev.pokemeteors;

import me.emafire003.dev.pokemeteors.config.YaclScreenMaker;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.net.URI;

public class PokemeteorsCommonClient {
    /**Create a config screen for ModMenu if YACL is present, or
     * a confirmation screen otherwise to tell you to download yacl*/
    public static Screen createConfigScreen(Screen parent) {
        if (!PlatformSpecificStuff.isModLoaded("yet_another_config_lib_v3")) {
            return new ConfirmScreen((result) -> {
                if (result) {
                    Util.getPlatform().openUri(URI.create("https://modrinth.com/mod/yacl/versions"));
                }
                Minecraft.getInstance().setScreen(parent);
            },
                    Component.literal("You need to install YACL"), Component.literal("To modify the settings file you need to install YACL as well. Click on yes to open the modrinth page to download it."), CommonComponents.GUI_YES, CommonComponents.GUI_NO);
        } else {
            return YaclScreenMaker.getScreen(parent);
        }
    }
}

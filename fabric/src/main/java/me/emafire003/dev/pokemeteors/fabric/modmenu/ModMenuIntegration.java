package me.emafire003.dev.pokemeteors.fabric.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.emafire003.dev.pokemeteors.PokemeteorsCommonClient;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return PokemeteorsCommonClient::createConfigScreen;
    }
}
package me.emafire003.dev.pokemeteors.neoforge;

import me.emafire003.dev.pokemeteors.PokemeteorsCommonClient;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod("pokemeteors")
public class PokemeteorsNeoforge {

    public PokemeteorsNeoforge() {
        NeoForge.EVENT_BUS.register(this);
        //PokemeteorsCommon.init(Path.of(FMLLoader.getGamePath() + "/config/" + MOD_ID + "/"));

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (client, parent) -> PokemeteorsCommonClient.createConfigScreen(parent)
        );
    }

}

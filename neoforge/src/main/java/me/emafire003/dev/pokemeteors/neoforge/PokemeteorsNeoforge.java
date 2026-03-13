package me.emafire003.dev.pokemeteors.neoforge;

import me.emafire003.dev.pokemeteors.common.PokemeteorsCommon;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;

import java.nio.file.Path;

import static me.emafire003.dev.pokemeteors.common.PokemeteorsCommon.MOD_ID;

@Mod("pokemeteors")
public class PokemeteorsNeoforge {

    public PokemeteorsNeoforge() {
        NeoForge.EVENT_BUS.register(this);
        PokemeteorsCommon.init(Path.of(FMLLoader.getGamePath() + "/config/" + MOD_ID + "/"));
    }

}

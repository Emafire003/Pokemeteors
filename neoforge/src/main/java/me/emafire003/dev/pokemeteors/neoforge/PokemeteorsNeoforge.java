package me.emafire003.dev.pokemeteors.neoforge;

import me.emafire003.dev.pokemeteors.common.ExampleCommandRegistry;
import me.emafire003.dev.pokemeteors.common.PokemeteorsCommon;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod("pokemeteors")
public class PokemeteorsNeoforge {

    public PokemeteorsNeoforge() {
        NeoForge.EVENT_BUS.register(this);
        PokemeteorsCommon.init();
    }

}

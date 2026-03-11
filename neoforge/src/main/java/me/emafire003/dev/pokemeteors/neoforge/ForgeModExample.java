package me.emafire003.dev.pokemeteors.neoforge;

import me.emafire003.dev.pokemeteors.common.ExampleCommandRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod("pokemeteors")
public class ForgeModExample {

    public ForgeModExample() {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommandRegistration(RegisterCommandsEvent event) {
        ExampleCommandRegistry.registerCommands(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }
}

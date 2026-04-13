package me.emafire003.dev.pokemeteors.neoforge;

import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.PokemeteorsCommonClient;
import me.emafire003.dev.pokemeteors.config.ConfigSettings;
import me.emafire003.dev.pokemeteors.datamanager.PokeResourceManagerListener;
import me.emafire003.dev.pokemeteors.neoforge.entity.PKMNeoforgeEntities;
import me.emafire003.dev.pokemeteors.util.PokemeteorUtils;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod("pokemeteors")
public class PokemeteorsNeoforge {

    public PokemeteorsNeoforge(IEventBus eventBus) {
        NeoForge.EVENT_BUS.register(this);
        //PokemeteorsCommon.init(Path.of(FMLLoader.getGamePath() + "/config/" + MOD_ID + "/"));

        PKMNeoforgeEntities.register(eventBus);

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (client, parent) -> PokemeteorsCommonClient.createConfigScreen(parent)
        );

        try {
            Files.createDirectories(Path.of(FMLLoader.getGamePath() + "/config/").resolve(PokemeteorsCommon.MOD_ID+"/"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void registerDataListeners(AddReloadListenerEvent event) {
        event.addListener(new PokeResourceManagerListener());
    }

    private static MinecraftServer serverInstance = null;

    // Loads the config file on server startup as well as the scheduler
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        try{
            if(ModList.get().isLoaded("yet_another_config_lib_v3")){
                ConfigSettings.HANDLER.load();
            }
            //This is needed because for SOME REASON the datapack reload event doesn't have a server parameter :/
            serverInstance = event.getServer();
            //minecraftServer.getWorlds().forEach(OhMyMeteors::reInitStructures);
        }catch (Exception e){
            PokemeteorsCommon.LOGGER.error("There was an error while loading the config files!");
            e.printStackTrace();
        }
    }

    // Wow this looks like a stupid way to do this
    @SubscribeEvent
    public void onDatapackReload(OnDatapackSyncEvent event) {
        //why not just have the ServerLifeCycleEvents.Datapackreload thingy? Bah
        if(event.getPlayer() == null){
            if(ModList.get().isLoaded("yet_another_config_lib_v3")){
                ConfigSettings.HANDLER.load();
            }
            if(serverInstance == null){
                PokemeteorsCommon.LOGGER.error("Something went very wrong, could not get the server while reloading the datapacks!");
                return;
            }
            serverInstance.getAllLevels().forEach(PokemeteorUtils::reInitStructures);
        }
    }

}

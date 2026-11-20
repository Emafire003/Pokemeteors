package me.emafire003.dev.pokemeteors.neoforge;

import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.entity.client.PokeMeteorEntityRenderer;
import me.emafire003.dev.pokemeteors.neoforge.entity.PKMNeoforgeEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = PokemeteorsCommon.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = PokemeteorsCommon.MOD_ID, value = Dist.CLIENT)
public class PokemeteorNeoforgeClient {

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        registerEntityStuff();
    }

    public static void registerEntityStuff(){
        EntityRenderers.register(PKMNeoforgeEntities.POKEMETEOR_ENTITY.get(), PokeMeteorEntityRenderer::new);

    }

}

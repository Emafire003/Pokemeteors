package me.emafire003.dev.pokemeteors.fabric;

import me.emafire003.dev.pokemeteors.common.entity.client.PokeMeteorEntityRenderer;
import me.emafire003.dev.pokemeteors.fabric.entity.PKMFabricEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class PokemeteorsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerEntityStuff();
    }

    public static void registerEntityStuff(){
        //EntityModelLayerRegistry.registerModelLayer(BaseTntMeteorEntityModel.METEOR, BaseTntMeteorEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(PKMFabricEntities.POKE_METEOR, PokeMeteorEntityRenderer::new);
    }
}

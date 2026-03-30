package me.emafire003.dev.pokemeteors.fabric;

import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.datamanager.PokeResourceManagerListener;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public class PokeResourceManagerFabric extends PokeResourceManagerListener implements IdentifiableResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return ResourceLocation.fromNamespaceAndPath(PokemeteorsCommon.MOD_ID, PokeResourceManagerListener.DIR);
    }
}

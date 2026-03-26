package me.emafire003.dev.pokemeteors.mixin;

import com.cobblemon.mod.common.api.spawning.detail.PokemonHerdSpawnDetail;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PokemonHerdSpawnDetail.class)
public class HerdSpawnMixin {

    //TODO mixin into the create spawn action, nullify it, and spawn the meteor with more stuff in it
}

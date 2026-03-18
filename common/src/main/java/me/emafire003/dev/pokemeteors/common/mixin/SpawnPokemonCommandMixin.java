package me.emafire003.dev.pokemeteors.common.mixin;

import com.cobblemon.mod.common.command.SpawnPokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.emafire003.dev.pokemeteors.common.util.PokemeteorUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static me.emafire003.dev.pokemeteors.common.PokemeteorsCommon.SPECIES_CHANCE_CONFIG;

@Debug(export = true)
@Mixin(SpawnPokemon.class)
public class SpawnPokemonCommandMixin {

    @WrapOperation(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    public boolean spawnMeteorInstead(ServerLevel instance, Entity arg, Operation<Boolean> original){
        //TODO add an if clause to enable/disable the command thingy
        if(true){
            PokemonEntity pokemon = (PokemonEntity) arg;
            //TODO remember to remvoe the debug true||
            if(true || instance.getRandom().nextInt(SPECIES_CHANCE_CONFIG.getChance(pokemon.getExposedSpecies())) == 0){
                if(!instance.isClientSide()){
                    PokemeteorUtils.spawnMeteor(instance, arg.position(), pokemon, false);
                    return true;
                }
            }

        }
        return original.call(instance, arg);
    }
}

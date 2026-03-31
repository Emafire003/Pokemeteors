package me.emafire003.dev.pokemeteors.mixin;

import com.cobblemon.mod.common.command.SpawnPokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.emafire003.dev.pokemeteors.PlatformSpecificStuff;
import me.emafire003.dev.pokemeteors.config.ConfigSettings;
import me.emafire003.dev.pokemeteors.util.PokemeteorUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static me.emafire003.dev.pokemeteors.PokemeteorsCommon.SPECIES_CHANCE_CONFIG;

@Debug(export = true)
@Mixin(SpawnPokemon.class)
public class SpawnPokemonCommandMixin {

    @WrapOperation(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    public boolean spawnMeteorInstead(ServerLevel instance, Entity arg, Operation<Boolean> original){
        if(PlatformSpecificStuff.isModLoaded("yet_another_config_lib_v3") && !ConfigSettings.HANDLER.instance().overridePokespawnCommand){
            return original.call(instance, arg);
        }
        PokemonEntity pokemon = (PokemonEntity) arg;
        //pokemon.getSpawnCause().getSpawner()

        if(SPECIES_CHANCE_CONFIG.contains(pokemon.getExposedSpecies()) &&
                instance.getRandom().nextInt(SPECIES_CHANCE_CONFIG.getChance(pokemon.getExposedSpecies())) == 0){
            if(!instance.isClientSide()){
                if(SPECIES_CHANCE_CONFIG.getUniqueMeteor(pokemon).contains("simple_spawn") || ConfigSettings.HANDLER.instance().onlySimpleSpawns){
                    PokemeteorUtils.spawnMeteor(instance, arg.position(), pokemon, true);
                }else{
                    PokemeteorUtils.spawnMeteor(instance, arg.position(), pokemon, false);
                }
                return true;
            }
        }

        return original.call(instance, arg);
    }
}

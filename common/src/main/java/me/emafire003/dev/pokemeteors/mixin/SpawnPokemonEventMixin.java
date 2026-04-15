package me.emafire003.dev.pokemeteors.mixin;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.entity.SpawnEvent;
import com.cobblemon.mod.common.api.spawning.detail.EntitySpawnResult;
import com.cobblemon.mod.common.api.spawning.detail.SingleEntitySpawnAction;
import com.cobblemon.mod.common.api.spawning.position.SpawnablePosition;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Species;
import com.llamalad7.mixinextras.sugar.Local;
import kotlin.Unit;
import me.emafire003.dev.pokemeteors.config.ConfigSettings;
import me.emafire003.dev.pokemeteors.util.PokemeteorUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.emafire003.dev.pokemeteors.PokemeteorsCommon.SPECIES_CHANCE_CONFIG;

@Debug(export = true)
@Mixin(SingleEntitySpawnAction.class)
public abstract class SpawnPokemonEventMixin {

	@Inject(method = "run()Lcom/cobblemon/mod/common/api/spawning/detail/EntitySpawnResult;", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;setPos(Lnet/minecraft/world/phys/Vec3;)V"), cancellable = true)
	private void spawnMeteorInstead(CallbackInfoReturnable<EntitySpawnResult> cir, @Local(name = "e") Entity e){
		if(e instanceof PokemonEntity pokemon){
            //SpawnablePosition spawnablePosition = spawnEvent.getSpawnablePosition();
			Species sp = pokemon.getExposedSpecies();

			if(SPECIES_CHANCE_CONFIG.contains(sp) && e.level().canSeeSky(e.blockPosition())){
				if(e.level().getRandom().nextInt(SPECIES_CHANCE_CONFIG.getChance(sp)) == 0){
					if(!pokemon.level().isClientSide()){

						@SuppressWarnings("rawtypes")
						SingleEntitySpawnAction spawnAction = ((SingleEntitySpawnAction) (Object) this);
						SpawnablePosition spawnablePosition = spawnAction.getSpawnablePosition();

						pokemon.setPos(spawnablePosition.getPosition().getCenter());

                        PokemeteorUtils.spawnMeteor((ServerLevel) pokemon.level(), pokemon.position(), pokemon, SPECIES_CHANCE_CONFIG.getUniqueMeteor(pokemon).contains("simple_spawn") || ConfigSettings.HANDLER.instance().onlySimpleSpawns);
						pokemon.finalizeSpawn((ServerLevelAccessor) e.level(), e.level().getCurrentDifficultyAt(pokemon.blockPosition()), MobSpawnType.NATURAL, null);

						CobblemonEvents.ENTITY_SPAWN.postThen(new SpawnEvent<>(e, spawnAction.getSpawnablePosition()), (spawnEvent -> null), (spawnEvent) -> {
                            //noinspection unchecked
                            spawnAction.getEntity().emit(e);
							if (e instanceof Mob) {
								((Mob) e).finalizeSpawn(spawnablePosition.getWorld(), spawnablePosition.getWorld().getCurrentDifficultyAt(spawnablePosition.getPosition()), MobSpawnType.NATURAL, null);
							}
							// it would duplicate the spawned pokemon spawnablePosition.getWorld().addFreshEntity(e);
							return Unit.INSTANCE;
						});
					}
					cir.cancel();
				}

			}


		}
	}
}
package me.emafire003.dev.pokemeteors.common.mixin;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.entity.SpawnEvent;
import com.cobblemon.mod.common.api.spawning.detail.EntitySpawnResult;
import com.cobblemon.mod.common.api.spawning.detail.SingleEntitySpawnAction;
import com.cobblemon.mod.common.api.spawning.position.SpawnablePosition;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Species;
import com.llamalad7.mixinextras.sugar.Local;
import kotlin.Unit;
import me.emafire003.dev.pokemeteors.common.util.PokemeteorUtils;
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

import static me.emafire003.dev.pokemeteors.common.PokemeteorsCommon.SPECIES_CHANCE_CONFIG;

@Debug(export = true)
@Mixin(SingleEntitySpawnAction.class)
public abstract class SpawnPokemonEventMixin {

	//TODO actually o dont't know if it spawns the pokemon
	@Inject(method = "run()Lcom/cobblemon/mod/common/api/spawning/detail/EntitySpawnResult;", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;setPos(Lnet/minecraft/world/phys/Vec3;)V"), cancellable = true)
	private void spawnMeteorInstead(CallbackInfoReturnable<EntitySpawnResult> cir, @Local(name = "e") Entity e){
		if(e instanceof PokemonEntity pokemon){
            //SpawnablePosition spawnablePosition = spawnEvent.getSpawnablePosition();
			Species sp = pokemon.getExposedSpecies();//clefairy

			//TODO test out. maybe just go back to the event thing?
			if(SPECIES_CHANCE_CONFIG.contains(sp)){
				//TODO remember to remvoe the debug true||
				if(true || e.level().getRandom().nextInt(SPECIES_CHANCE_CONFIG.getChance(sp)) == 0){
					if(!pokemon.level().isClientSide()){
						PokemeteorUtils.spawnMeteor((ServerLevel) pokemon.level(), pokemon.position(), pokemon, false);
						pokemon.finalizeSpawn((ServerLevelAccessor) e.level(), e.level().getCurrentDifficultyAt(pokemon.blockPosition()), MobSpawnType.NATURAL, null);

						SingleEntitySpawnAction spawnAction = ((SingleEntitySpawnAction) (Object) this);

						SpawnablePosition spawnablePosition = spawnAction.getSpawnablePosition();

						CobblemonEvents.ENTITY_SPAWN.postThen(new SpawnEvent(e, spawnAction.getSpawnablePosition()), (spawnEvent -> {return null;}), (spawnEvent) -> {
							spawnAction.getEntity().emit(e);
							if (e instanceof Mob) {
								((Mob) e).finalizeSpawn(spawnablePosition.getWorld(), spawnablePosition.getWorld().getCurrentDifficultyAt(spawnablePosition.getPosition()), MobSpawnType.NATURAL, null);
							}
							spawnablePosition.getWorld().addFreshEntity(e);
							return Unit.INSTANCE;
						});

						//CobblemonEvents.ENTITY_SPAWN.postThen(new SpawnEvent<Entity>(e, spawnablePosition), (spawnEvent -> {}));
					}
				}
			}

			cir.cancel();
		}
	}
}
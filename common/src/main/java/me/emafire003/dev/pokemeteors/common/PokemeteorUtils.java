package me.emafire003.dev.pokemeteors.common;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import me.emafire003.dev.ohmymeteors.OhMyMeteors;
import me.emafire003.dev.ohmymeteors.config.Config;
import me.emafire003.dev.ohmymeteors.entities.MeteorProjectileEntity;
import me.emafire003.dev.ohmymeteors.entities.OMMEntities;
import me.emafire003.dev.ohmymeteors.util.MeteorUtils;
import me.emafire003.dev.pokemeteors.common.entity.PokeMeteorEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class PokemeteorUtils {

    /**
     * Gets a meteor object to be spawned in, with a velocity oriented downwards and a spawn position already set up
     * */
    public static PokeMeteorEntity getDownwardsMeteor(Vec3 targetSpawnPos, PokemonEntity spawnedPokemon, ServerLevel world, int min_spawn_d, int max_spawn_d, double spawn_height, int min_size, int max_size){
        PokeMeteorEntity meteor = new PokeMeteorEntity(OMMEntities.METEOR_PROJECTILE_ENTITY, world, targetSpawnPos, spawnedPokemon);

        Tuple<Vec3, Vec3> pos_vel = MeteorUtils.getDownwardsMeteorPosAndVelocity(targetSpawnPos, world, min_spawn_d, max_spawn_d, spawn_height);

        meteor.setPosRaw(pos_vel.getA().x, pos_vel.getA().y, pos_vel.getA().z);

        //TODO add variable or config mor max meteor size
        meteor.setSize(world.getRandom().nextIntBetweenInclusive(Math.max(0, min_size), Math.min(50, max_size)));

        meteor.setDeltaMovement(targetSpawnPos.subtract(meteor.position()).normalize().multiply(1,1,1).add(0, Config.DOWNWARDS_SPEED_MODIFIER, 0));
        
        return meteor;
    }
    
    
    /**Spawns a meteor around a random alive online player
     *
     * @param world The world in which the meteors are gonna be spawned in
     * @param targetSpawnPos The position where the meteor will impact and spawn the pokemon
     * @param spawnedPokemon The pokemon that will spawn inside/along the meteor
     * @param silenced Weather or not the meteor should be announced in chat*/
    public static void spawnMeteor(ServerLevel world, Vec3 targetSpawnPos, PokemonEntity spawnedPokemon, boolean silenced){
        PokeMeteorEntity meteor = getDownwardsMeteor(targetSpawnPos, spawnedPokemon, world.getLevel(),
                Config.MIN_METEOR_SPAWN_DISTANCE, Config.MAX_METEOR_SPAWN_DISTANCE, Config.METEOR_SPAWN_HEIGHT, Config.NATURAL_METEOR_MIN_SIZE, Config.NATURAL_METEOR_MAX_SIZE);

        meteor.setSilenced(silenced);

        String message;

        if(Config.SPAWN_HUGE_METEORS){
            if(world.getRandom().nextIntBetweenInclusive(0, Config.HUGE_METEOR_CHANCE) == 0){
                meteor = getDownwardsMeteor(targetSpawnPos, spawnedPokemon, world.getLevel(),
                        Config.MIN_METEOR_SPAWN_DISTANCE, Config.MAX_METEOR_SPAWN_DISTANCE, Config.METEOR_SPAWN_HEIGHT, Config.MAX_BIG_METEOR_SIZE, Config.HUGE_METEOR_SIZE_LIMIT);

                message = "message.ohmymeteors.meteor_spawned.huge";
            } else {
                //world mess is because it needs a final variable btw
                message = "message.ohmymeteors.meteor_spawned";
            }
        } else {
            message = "message.ohmymeteors.meteor_spawned";
        }

        if(Config.ANNOUNCE_METEOR_SPAWN && !meteor.isSilenced()){
            if(Config.ANNOUNCE_LOCATION){
                String meteorPos = meteor.blockPosition().getX() + " x, " + meteor.blockPosition().getZ() + " z!";
                world.players().forEach(player -> player.displayClientMessage(Component.literal(OhMyMeteors.PREFIX).append(Component.translatable(message+".localized", meteorPos).withStyle(ChatFormatting.RED)), Config.ACTIONBAR_ANNOUNCEMENTS));
            }else{
                world.players().forEach(player -> player.displayClientMessage(Component.literal(OhMyMeteors.PREFIX).append(Component.translatable(message).withStyle(ChatFormatting.RED)), Config.ACTIONBAR_ANNOUNCEMENTS));
            }
        }

        world.addFreshEntity(meteor);
    }
}

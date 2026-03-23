package me.emafire003.dev.pokemeteors.util;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import me.emafire003.dev.ohmymeteors.OhMyMeteors;
import me.emafire003.dev.ohmymeteors.config.Config;
import me.emafire003.dev.ohmymeteors.util.MeteorUtils;
import me.emafire003.dev.pokemeteors.PlatformSpecificStuff;
import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.entity.PokeMeteorEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class PokemeteorUtils {


    public static List<ResourceLocation> METEOR_STRUCTURES = new ArrayList<>();

    public static void reInitStructures(ServerLevel world){
        METEOR_STRUCTURES = new ArrayList<>(world.getStructureManager().listTemplates().filter(
                identifier -> identifier.getNamespace().equals(PokemeteorsCommon.MOD_ID)
        ).toList());

        METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("error"));

        //this allows to have "ignore_<structure>" to "remove" a default structure with a datapack
        //or "ignoredefault" to have it remove all the structures
        List<ResourceLocation> structures_copy = new ArrayList<>(METEOR_STRUCTURES);

        //the stream is to avoid concurrent modification exception
        structures_copy.forEach(id -> {
            if(id.getPath().contains("ignore_")){
                //If in the root folder, adjust the thingy
                if(id.getPath().startsWith("ignore_")){
                    METEOR_STRUCTURES.remove(ResourceLocation.fromNamespaceAndPath(id.getNamespace(),
                            id.getPath().replaceAll("ignore_", "").split("_")[0]+"/"+id.getPath().replaceAll("ignore_", "")));
                    METEOR_STRUCTURES.remove(id);
                }else{
                    //Removes the targeted structure
                    METEOR_STRUCTURES.remove(ResourceLocation.fromNamespaceAndPath(id.getNamespace(), id.getPath().replaceAll("ignore_", "")));
                    METEOR_STRUCTURES.remove(id);//Since this ignore_structure also needs to be removed
                }

            }
            if(id.getPath().contains("ignoredefault") || id.getPath().contains("ignoredefaults")){
                //TODO remove this mods' defaults
                /*METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("big/special/big_meteor_cat"));
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("small/small_meteor_2"));*/
                METEOR_STRUCTURES.remove(id);

            }
        });

        if(METEOR_STRUCTURES.isEmpty()){
            METEOR_STRUCTURES.add(PokemeteorsCommon.getIdentifier("error"));
            PokemeteorsCommon.LOGGER.error("ERROR! No meteor structures available, you have just removed every default structure! An error meteor structure is all that is going to spawn currently.  Please insert at least one of your custom structures, and reload!");
        }
    }

    /**
     * Gets a meteor object to be spawned in, with a velocity oriented downwards and a spawn position already set up
     * */
    public static PokeMeteorEntity getDownwardsMeteor(Vec3 targetSpawnPos, PokemonEntity spawnedPokemon, ServerLevel world, int min_spawn_d, int max_spawn_d, double spawn_height){
        //TODO this can't really work since it needs to be on fabric/neoforge in a different way
        PokeMeteorEntity meteor = PlatformSpecificStuff.getSinglePokeMeteor(world, targetSpawnPos, spawnedPokemon);
        Tuple<Vec3, Vec3> pos_vel = MeteorUtils.getDownwardsMeteorPosAndVelocity(targetSpawnPos, world, min_spawn_d, max_spawn_d, spawn_height);

        meteor.setPosRaw(pos_vel.getA().x, pos_vel.getA().y, pos_vel.getA().z);

        meteor.setSize(world.getRandom().nextIntBetweenInclusive(Math.max(0, PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getMinMeteorSize(spawnedPokemon)), Math.min(50, PokemeteorsCommon.SPECIES_CHANCE_CONFIG.getMaxMeteorSize(spawnedPokemon))));

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
                Config.MIN_METEOR_SPAWN_DISTANCE, Config.MAX_METEOR_SPAWN_DISTANCE, Config.METEOR_SPAWN_HEIGHT);

        meteor.setSilenced(silenced);

        String message;

        /*if(Config.SPAWN_HUGE_METEORS){
            if(world.getRandom().nextIntBetweenInclusive(0, Config.HUGE_METEOR_CHANCE) == 0){
                meteor = getDownwardsMeteor(targetSpawnPos, spawnedPokemon, world.getLevel(),
                        Config.MIN_METEOR_SPAWN_DISTANCE, Config.MAX_METEOR_SPAWN_DISTANCE, Config.METEOR_SPAWN_HEIGHT);

                message = "message.ohmymeteors.meteor_spawned.huge";
            } else {
                //world mess is because it needs a final variable btw
                message = "message.ohmymeteors.meteor_spawned";
            }
        } else {

        }*/
        message = "message.ohmymeteors.meteor_spawned";

//TODO maybe update with the pokemeteors messages
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

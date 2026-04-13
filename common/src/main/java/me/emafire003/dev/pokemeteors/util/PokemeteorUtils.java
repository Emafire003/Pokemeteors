package me.emafire003.dev.pokemeteors.util;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import me.emafire003.dev.ohmymeteors.config.Config;
import me.emafire003.dev.ohmymeteors.util.MeteorUtils;
import me.emafire003.dev.pokemeteors.PlatformSpecificStuff;
import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.config.ConfigSettings;
import me.emafire003.dev.pokemeteors.entity.PokeMeteorEntity;
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
            if(id.getPath().contains("ignoredefault")){

                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("big/moonstone_big"));
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("huge/kyurem_huge"));
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("medium/sun_medium"));
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("medium/solmeteor"));
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("medium/moon_medium"));
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("medium/lunmeteor"));
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("medium/dusk_medium"));
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("medium/dawn_medium"));
                //Miniors
                List<ResourceLocation> copyList = new ArrayList<>(METEOR_STRUCTURES);
                copyList.forEach( structure -> {
                    if(structure.getPath().startsWith("small/minior/small_")){
                        METEOR_STRUCTURES.remove(structure);
                    }
                });
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("small/tublestone_small"));
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("small/moon_small"));
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("deoxys"));
                METEOR_STRUCTURES.remove(PokemeteorsCommon.getIdentifier("simple_spawn"));
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
     * @param simpleSpawn Weather or not the meteor should spawn a pokémon without a structure*/
    public static void spawnMeteor(ServerLevel world, Vec3 targetSpawnPos, PokemonEntity spawnedPokemon, boolean simpleSpawn){
        PokeMeteorEntity meteor = getDownwardsMeteor(targetSpawnPos, spawnedPokemon, world.getLevel(),
                Config.MIN_METEOR_SPAWN_DISTANCE, Config.MAX_METEOR_SPAWN_DISTANCE, Config.METEOR_SPAWN_HEIGHT);

        meteor.setSimpleSpawn(simpleSpawn);

        if(PlatformSpecificStuff.isModLoaded("yet_another_config_lib_v3")){
            if(ConfigSettings.HANDLER.instance().announcePokemeteorSpawn){
                Component msg = Component.translatable("pokemeteors.announce.spawn");
                if(ConfigSettings.HANDLER.instance().announcePokemonInsideMeteor){
                    msg = Component.literal(msg.getString()).append(Component.translatable("pokemeteors.announce.pokemon", spawnedPokemon.getName()));
                }
                if(ConfigSettings.HANDLER.instance().announceLocation){
                    String meteorPos = meteor.blockPosition().getX() + " x, " + meteor.blockPosition().getZ() + " z";
                    msg = Component.literal(msg.getString()).append(Component.translatable("pokemeteors.announce.location", meteorPos));

                }
                Component finalMsg = Component.empty().append(msg).append(Component.literal("!"));
                world.players().forEach(player -> player.displayClientMessage(Component.literal(PokemeteorsCommon.PREFIX).append(finalMsg), ConfigSettings.HANDLER.instance().announceInActionBar));

            }
        }
        world.addFreshEntity(meteor);
    }
}

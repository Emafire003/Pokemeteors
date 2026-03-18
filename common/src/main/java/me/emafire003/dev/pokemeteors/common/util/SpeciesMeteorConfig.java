package me.emafire003.dev.pokemeteors.common.util;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Species;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO move to a datapack loader thingy
public class SpeciesMeteorConfig {
    List<SpeciesMeteorChance> speciesMeteorChances = new ArrayList<>();

    private HashMap<String, Integer> cache = new HashMap<>();

    public SpeciesMeteorConfig(SpeciesMeteorChance speciesMeteorChance){
        this.speciesMeteorChances.add(speciesMeteorChance);
    }

    public SpeciesMeteorConfig(List<SpeciesMeteorChance> speciesMeteorChances){
        this.speciesMeteorChances = speciesMeteorChances;
    }

    /** Checks if a given speciesMeteorChance is contained in this "map"*/
    public boolean contains(SpeciesMeteorChance speciesMeteorChance){
        return this.speciesMeteorChances.contains(speciesMeteorChance);
    }

    /**Checks if a given pokemon species is contained in this "map"*/
    public boolean contains(Species pokemon){
        return contains(pokemon.getResourceIdentifier());
    }

    /**Checks if a given pokemon species is contained in this "map"*/
    public boolean contains(ResourceLocation pokemon){
        return contains(pokemon.toString());
    }

    public boolean contains(String pokemon){
        if(cache.containsKey(pokemon)){
            return true;
        }
        for(int i = 0; i<this.speciesMeteorChances.size(); i++){
            if(this.speciesMeteorChances.get(i).species.equals(pokemon)){
                cache.put(pokemon, i);
                return true;
            }
        }
        return false;
    }

    public int getChance(String pokemon){
        if(cache.containsKey(pokemon)){
            return this.speciesMeteorChances.get(cache.get(pokemon)).getChance();
        }
        for(int i = 0; i<this.speciesMeteorChances.size(); i++){
            if(this.speciesMeteorChances.get(i).species.equals(pokemon)){
                cache.put(pokemon, i);
                return this.speciesMeteorChances.get(i).getChance();
            }
        }
        return -1;
    }

    public int getChance(ResourceLocation pokemon){
        return getChance(pokemon.toString());
    }

    public int getChance(Species pokemon){
        return getChance(pokemon.getResourceIdentifier());
    }

    public int getChance(PokemonEntity pokemon){
        return getChance(pokemon.getExposedSpecies());
    }

    public int getMinMeteorSize(String pokemon){
        if(cache.containsKey(pokemon)){
            return this.speciesMeteorChances.get(cache.get(pokemon)).getMinMeteorSize();
        }
        for(int i = 0; i<this.speciesMeteorChances.size(); i++){
            if(this.speciesMeteorChances.get(i).species.equals(pokemon)){
                cache.put(pokemon, i);
                return this.speciesMeteorChances.get(i).getMinMeteorSize();
            }
        }
        //default
        return 3;
    }

    public int getMaxMeteorSize(String pokemon){
        if(cache.containsKey(pokemon)){
            return this.speciesMeteorChances.get(cache.get(pokemon)).getMaxMeteorSize();
        }
        for(int i = 0; i<this.speciesMeteorChances.size(); i++){
            if(this.speciesMeteorChances.get(i).species.equals(pokemon)){
                cache.put(pokemon, i);
                return this.speciesMeteorChances.get(i).getMinMeteorSize();
            }
        }
        //default
        return 15;
    }

    public int getMinMeteorSize(ResourceLocation pokemon){
        return getMinMeteorSize(pokemon.toString());
    }

    public int getMaxMeteorSize(ResourceLocation pokemon){
        return getMaxMeteorSize(pokemon.toString());
    }

    public int getMinMeteorSize(Species pokemon){
        return getMinMeteorSize(pokemon.getResourceIdentifier());
    }

    public int getMaxMeteorSize(Species pokemon){
        return getMaxMeteorSize(pokemon.getResourceIdentifier());
    }

    public int getMinMeteorSize(PokemonEntity pokemon){
        return getMinMeteorSize(pokemon.getExposedSpecies());
    }

    public int getMaxMeteorSize(PokemonEntity pokemon){
        return getMaxMeteorSize(pokemon.getExposedSpecies());
    }


}

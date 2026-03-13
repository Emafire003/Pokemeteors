package me.emafire003.dev.pokemeteors.common.util;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Species;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

//TODO will be used for the json containgin the pokemons to spawn with a meteor
public class SpeciesMeteorConfig {
    List<SpeciesMeteorChance> speciesMeteorChances = new ArrayList<>();

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
        for(SpeciesMeteorChance sp : this.speciesMeteorChances){
            if(sp.species.equals(pokemon.getResourceIdentifier())){
                return true;
            }
        }
        return false;
    }

    /**Checks if a given pokemon species is contained in this "map"*/
    public boolean contains(ResourceLocation pokemon){
        for(SpeciesMeteorChance sp : this.speciesMeteorChances){
            if(sp.species.equals(pokemon)){
                return true;
            }
        }
        return false;
    }

    public int getChance(Species pokemon){
        for(SpeciesMeteorChance sp : this.speciesMeteorChances){
            if(sp.species.equals(pokemon.getResourceIdentifier())){
                return sp.getChance();
            }
        }
        return -1;
    }

    public int getChance(ResourceLocation pokemon){
        for(SpeciesMeteorChance sp : this.speciesMeteorChances){
            if(sp.species.equals(pokemon)){
                return sp.getChance();
            }
        }
        return -1;
    }

    public int getChance(PokemonEntity pokemon){
        for(SpeciesMeteorChance sp : this.speciesMeteorChances){
            if(sp.species.equals(pokemon.getExposedSpecies().getResourceIdentifier())){
                return sp.getChance();
            }
        }
        return -1;
    }
}

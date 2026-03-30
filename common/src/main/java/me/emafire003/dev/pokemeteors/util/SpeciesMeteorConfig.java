package me.emafire003.dev.pokemeteors.util;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Species;
import com.google.gson.annotations.Expose;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.emafire003.dev.ohmymeteors.util.MeteorSizeClass;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO move to a datapack loader thingy
public class SpeciesMeteorConfig {
    @Expose
    List<SpeciesMeteorChance> speciesMeteorChances = new ArrayList<>();
    @Expose
    private int version_do_not_touch = 1;
    HashMap<String, Integer> cache = new HashMap<>();

    public static Codec<SpeciesMeteorConfig> CODEC = RecordCodecBuilder.create(
            instance ->
                    instance.group(Codec.INT.fieldOf("version_do_not_touch")
                                            .forGetter(SpeciesMeteorConfig::getVersion_do_not_touch),
                                    Codec.list(SpeciesMeteorChance.CODEC).fieldOf("speciesMeteorChances")
                                            .forGetter(SpeciesMeteorConfig::getSpeciesMeteorChances)
                            )
                            .apply(instance, SpeciesMeteorConfig::new));

    public SpeciesMeteorConfig(SpeciesMeteorChance speciesMeteorChance){
        this.speciesMeteorChances.add(speciesMeteorChance);
        cache = new HashMap<>();
    }

    public SpeciesMeteorConfig(List<SpeciesMeteorChance> speciesMeteorChances){
        this.speciesMeteorChances = speciesMeteorChances;
        cache = new HashMap<>();
    }

    //used by codec
    public SpeciesMeteorConfig(int version, List<SpeciesMeteorChance> speciesMeteorChances) {
        this.version_do_not_touch = version;
        this.speciesMeteorChances = speciesMeteorChances;
    }

    /** Checks if a given speciesMeteorChance is contained in this "map"*/
    public boolean contains(SpeciesMeteorChance speciesMeteorChance){
        return this.speciesMeteorChances.contains(speciesMeteorChance);
    }

    public List<SpeciesMeteorChance> getSpeciesMeteorChances() {
        return speciesMeteorChances;
    }

    public int getVersion_do_not_touch() {
        return version_do_not_touch;
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
        if(cache == null){
            cache = new HashMap<>();
        }
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
        return 5;
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
        if(pokemon == null){
            return 3;
        }
        return getMinMeteorSize(pokemon.getExposedSpecies());
    }

    public int getMaxMeteorSize(PokemonEntity pokemon){
        if(pokemon == null){
            return 5;
        }
        return getMaxMeteorSize(pokemon.getExposedSpecies());
    }

    public MeteorSizeClass getSizeClass(String pokemon){
        if(cache.containsKey(pokemon)){
            return this.speciesMeteorChances.get(cache.get(pokemon)).getSizeClass();
        }
        for(int i = 0; i<this.speciesMeteorChances.size(); i++){
            if(this.speciesMeteorChances.get(i).species.equals(pokemon)){
                cache.put(pokemon, i);
                return this.speciesMeteorChances.get(i).getSizeClass();
            }
        }
        return MeteorSizeClass.SMALL;
    }

    public MeteorSizeClass getSizeClass(ResourceLocation pokemon){
        return getSizeClass(pokemon.toString());
    }

    public MeteorSizeClass getSizeClass(Species pokemon){
        return getSizeClass(pokemon.getResourceIdentifier());
    }

    public MeteorSizeClass getSizeClass(PokemonEntity pokemon){
        if(pokemon == null){
            return MeteorSizeClass.SMALL;
        }
        return getSizeClass(pokemon.getExposedSpecies());
    }

    public String getSpecialMeteor(String pokemon){
        if(cache.containsKey(pokemon)){
            return this.speciesMeteorChances.get(cache.get(pokemon)).getUniqueMeteor();
        }
        for(int i = 0; i<this.speciesMeteorChances.size(); i++){
            if(this.speciesMeteorChances.get(i).species.equals(pokemon)){
                cache.put(pokemon, i);
                return this.speciesMeteorChances.get(i).getUniqueMeteor();
            }
        }
        return "";
    }

    public String getSpecialMeteor(ResourceLocation pokemon){
        return getSpecialMeteor(pokemon.toString());
    }

    public String getSpecialMeteor(Species pokemon){
        return getSpecialMeteor(pokemon.getResourceIdentifier());
    }

    public String getSpecialMeteor(PokemonEntity pokemon){
        if(pokemon == null){
            return "";
        }
        return getSpecialMeteor(pokemon.getExposedSpecies());
    }

    @Nullable
    public Map<String, String> getAspectUniqueMeteor(String pokemon){
        if(cache.containsKey(pokemon)){
            return this.speciesMeteorChances.get(cache.get(pokemon)).getAspectUniqueMeteor();
        }
        for(int i = 0; i<this.speciesMeteorChances.size(); i++){
            if(this.speciesMeteorChances.get(i).species.equals(pokemon)){
                cache.put(pokemon, i);
                return this.speciesMeteorChances.get(i).getAspectUniqueMeteor();
            }
        }
        return null;
    }

    public Map<String, String> getAspectUniqueMeteor(ResourceLocation pokemon){
        return getAspectUniqueMeteor(pokemon.toString());
    }

    public Map<String, String> getAspectUniqueMeteor(Species pokemon){
        return getAspectUniqueMeteor(pokemon.getResourceIdentifier());
    }

    public Map<String, String> getAspectUniqueMeteor(PokemonEntity pokemon){
        if(pokemon == null){
            return null;
        }
        return getAspectUniqueMeteor(pokemon.getExposedSpecies());
    }


}

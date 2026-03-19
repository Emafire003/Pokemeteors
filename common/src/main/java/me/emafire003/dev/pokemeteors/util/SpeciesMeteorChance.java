package me.emafire003.dev.pokemeteors.util;

import com.google.gson.annotations.Expose;
import me.emafire003.dev.ohmymeteors.util.MeteorSizeClass;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class SpeciesMeteorChance {
    @Expose
    String species;
    @Expose
    int chance;
    @Expose
    int max_meteor_size = 5;
    @Expose
    int min_meteor_size = 2;
    @Expose
    MeteorSizeClass meteor_size_class;
    @Expose
    String special_meteor;
    List<ResourceLocation> biome_filter;

    public SpeciesMeteorChance() {
    }

    public int getMaxMeteorSize() {
        return max_meteor_size;
    }

    public void setMaxMeteorSize(int max_meteor_size) {
        this.max_meteor_size = max_meteor_size;
    }

    public int getMinMeteorSize() {
        return min_meteor_size;
    }

    public void setMinMeteorSize(int min_meteor_size) {
        this.min_meteor_size = min_meteor_size;
    }

    public SpeciesMeteorChance(String species, int chance) {
        this.species = species;
        this.chance = chance;
    }

    //TODO maybe add a biome/dimension filter somewhere?


    public MeteorSizeClass getSizeClass() {
        return meteor_size_class;
    }

    public void setMeteorSizeClass(MeteorSizeClass meteor_size_class) {
        this.meteor_size_class = meteor_size_class;
    }

    public SpeciesMeteorChance(String species, int chance, int max_meteor_size, int min_meteor_size, MeteorSizeClass meteor_size_class, String special_meteor) {
        this.species = species;
        this.chance = chance;
        this.max_meteor_size = max_meteor_size;
        this.min_meteor_size = min_meteor_size;
        this.meteor_size_class = meteor_size_class;
        this.special_meteor = special_meteor;
    }

    public String getSpecialMeteor() {
        return special_meteor;
    }

    public void setSpecialMeteor(String special_meteor) {
        this.special_meteor = special_meteor;
    }

    public SpeciesMeteorChance(String species, int chance, int max_meteor_size, int min_meteor_size, List<ResourceLocation> biome_filter) {
        this.species = species;
        this.chance = chance;
        this.max_meteor_size = max_meteor_size;
        this.min_meteor_size = min_meteor_size;
        this.biome_filter = biome_filter;
    }

    public ResourceLocation getSpecies() {
        return ResourceLocation.tryParse(species);
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setSpecies(ResourceLocation species) {
        this.species = species.toString();
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}

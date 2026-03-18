package me.emafire003.dev.pokemeteors.common.util;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class SpeciesMeteorChance {
    String species;
    int chance;
    int max_meteor_size = 15;
    int min_meteor_size = 2;
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

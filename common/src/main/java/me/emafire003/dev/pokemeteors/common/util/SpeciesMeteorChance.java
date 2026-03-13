package me.emafire003.dev.pokemeteors.common.util;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class SpeciesMeteorChance {
    ResourceLocation species;
    int chance;
    List<ResourceLocation> biome_filter;

    public SpeciesMeteorChance() {
    }

    public SpeciesMeteorChance(ResourceLocation species, int chance) {
        this.species = species;
        this.chance = chance;
    }

    public SpeciesMeteorChance(ResourceLocation species, int chance, List<ResourceLocation> biome_filter) {
        this.species = species;
        this.chance = chance;
        this.biome_filter = biome_filter;
    }
    //TODO maybe add a biome/dimension filter somewhere?


    public ResourceLocation getSpecies() {
        return species;
    }

    public void setSpecies(ResourceLocation species) {
        this.species = species;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}

package me.emafire003.dev.pokemeteors.util;

import com.google.gson.annotations.Expose;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import me.emafire003.dev.ohmymeteors.util.MeteorSizeClass;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeciesMeteorChance {

    public static Codec<SpeciesMeteorChance> CODEC = RecordCodecBuilder.create(
            instance ->
                    instance.group(Codec.STRING.fieldOf("species")
                                            .forGetter(SpeciesMeteorChance::species),
                                    Codec.INT.fieldOf("chance")
                                            .forGetter(SpeciesMeteorChance::getChance),
                                    Codec.INT.fieldOf("max_meteor_size")
                                            .forGetter(SpeciesMeteorChance::getMaxMeteorSize),
                                    Codec.INT.fieldOf("min_meteor_size")
                                            .forGetter(SpeciesMeteorChance::getMinMeteorSize),
                                    MeteorSizeClass.CODEC.fieldOf("meteor_size_class")
                                            .forGetter(SpeciesMeteorChance::getSizeClass),
                                    Codec.STRING.fieldOf("unique_meteor")
                                            .forGetter(SpeciesMeteorChance::getUniqueMeteor),
                                    Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("aspect_unique_meteor")
                                            .forGetter(SpeciesMeteorChance::getAspectUniqueMeteor)
                            )
                            .apply(instance, SpeciesMeteorChance::new));

    public static StreamCodec<ByteBuf, SpeciesMeteorChance> PACKET_CODEC = ByteBufCodecs.fromCodec(CODEC);

    /// The pokemon's species formatted as an identifier so "cobblemon:minior" for example
    @Expose
    String species;
    /// The 1 in x chances that each time that pokemon spawns will be along a meteor (1 in 1 is always)
    @Expose
    int chance;
    /// The max meteor size that can spawn with this pokemon
    @Expose
    int max_meteor_size = 5;
    /// The min meteor size that can spawn with this pokemon
    @Expose
    int min_meteor_size = 2;
    /// The {@link MeteorSizeClass} of meteor that can spawn with this pokemon. These kind of structures are placed inside
    /// "data/pokemeteors/structure/small" or /big or /medium etc like in ohmymeteors
    @Expose
    MeteorSizeClass meteor_size_class;
    /// A string representing an identifier a specific meteor structure that should spawn along with this pokemon, like "pokemeteors:deoxys_special"
    /// Or a folder containing a set of specific meteors like "minior". These will be searched in  "data/pokemeteors/structure/'meteor_size_class'/minior/"
    @Expose
    String unique_meteor;
    /// A map of Aspect:Unique meteor, for example the minior line thingy
    @Expose
    HashMap<String, String> aspect_unique_meteor;


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

    public Map<String, String> getAspectUniqueMeteor() {
        return aspect_unique_meteor;
    }

    public void setAspectUniqueMeteor(HashMap<String, String> aspect_unique_meteor) {
        this.aspect_unique_meteor = aspect_unique_meteor;
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

    public SpeciesMeteorChance(String species, int chance, int max_meteor_size, int min_meteor_size, MeteorSizeClass meteor_size_class, String unique_meteor) {
        this.species = species;
        this.chance = chance;
        this.max_meteor_size = max_meteor_size;
        this.min_meteor_size = min_meteor_size;
        this.meteor_size_class = meteor_size_class;
        this.unique_meteor = unique_meteor;
    }

    //used by the codec
    public SpeciesMeteorChance(String species, int chance, int max_meteor_size, int min_meteor_size, MeteorSizeClass meteor_size_class, String unique_meteor, Map<String, String> aspect_unique_meteor) {
        this.species = species;
        this.chance = chance;
        this.max_meteor_size = max_meteor_size;
        this.min_meteor_size = min_meteor_size;
        this.meteor_size_class = meteor_size_class;
        this.unique_meteor = unique_meteor;
        this.aspect_unique_meteor = new HashMap<>(aspect_unique_meteor);
    }

    public SpeciesMeteorChance(String species, int chance, int max_meteor_size, int min_meteor_size, MeteorSizeClass meteor_size_class, String unique_meteor, HashMap<String, String> aspect_unique_meteor) {
        this.species = species;
        this.chance = chance;
        this.max_meteor_size = max_meteor_size;
        this.min_meteor_size = min_meteor_size;
        this.meteor_size_class = meteor_size_class;
        this.unique_meteor = unique_meteor;
        this.aspect_unique_meteor = aspect_unique_meteor;
    }

    public String getUniqueMeteor() {
        return unique_meteor;
    }

    public void setSpecialMeteor(String special_meteor) {
        this.unique_meteor = special_meteor;
    }

    public SpeciesMeteorChance(String species, int chance, int max_meteor_size, int min_meteor_size, List<ResourceLocation> biome_filter) {
        this.species = species;
        this.chance = chance;
        this.max_meteor_size = max_meteor_size;
        this.min_meteor_size = min_meteor_size;
    }

    public ResourceLocation getSpecies() {
        return ResourceLocation.tryParse(species);
    }

    public String species(){
        return species;
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

    @Override
    public String toString() {
        return "SpeciesMeteorChance{" +
                "species='" + species + '\'' +
                ", chance=" + chance +
                ", max_meteor_size=" + max_meteor_size +
                ", min_meteor_size=" + min_meteor_size +
                ", meteor_size_class=" + meteor_size_class +
                ", unique_meteor='" + unique_meteor + '\'' +
                ", aspect_unique_meteor=" + aspect_unique_meteor +
                '}';
    }
}

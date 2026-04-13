package me.emafire003.dev.pokemeteors.fabric.entity;

import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.entity.PokeMeteorEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static me.emafire003.dev.ohmymeteors.entities.OMMEntities.getMeteorTrackingDistance;

public class PKMFabricEntities {

    public static final EntityType<PokeMeteorEntity> POKE_METEOR = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            PokemeteorsCommon.getIdentifier("pokemeteor"),
            EntityType.Builder.<PokeMeteorEntity>of(PokeMeteorEntity::new, MobCategory.MISC)
                    .clientTrackingRange(getMeteorTrackingDistance())
                    .sized(0.9F, 0.9F).build("pokemeteor"));

    public static void registerEntities(){
        //FabricDefaultAttributeRegistry.register(METEOR_KITTY_CAT, MeteorCatEntity.createCatAttributes());
    }
}

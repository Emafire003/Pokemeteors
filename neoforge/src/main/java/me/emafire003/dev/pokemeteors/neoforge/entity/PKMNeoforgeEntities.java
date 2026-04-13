package me.emafire003.dev.pokemeteors.neoforge.entity;

import me.emafire003.dev.ohmymeteors.config.Config;
import me.emafire003.dev.pokemeteors.PokemeteorsCommon;
import me.emafire003.dev.pokemeteors.entity.PokeMeteorEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class PKMNeoforgeEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, PokemeteorsCommon.MOD_ID);

    public static final Supplier<EntityType<PokeMeteorEntity>> POKEMETEOR_ENTITY =
            ENTITY_TYPES.register(
            "pokemeteor", () ->
            EntityType.Builder.<PokeMeteorEntity>of(PokeMeteorEntity::new, MobCategory.MISC)
                    .clientTrackingRange(getMeteorTrackingDistance())
                    .sized(0.9F, 0.9F).build("pokemeteor"));

    public static int getMeteorTrackingDistance(){
        Config.reloadConfig();
        return Config.METEOR_RENDER_DISTANCE;
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

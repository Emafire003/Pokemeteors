package me.emafire003.dev.pokemeteors.entity.client;

import me.emafire003.dev.ohmymeteors.OhMyMeteors;
import me.emafire003.dev.ohmymeteors.entities.client.MeteorProjectileEntityRenderer;
import me.emafire003.dev.pokemeteors.entity.PokeMeteorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PokeMeteorEntityRenderer<T extends PokeMeteorEntity> extends MeteorProjectileEntityRenderer<T> {

    public PokeMeteorEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param entity The entity of the renderer (aka PokeMeteor entity)
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(T entity) {
        //TODO change texture                        (vvv and modid vvv) (maybe, i don't know tbf)
        return ResourceLocation.fromNamespaceAndPath(OhMyMeteors.MOD_ID, "textures/block/meteoric_rock.png");
    }
}
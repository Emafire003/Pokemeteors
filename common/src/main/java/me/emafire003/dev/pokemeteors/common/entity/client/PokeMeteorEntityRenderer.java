package me.emafire003.dev.pokemeteors.common.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.emafire003.dev.ohmymeteors.OhMyMeteors;
import me.emafire003.dev.pokemeteors.common.entity.PokeMeteorEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PokeMeteorEntityRenderer extends EntityRenderer<PokeMeteorEntity> {
    protected PokeMeteorEntityModel model;

    public PokeMeteorEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new PokeMeteorEntityModel(ctx.bakeLayer(PokeMeteorEntityModel.METEOR));
    }

    @Override
    public void render(PokeMeteorEntity entity, float yaw, float tickDelta, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light) {

        //TODO change texture and the size
        matrices.pushPose();
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(vertexConsumers,
                this.model.renderType(getTextureLocation(entity)), false, false);

        matrices.translate(0, -entity.getDimensions(entity.getPose()).height()/1.5, 0);

        matrices.scale(entity.getSize(), entity.getSize(), entity.getSize());

        this.model.renderToBuffer(matrices, vertexconsumer, light, OverlayTexture.NO_OVERLAY);

        matrices.popPose();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param entity The entity of the renderer (aka PokeMeteor entity)
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(PokeMeteorEntity entity) {
        //TODO change texture                        (vvv and modid vvv)
        return ResourceLocation.fromNamespaceAndPath(OhMyMeteors.MOD_ID, "textures/block/meteoric_rock.png");
    }
}
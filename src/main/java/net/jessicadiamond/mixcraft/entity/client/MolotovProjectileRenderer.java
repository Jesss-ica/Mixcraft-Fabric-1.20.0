package net.jessicadiamond.mixcraft.entity.client;

import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.entity.custom.MolotovProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class MolotovProjectileRenderer extends EntityRenderer<MolotovProjectileEntity> {

    protected MolotovProjectileModel model;

    public MolotovProjectileRenderer(EntityRendererFactory.Context ctx ){
        super(ctx);
        this.model = new MolotovProjectileModel(ctx.getPart(MolotovProjectileModel.MOLOTOV));
    }

    @Override
    public void render(MolotovProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        VertexConsumer vertexconsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers,
                this.model.getLayer(Identifier.of(MixCraft.MOD_ID, "textures/entity/molotov/molotov.png")), false, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(MolotovProjectileEntity entity){
        return Identifier.of(MixCraft.MOD_ID, "textures/entity/molotov/molotov.png");
    }
}

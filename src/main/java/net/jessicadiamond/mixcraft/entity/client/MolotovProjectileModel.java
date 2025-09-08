package net.jessicadiamond.mixcraft.entity.client;

import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.entity.custom.MolotovProjectileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import javax.swing.text.html.parser.Entity;

public class MolotovProjectileModel extends EntityModel<MolotovProjectileEntity>{
    public static final EntityModelLayer MOLOTOV = new EntityModelLayer(Identifier.of(MixCraft.MOD_ID, "molotov"), "main");

    private final ModelPart Molotov;


    public MolotovProjectileModel(ModelPart root) {
        this.Molotov = root.getChild("Molotov");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData Molotov = modelPartData.addChild("Molotov", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 0.0F, -1.5F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 11).cuboid(-1.0F, -3.0F, -1.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, 17.0F, -0.5F));

        ModelPartData cube_r1 = Molotov.addChild("cube_r1", ModelPartBuilder.create().uv(16, 5).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.9F, 0.5F, 0.5F, -0.0058F, 0.0043F, -0.9688F));

        ModelPartData cube_r2 = Molotov.addChild("cube_r2", ModelPartBuilder.create().uv(12, 11).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.2F, 0.1F, 0.5F, -0.0017F, 0.007F, -0.2707F));

        ModelPartData cube_r3 = Molotov.addChild("cube_r3", ModelPartBuilder.create().uv(16, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -2.3F, 0.5F, 0.0F, 0.0F, 0.6981F));
        return TexturedModelData.of(modelData, 32, 32);
    }


    @Override
    public void setAngles(MolotovProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        Molotov.render(matrices, vertexConsumer, light, overlay, color);
    }
}

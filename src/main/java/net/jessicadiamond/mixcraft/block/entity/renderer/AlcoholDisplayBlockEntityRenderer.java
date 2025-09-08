package net.jessicadiamond.mixcraft.block.entity.renderer;

import net.jessicadiamond.mixcraft.block.custom.AlcoholDisplayBlock;
import net.jessicadiamond.mixcraft.block.entity.custom.AlcoholDisplayBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class AlcoholDisplayBlockEntityRenderer implements BlockEntityRenderer<AlcoholDisplayBlockEntity> {


    public AlcoholDisplayBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){

    }

    @Override
    public void render(AlcoholDisplayBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        //ItemStack stack = entity.getStack(0);

        matrices.push();
        matrices.translate(0f, 0.6f, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);
        //matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getRenderingRotation()));

        for(int i = 0; i < entity.MAX_SIZE; ++i){;
            matrices.translate(1f,0f,0f);
            itemRenderer.renderItem(entity.getStack(i), ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),
                    entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), i);
        }
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos){
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight,sLight);
    }
}

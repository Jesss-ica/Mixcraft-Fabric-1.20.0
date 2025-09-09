package net.jessicadiamond.mixcraft.block.entity.renderer;

import net.jessicadiamond.mixcraft.block.custom.AlcoholDisplayBlock;
import net.jessicadiamond.mixcraft.block.entity.custom.AlcoholDisplayBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
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
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class AlcoholDisplayBlockEntityRenderer implements BlockEntityRenderer<AlcoholDisplayBlockEntity> {


    // ========== Item Pos For Displays



    public AlcoholDisplayBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){

    }

    @Override
    public void render(AlcoholDisplayBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        //ItemStack stack = entity.getStack(0);

        matrices.push();
        matrices.scale(1f, 1f, 1f);

        // == CREATE A BETTER SOLUTION
        if(entity.getFacingRotation() == 0f){
            matrices.translate(0f,0f,0f);
        }else if(entity.getFacingRotation() == 90f ){
            matrices.translate(0f, 0f, 1f);
        }else if(entity.getFacingRotation() == 180f ){
            matrices.translate(1f, 0f, 1f);
        }else if(entity.getFacingRotation() == 270f ){
            matrices.translate(1f, 0f, 0f);
        }

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getFacingRotation()));

        matrices.translate(0.3f, 0.13f, 0.3f);

        int k = 0;

        for(int i = 0; i < 2; ++i){
            for(int j = 0; j < 2; j++){
                if(!entity.getStack(k).isEmpty()){
                    matrices.translate(0.4f * j, 0.195f * i, 0.4f * i);
                    if(entity.getStack(k).getItem() instanceof BlockItem){
                        itemRenderer.renderItem(entity.getStack(k), ModelTransformationMode.GROUND, getLightLevel(entity.getWorld(),
                                entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), i);
                    }
                    matrices.translate(-0.4f * j, -0.195f * i, -0.4f * i);
                }
                ++k;
            }
        }



//        for(int i = 0; i < entity.MAX_SIZE; ++i){
//            if(!entity.getStack(i).isEmpty() && entity.getStack(i).getItem() instanceof BlockItem){
//                matrices.translate(0.3f * i, 0.2f, 0.3f);
//                itemRenderer.renderItem(entity.getStack(i), ModelTransformationMode.GROUND, getLightLevel(entity.getWorld(),
//                        entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), i);
//                matrices.translate(-0.3f * i, -0.2f, -0.3f);
//                //break;
//            }
//        }

        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos){
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight,sLight);
    }
}

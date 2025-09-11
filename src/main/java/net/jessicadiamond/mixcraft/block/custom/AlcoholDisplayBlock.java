package net.jessicadiamond.mixcraft.block.custom;

import com.mojang.serialization.MapCodec;
import net.jessicadiamond.mixcraft.block.entity.custom.AlcoholDisplayBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Overwrite;

import javax.swing.*;
import java.util.List;

public class AlcoholDisplayBlock extends BlockWithEntity{

    public static final MapCodec<AlcoholDisplayBlock> CODEC = AlcoholDisplayBlock.createCodec(AlcoholDisplayBlock::new);

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    // ======== Different Collider Shapes
    private static final VoxelShape BASE_SHAPE =
            Block.createCuboidShape(0f,0f,0f,16.0f,3.0f,16.0f);
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0f,3f,8f,16.0f,6.0f,16f),
            BASE_SHAPE);
    public static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0f,3f,0f,8.0f,6.0f,16f),
            BASE_SHAPE);
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0f,3f,0f,16.0f,6.0f,8f),
            BASE_SHAPE);
    public static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(8f,3f,0f,16.0f,6.0f,16f),
            BASE_SHAPE);
    // ======= Occupied Slots


    public AlcoholDisplayBlock(Settings settings) {
        super(settings);
        BlockState blockState = this.stateManager.getDefaultState().with(FACING, Direction.NORTH);
        this.setDefaultState(blockState);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch ((Direction)state.get(FACING)) {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return BASE_SHAPE;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AlcoholDisplayBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved){
        if(state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof AlcoholDisplayBlockEntity){
                ItemScatterer.spawn(world,pos, ((AlcoholDisplayBlockEntity) blockEntity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state,world,pos,newState,moved);
        }
    }

//    @Override
//    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world,
//                                             BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit){
//        if(world.getBlockEntity(pos) instanceof AlcoholDisplayBlockEntity alcoholDisplayBlockEntity) {
//            if(alcoholDisplayBlockEntity.isEmpty() && !stack.isEmpty()) {
//                alcoholDisplayBlockEntity.setStack(0, stack.copyWithCount(1));
//                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);
//                stack.decrement(1);
//
//                alcoholDisplayBlockEntity.markDirty();
//                world.updateListeners(pos, state, state, 0);
//            } else if(stack.isEmpty() && !player.isSneaking()) {
//                ItemStack stackOnAlcoholDisplay = alcoholDisplayBlockEntity.getStack(0);
//                player.setStackInHand(Hand.MAIN_HAND, stackOnAlcoholDisplay);
//                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
//                alcoholDisplayBlockEntity.clear();
//
//                alcoholDisplayBlockEntity.markDirty();
//                world.updateListeners(pos, state, state, 0);
//            } else if(player.isSneaking() && !world.isClient()){
//                player.openHandledScreen(alcoholDisplayBlockEntity);
//            }
//        }
//
//        return ItemActionResult.SUCCESS;
//    }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit){
        if(world.getBlockEntity(pos) instanceof AlcoholDisplayBlockEntity alcoholDisplayBlockEntity) {
            player.openHandledScreen(alcoholDisplayBlockEntity);
        }
        return ActionResult.SUCCESS;
    }
}

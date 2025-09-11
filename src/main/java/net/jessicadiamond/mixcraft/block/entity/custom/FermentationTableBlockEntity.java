package net.jessicadiamond.mixcraft.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.jessicadiamond.mixcraft.block.ModBlocks;
import net.jessicadiamond.mixcraft.block.entity.ImplementedInventory;
import net.jessicadiamond.mixcraft.block.entity.ModBlockEntities;
import net.jessicadiamond.mixcraft.item.ModItems;
import net.jessicadiamond.mixcraft.recipe.FermentationTableRecipe;
import net.jessicadiamond.mixcraft.recipe.FermentationTableRecipeInput;
import net.jessicadiamond.mixcraft.recipe.ModRecipes;
import net.jessicadiamond.mixcraft.screen.custom.FermentationTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FermentationTableBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    private static final int ITEM_SLOT = 0;
    private static final int BOTTLE_SLOT = 1;
    private static final int FUEL_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;

    protected PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public FermentationTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FERMENTATION_TABLE_BE, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FermentationTableBlockEntity.this.progress;
                    case 1 -> FermentationTableBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: FermentationTableBlockEntity.this.progress = value;
                    case 1: FermentationTableBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.mixcraft.fermentation_table");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FermentationTableScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("fermentation_table.progress", progress);
        nbt.putInt("fermentation_table.max_progress", maxProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("fermentation_table.progress");
        maxProgress = nbt.getInt("fermentation_table.max_progress");
        super.readNbt(nbt, registryLookup);
    }


    public void tick(World world, BlockPos pos, BlockState state) {
        if(hasRecipe()){
            increaseCraftingProgress();
            markDirty(world, pos, state);

            if(hasCraftingFinished()){
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {this.progress = 0; this.maxProgress = 72;}

    private void craftItem() {
        Optional<RecipeEntry<FermentationTableRecipe>> recipe = getCurrentRecipe();

        ItemStack output = recipe.get().value().output();
        this.setStack(OUTPUT_SLOT, new ItemStack(output.getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + output.getCount()));

        this.removeStack(ITEM_SLOT, 1);
        this.removeStack(BOTTLE_SLOT, 1);
        this.removeStack(FUEL_SLOT, 1);
    }

    private boolean hasCraftingFinished() {return this.progress >= this.maxProgress;}

    private void increaseCraftingProgress() { this.progress++;}
    
    private boolean hasRecipe() {
        Optional<RecipeEntry<FermentationTableRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()){
            return false;
        }

        ItemStack output = recipe.get().value().output();

        return canInsertAmountIntoOutputSlot(output.getCount()) &&
                canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeEntry<FermentationTableRecipe>> getCurrentRecipe() {
        return this.getWorld().getRecipeManager()
                .getFirstMatch(ModRecipes.FERMENTATION_TABLE_TYPE, new FermentationTableRecipeInput(
                        inventory.get(ITEM_SLOT),
                        inventory.get(BOTTLE_SLOT),
                        inventory.get(FUEL_SLOT)),
                        this.getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() ||
                this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }


    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}

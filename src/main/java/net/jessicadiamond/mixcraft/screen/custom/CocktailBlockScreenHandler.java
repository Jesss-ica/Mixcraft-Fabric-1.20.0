package net.jessicadiamond.mixcraft.screen.custom;

import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.block.entity.custom.CocktailBlockEntity;
import net.jessicadiamond.mixcraft.block.entity.custom.FermentationTableBlockEntity;
import net.jessicadiamond.mixcraft.components.AlcoholComponent;
import net.jessicadiamond.mixcraft.components.ModComponents;
import net.jessicadiamond.mixcraft.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CocktailBlockScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    public final CocktailBlockEntity blockEntity;

    public CocktailBlockScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos));
    }

    public CocktailBlockScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        super(ModScreenHandlers.COCKTAIL_BLOCK_SCREEN_HANDLER, syncId);
        this.inventory = ((Inventory) blockEntity);
        this.blockEntity = ((CocktailBlockEntity) blockEntity);

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        this.addSlot(new Slot(inventory, 0, 20, 30));
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }


    /// =======
    ///  SHAKER TANK SHANANIGANS
    /// =======

    public void addToTank(int amount){
        if(tankToOverflow(amount)){
            ItemStack stack = this.blockEntity.getStack(0);
            AlcoholComponent alcoholComponent = stack.get(ModComponents.ALCOHOL_COMPONENT);

            if(alcoholComponent != null && wontDrainNegative(amount, alcoholComponent)){
                stack.set(ModComponents.ALCOHOL_COMPONENT, new AlcoholComponent(alcoholComponent.millilitres() - amount, alcoholComponent.unitsPerShot(), alcoholComponent.ABV()));
                blockEntity.increaseCurrentFill(amount);
            }
        }
    }

    public int getScaledTankFill(){
        int currentFill = blockEntity.getCurrentFill();
        int maxFill = blockEntity.MAX_TANK_SIZE;
        int tankPixelSize = 56;

        return maxFill != 0 && currentFill != 0 ? currentFill * tankPixelSize / maxFill : 0;

    }

    private boolean wontDrainNegative(int amount, AlcoholComponent alcoholComponent){
        return  0 <= alcoholComponent.millilitres() - amount;
    }

    public boolean tankToOverflow(int amount){
        return blockEntity.MAX_TANK_SIZE >= blockEntity.CURRENT_FILL + amount;
    }



    /// ====
    ///  End of tank shananigans
    /// ====

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}

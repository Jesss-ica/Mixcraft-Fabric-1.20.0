package net.jessicadiamond.mixcraft.screen.custom;

import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.block.entity.custom.CocktailBlockEntity;
import net.jessicadiamond.mixcraft.components.AlcoholComponent;
import net.jessicadiamond.mixcraft.components.ModComponents;
import net.jessicadiamond.mixcraft.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;

public class CocktailBlockScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    public final CocktailBlockEntity blockEntity;

    private int tankMergeTarget;

    public CocktailBlockScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos));

    }

    public CocktailBlockScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        super(ModScreenHandlers.COCKTAIL_BLOCK_SCREEN_HANDLER, syncId);
        this.inventory = ((Inventory) blockEntity);
        this.blockEntity = ((CocktailBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory, 0, 22, 34));

        this.addSlot(new Slot(inventory, 1, 137, 34));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }



    /// =======
    ///  SHAKER TANK SHANANIGANS
    /// =======
    ///
    public int getTankSize(){ return blockEntity.tankEntries.size(); }

    public void addToTank(int amount){
        if(tankToOverflow(amount)){
            ItemStack stack = this.blockEntity.getStack(0);
            AlcoholComponent alcoholComponent = stack.get(ModComponents.ALCOHOL_COMPONENT);
            if(alcoholComponent != null && wontDrainNegative(amount, alcoholComponent)){
                if(canMerge(stack.getItem())){
                    int newAmount = this.blockEntity.tankEntries.get(tankMergeTarget).amount + amount;
                    this.blockEntity.tankEntries.get(tankMergeTarget).setAmount(newAmount);
                    this.blockEntity.increaseCurrentFill(amount);
                    blockEntity.updateItemStack(stack, alcoholComponent, amount);
                }else{
                    this.blockEntity.updateItemStack(stack, alcoholComponent, amount);
                    this.blockEntity.increaseCurrentFill(amount);
                    this.blockEntity.addToTank(stack.getItem(), amount, stringToColor(alcoholComponent.color()));
                }
            }
        }
    }


    public CocktailBlockEntity.TankComponent getTankEntry(int tankPos){
        return blockEntity.tankEntries.get(tankPos);
    }

    private boolean canMerge(Item itm){
        for(int i = 0; i < this.blockEntity.tankEntries.size(); ++i){
            if(this.blockEntity.tankEntries.get(i).alcohol == itm){
                this.tankMergeTarget = i;
                return true;
            }
        }
        return false;
    }

    public int getScaledTypeFill(int i){
        int fillAmount = blockEntity.tankEntries.get(i).amount;
        int maxFill = blockEntity.MAX_TANK_SIZE;
        int tankPixelSize = 56;

        return maxFill != 0 && fillAmount != 0 ? fillAmount * tankPixelSize / maxFill : 0;
    }

    public int getScaledTankFill(){
        int currentFill = blockEntity.getCurrentFill();
        int maxFill = blockEntity.MAX_TANK_SIZE;
        int tankPixelSize = 56;

        return maxFill != 0 && currentFill != 0 ? currentFill * tankPixelSize / maxFill : 0;
    }

    public float[] getFillColorRGB(String color){
        return stringToColor(color).getRGBColorComponents(null);
    }

    private Color stringToColor(String color){
        switch (color){
            case "WHITE":
                return Color.white;
            case "RED":
                return Color.red;
            case "YELLOW":
                return Color.yellow;
            case "BROWN":
                return new Color(139,69,19);
            default:
                return Color.white;
        }
    }

    private boolean wontDrainNegative(int amount, AlcoholComponent alcoholComponent){
        return  0 <= alcoholComponent.millilitres() - amount;
    }

    public boolean tankToOverflow(int amount){
        return blockEntity.MAX_TANK_SIZE >= blockEntity.CURRENT_FILL + amount;
    }

    public void clearTankEntry(int targetEntry){
        CocktailBlockEntity.TankComponent entry = getTankEntry(targetEntry);
        blockEntity.CURRENT_FILL = blockEntity.CURRENT_FILL - entry.amount;
        blockEntity.tankEntries.remove(targetEntry);
    }


    /// ====
    ///  End of tank shananigans
    /// ====


    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
            switch (id) {
                case 1:
                    this.addToTank(25);
                    //this.inventory.markDirty();

                    return true;
                case 2:
                    this.addToTank(50);
                    //this.inventory.markDirty();

                    return true;
                case 3:
                    this.addToTank(75);
                    //this.inventory.markDirty();

                    return true;
                case 4:
                    this.blockEntity.craftCocktail();
                    //this.inventory.markDirty();

                    return true;
                case 5:
                    this.blockEntity.clearWholeTank();
                    //this.inventory.markDirty();

                    return true;
                default:
                    return false;
            }
    }




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

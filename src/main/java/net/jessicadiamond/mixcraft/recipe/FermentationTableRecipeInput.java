package net.jessicadiamond.mixcraft.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.Identifier;

import java.util.List;

public class FermentationTableRecipeInput implements RecipeInput {


    public static final FermentationTableRecipeInput EMPTY = new FermentationTableRecipeInput(ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY);

    public final ItemStack item;
    public final ItemStack bottle;
    public final ItemStack fuel;

    public FermentationTableRecipeInput(ItemStack item, ItemStack bottle, ItemStack fuel){
        this.item = item;
        this.bottle = bottle;
        this.fuel = fuel;
    }

    public FermentationTableRecipeInput(ItemStack item, ItemStack bottle){
        this.item = item;
        this.bottle = bottle;
        this.fuel = ItemStack.EMPTY;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return switch (slot) {
            case 0 -> this.item;
            case 1 -> this.bottle;
            case 2 -> this.fuel;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        };
    }

    @Override
    public int getSize() {return 3;}

    @Override
    public boolean isEmpty() {
        return this.item.isEmpty() && this.bottle.isEmpty() && this.fuel.isEmpty();
    }
}

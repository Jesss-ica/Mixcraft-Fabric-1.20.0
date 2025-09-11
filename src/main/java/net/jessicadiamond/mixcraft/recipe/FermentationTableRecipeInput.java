package net.jessicadiamond.mixcraft.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record FermentationTableRecipeInput(ItemStack item, ItemStack bottle, ItemStack fuel) implements RecipeInput {

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

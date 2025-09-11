package net.jessicadiamond.mixcraft.recipe;

import net.jessicadiamond.mixcraft.MixCraft;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeSerializer<FermentationTableRecipe> FERMENTATION_TABLE_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(MixCraft.MOD_ID, "fermentation_table"),
                    new FermentationTableRecipe.Serializer());

    public static final RecipeType<FermentationTableRecipe> FERMENTATION_TABLE_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(MixCraft.MOD_ID, "fermentation_table"),
            new RecipeType<FermentationTableRecipe>() {
                @Override
                public String toString() {
                    return "fermentation_table";
                }});

    public static void registerRecipes(){
        MixCraft.LOGGER.info("Registering Custom Recipes for " + MixCraft.MOD_ID);
    }

}

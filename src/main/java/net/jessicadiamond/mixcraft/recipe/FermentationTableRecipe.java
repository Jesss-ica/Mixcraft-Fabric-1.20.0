package net.jessicadiamond.mixcraft.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class FermentationTableRecipe implements Recipe<FermentationTableRecipeInput> {
    public final Ingredient item;
    public final Ingredient bottle;
    public  final Ingredient fuel;
    public final ItemStack output;

    public FermentationTableRecipe(Ingredient item, Ingredient bottle, Ingredient fuel, ItemStack output){
        this.item = item;
        this.bottle = bottle;
        this.fuel = fuel;
        this.output = output;
    }

    public FermentationTableRecipe(Ingredient item, Ingredient bottle, ItemStack output){
        this.item = item;
        this.bottle = bottle;
        this.fuel = Ingredient.EMPTY;
        this.output = output;
    }

    public boolean matches(FermentationTableRecipeInput input, World world) {
        return this.item.test(input.item) && this.bottle.test(input.bottle) && fuel.test(input.fuel);
    }

    public ItemStack craft(FermentationTableRecipeInput fermentationTableRecipeInput, RegistryWrapper.WrapperLookup lookup) {

        ItemStack itemStack = fermentationTableRecipeInput.bottle.copyComponentsToNewStack(this.output.getItem(), this.output.getCount());
        itemStack.applyUnvalidatedChanges(this.output.getComponentChanges());
        return itemStack;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.output;
    }

    public boolean testItem(ItemStack stack) {
        return this.item.test(stack);
    }

    public boolean testBottle(ItemStack stack) {
        return this.bottle.test(stack);
    }

    public boolean testFuel(ItemStack stack) {
        return this.fuel.test(stack);
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FERMENTATION_TABLE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FERMENTATION_TABLE_TYPE;
    }

    @Override
    public boolean isEmpty() {
        return Stream.of(this.item, this.bottle, this.fuel).anyMatch(Ingredient::isEmpty);
    }

    public static class Serializer implements RecipeSerializer<FermentationTableRecipe> {

        public static final MapCodec<FermentationTableRecipe> CODEC = RecordCodecBuilder.mapCodec(
                inst -> inst.group(
                        Ingredient.ALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.item),
                        Ingredient.ALLOW_EMPTY_CODEC.fieldOf("bottle").forGetter(recipe -> recipe.bottle),
                        Ingredient.ALLOW_EMPTY_CODEC.fieldOf("fuel").orElse(Ingredient.EMPTY).forGetter(recipe -> recipe.fuel),
                        ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
                    )
                    .apply(inst, FermentationTableRecipe::new));


        public static final PacketCodec<RegistryByteBuf, FermentationTableRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                FermentationTableRecipe.Serializer::write, FermentationTableRecipe.Serializer::read
        );

        private static FermentationTableRecipe read(RegistryByteBuf buf) {
            Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient ingredient2 = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient ingredient3 = Ingredient.PACKET_CODEC.decode(buf);
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            return new FermentationTableRecipe(ingredient, ingredient2, ingredient3, itemStack);
        }

        private static void write(RegistryByteBuf buf, FermentationTableRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.item);
            Ingredient.PACKET_CODEC.encode(buf, recipe.bottle);
            Ingredient.PACKET_CODEC.encode(buf, recipe.fuel);
            ItemStack.PACKET_CODEC.encode(buf, recipe.output);
        }

        @Override
        public MapCodec<FermentationTableRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, FermentationTableRecipe> packetCodec() { return PACKET_CODEC; }
    }
}

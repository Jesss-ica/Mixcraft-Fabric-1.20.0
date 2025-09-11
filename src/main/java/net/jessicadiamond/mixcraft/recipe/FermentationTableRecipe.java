package net.jessicadiamond.mixcraft.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public record FermentationTableRecipe(Ingredient item, Ingredient bottle, Ingredient fuel, ItemStack output) implements Recipe<FermentationTableRecipeInput> {

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.item);
        list.add(this.bottle);
        list.add(this.fuel);
        return list;
    }



    @Override
    public boolean matches(FermentationTableRecipeInput input, World world) {
        if(world.isClient()){
            return false;
        }

        return this.item.test(input.item()) && this.bottle.test(input.bottle()) && fuel.test(input.fuel());
    }

    @Override
    public ItemStack craft(FermentationTableRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FERMENTATION_TABLE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FERMENTATION_TABLE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<FermentationTableRecipe> {
        public static final MapCodec<FermentationTableRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(FermentationTableRecipe::item),
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("bottle").forGetter(FermentationTableRecipe::bottle),
                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("fuel").forGetter(FermentationTableRecipe::fuel),
                ItemStack.CODEC.fieldOf("result").forGetter(FermentationTableRecipe::output)
        ).apply(inst, FermentationTableRecipe::new));

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
        public MapCodec<FermentationTableRecipe> codec() {return CODEC;}

        @Override
        public PacketCodec<RegistryByteBuf, FermentationTableRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}

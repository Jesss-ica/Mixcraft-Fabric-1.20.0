package net.jessicadiamond.mixcraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.jessicadiamond.mixcraft.block.ModBlocks;
import net.jessicadiamond.mixcraft.item.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TEST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.COCKTAIL_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.TEST_ITEM, Models.GENERATED);
        itemModelGenerator.register(ModItems.VODKA, Models.GENERATED);
        itemModelGenerator.register(ModItems.MOLOTOV, Models.GENERATED);
        //itemModelGenerator.register(ModItems.WHISKY, Models.GENERATED);
        itemModelGenerator.register(ModItems.BEER, Models.GENERATED);
    }
}

package net.jessicadiamond.mixcraft.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.block.custom.AlcoholDisplayBlock;
import net.jessicadiamond.mixcraft.block.custom.FermentationTableBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {

    public static final Block TEST_BLOCK = registerBlock(
            "test_block",
            new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block ALCOHOL_DISPLAY = registerBlock(
            "alcohol_display",
            new AlcoholDisplayBlock(AbstractBlock.Settings.create().nonOpaque().luminance(state -> 10)));

    public static final Block FERMENTATION_TABLE = registerBlock(
            "fermentation_table",
            new FermentationTableBlock(AbstractBlock.Settings.create())
    );


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MixCraft.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(MixCraft.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        MixCraft.LOGGER.info("Registering Mod Blocks for " + MixCraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.TEST_BLOCK);
        });
    }
}

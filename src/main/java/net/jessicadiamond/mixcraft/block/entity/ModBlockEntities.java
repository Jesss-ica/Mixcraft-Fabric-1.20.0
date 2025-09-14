package net.jessicadiamond.mixcraft.block.entity;

import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.block.ModBlocks;
import net.jessicadiamond.mixcraft.block.entity.custom.AlcoholDisplayBlockEntity;
import net.jessicadiamond.mixcraft.block.entity.custom.CocktailBlockEntity;
import net.jessicadiamond.mixcraft.block.entity.custom.FermentationTableBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<AlcoholDisplayBlockEntity> ALCOHOL_DISPLAY_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MixCraft.MOD_ID, "alcohol_display_be"),
                    BlockEntityType.Builder.create(AlcoholDisplayBlockEntity::new, ModBlocks.ALCOHOL_DISPLAY).build(null));

    public static final BlockEntityType<FermentationTableBlockEntity> FERMENTATION_TABLE_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MixCraft.MOD_ID, "fermentation_table_be"),
                    BlockEntityType.Builder.create(FermentationTableBlockEntity::new, ModBlocks.FERMENTATION_TABLE).build(null));

    public static final BlockEntityType<CocktailBlockEntity> COCKTAIL_BLOCK_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MixCraft.MOD_ID, "cocktail_block_be"),
                    BlockEntityType.Builder.create(CocktailBlockEntity::new, ModBlocks.COCKTAIL_BLOCK).build(null));

    public static void registerBlockEntities(){
        MixCraft.LOGGER.info("Registering Block Entities for " + MixCraft.MOD_ID);
    }

}

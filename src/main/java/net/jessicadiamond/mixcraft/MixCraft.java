package net.jessicadiamond.mixcraft;

import net.fabricmc.api.ModInitializer;

import net.jessicadiamond.mixcraft.block.ModBlocks;
import net.jessicadiamond.mixcraft.block.entity.ModBlockEntities;
import net.jessicadiamond.mixcraft.entity.ModEntities;
import net.jessicadiamond.mixcraft.item.ModItemGroups;
import net.jessicadiamond.mixcraft.item.ModItems;
import net.jessicadiamond.mixcraft.screen.ModScreenHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MixCraft implements ModInitializer {
	public static final String MOD_ID = "mixcraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModBlocks.registerModBlocks();
        ModItemGroups.registerItemGroups();
        ModItems.registerModItems();
        ModEntities.registerModEntities();
        ModBlockEntities.registerBlockEntities();
        ModScreenHandlers.registerScreenHandlers();

        LOGGER.info("Hello Fabric world!");
	}
}
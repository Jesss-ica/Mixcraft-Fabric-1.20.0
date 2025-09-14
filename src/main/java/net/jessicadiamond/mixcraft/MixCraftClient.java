package net.jessicadiamond.mixcraft;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.jessicadiamond.mixcraft.block.entity.ModBlockEntities;
import net.jessicadiamond.mixcraft.block.entity.renderer.AlcoholDisplayBlockEntityRenderer;
import net.jessicadiamond.mixcraft.entity.ModEntities;
import net.jessicadiamond.mixcraft.entity.client.MolotovProjectileModel;
import net.jessicadiamond.mixcraft.entity.client.MolotovProjectileRenderer;
import net.jessicadiamond.mixcraft.screen.ModScreenHandlers;
import net.jessicadiamond.mixcraft.screen.custom.AlcoholDisplayScreen;
import net.jessicadiamond.mixcraft.screen.custom.CocktailBlockScreen;
import net.jessicadiamond.mixcraft.screen.custom.FermentationTableScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModel;

public class MixCraftClient implements ClientModInitializer {

    @Override
	public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(MolotovProjectileModel.MOLOTOV, MolotovProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MOLOTOV, MolotovProjectileRenderer::new);

        BlockEntityRendererFactories.register(ModBlockEntities.ALCOHOL_DISPLAY_BE, AlcoholDisplayBlockEntityRenderer::new);

        HandledScreens.register(ModScreenHandlers.ALCOHOL_DISPLAY_SCREEN_HANDLER, AlcoholDisplayScreen :: new);
        HandledScreens.register(ModScreenHandlers.FERMENTATION_TABLE_SCREEN_HANDLER, FermentationTableScreen :: new);
        HandledScreens.register(ModScreenHandlers.COCKTAIL_BLOCK_SCREEN_HANDLER, CocktailBlockScreen:: new);
	}
}

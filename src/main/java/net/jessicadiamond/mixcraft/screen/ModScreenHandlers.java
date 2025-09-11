package net.jessicadiamond.mixcraft.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.screen.custom.AlcoholDisplayScreenHandler;
import net.jessicadiamond.mixcraft.screen.custom.FermentationTableScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;


public class ModScreenHandlers  {
    public static final ScreenHandlerType<AlcoholDisplayScreenHandler> ALCOHOL_DISPLAY_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MixCraft.MOD_ID, "alcohol_display_scren_handler"),
                    new ExtendedScreenHandlerType<>(AlcoholDisplayScreenHandler::new, BlockPos.PACKET_CODEC));

    public static final ScreenHandlerType<FermentationTableScreenHandler> FERMENTATION_TABLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MixCraft.MOD_ID, "fermentation_screen_handler"),
                    new ExtendedScreenHandlerType<>(FermentationTableScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void registerScreenHandlers(){
        MixCraft.LOGGER.info("Registering Screen Handlers for " + MixCraft.MOD_ID);
    }
}

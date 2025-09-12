package net.jessicadiamond.mixcraft.item.custom;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;

public class AbstractAlcohol extends Item {

    public static Settings settings;

    public AbstractAlcohol(Item.Settings itemSettings, AbstractAlcohol.Settings alcoholSettings){
        super(itemSettings);
        this.settings = alcoholSettings;
    }

    public static class Settings implements net.fabricmc.fabric.api.item.v1.FabricItem.Settings{

    }

}

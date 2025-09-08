package net.jessicadiamond.mixcraft.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.item.custom.MolotovItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {

    public static final Item TEST_ITEM = registerItem("test_item", new Item(new Item.Settings()));

    public static final Item MOLOTOV = registerItem("molotov", new MolotovItem(new Item.Settings().maxCount(16)));

    public static final Item VODKA = registerItem("vodka", new Item(new Item.Settings().food(ModFoodComponents.VODKA)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MixCraft.MOD_ID, name), item);
    }


    public static void registerModItems() {
        MixCraft.LOGGER.info("Registering Mod Items for " + MixCraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(TEST_ITEM);
        });
    }
}

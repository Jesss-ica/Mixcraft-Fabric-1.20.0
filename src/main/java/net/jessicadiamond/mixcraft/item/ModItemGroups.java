package net.jessicadiamond.mixcraft.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.block.ModBlocks;
import net.jessicadiamond.mixcraft.item.custom.MolotovItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup RUBY_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MixCraft.MOD_ID, "test_item"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.test_item"))
                    .icon(() -> new ItemStack(ModItems.TEST_ITEM)).entries((displayContext, entries) -> {
                        entries.add(ModItems.TEST_ITEM);
                        entries.add(ModBlocks.TEST_BLOCK);

                        entries.add(ModBlocks.ALCOHOL_DISPLAY);

                        entries.add(ModItems.VODKA);
                        entries.add(ModItems.MOLOTOV);


                    }).build());

    public static void registerItemGroups(){

    }
}

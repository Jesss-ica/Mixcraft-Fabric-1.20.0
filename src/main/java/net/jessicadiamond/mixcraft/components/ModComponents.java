package net.jessicadiamond.mixcraft.components;

import com.mojang.serialization.Codec;
import net.jessicadiamond.mixcraft.MixCraft;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {

//    public static final ComponentType<Integer> BOTTLE_SIZE = Registry.register(
//            Registries.DATA_COMPONENT_TYPE,
//            Identifier.of(MixCraft.MOD_ID, "bottle_size"),
//            ComponentType.<Integer>builder().codec(Codec.INT).build()
//    );

    public static final ComponentType<AlcoholComponent> ALCOHOL_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MixCraft.MOD_ID, "alcohol_component"),
            ComponentType.<AlcoholComponent>builder().codec(AlcoholComponent.CODEC).build()
    );



    public static void initialize(){
        MixCraft.LOGGER.info("Registing components for ", MixCraft.MOD_ID);
    }

}

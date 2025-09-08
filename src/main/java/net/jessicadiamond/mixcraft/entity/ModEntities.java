package net.jessicadiamond.mixcraft.entity;

import net.jessicadiamond.mixcraft.MixCraft;
import net.jessicadiamond.mixcraft.entity.custom.MolotovProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<MolotovProjectileEntity> MOLOTOV = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(MixCraft.MOD_ID, "molotov"),
                    EntityType.Builder.<MolotovProjectileEntity>create(MolotovProjectileEntity::new, SpawnGroup.MISC)
                            .dimensions(0.5f, 1.15f).build());

    public static void registerModEntities() {
        MixCraft.LOGGER.info("Registering Mod Entities for " + MixCraft.MOD_ID);
    }
}

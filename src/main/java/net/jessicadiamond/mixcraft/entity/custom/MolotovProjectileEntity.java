package net.jessicadiamond.mixcraft.entity.custom;

import net.jessicadiamond.mixcraft.entity.ModEntities;
import net.jessicadiamond.mixcraft.item.ModItems;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class MolotovProjectileEntity extends ThrownItemEntity {
    private float rotation;

    public MolotovProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public MolotovProjectileEntity(World world, PlayerEntity player) {
        super(ModEntities.MOLOTOV, player, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.MOLOTOV;
    }

//    @Override
//    protected ItemStack getDefaultItemStack() {
//        return new ItemStack(ModItems.MOLOTOV);
//    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 4);

        if (!this.getWorld().isClient()) {
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult){
        this.getWorld().createExplosion(
                this,
                this.getX(),
                this.getBodyY(0.0625),
                this.getZ(),
                1.0f,
                true,
                World.ExplosionSourceType.TNT);
        this.discard();
    }
}

package net.jessicadiamond.mixcraft.item.custom;

import net.jessicadiamond.mixcraft.components.AlcoholComponent;
import net.jessicadiamond.mixcraft.components.ModComponents;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class AbstractAlcohol extends Item {

    public AbstractAlcohol(Item.Settings itemSettings){
        super(itemSettings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user){
        if(stack.get(ModComponents.ALCOHOL_COMPONENT).millilitres() <= 0){
            return stack;
        }
        FoodComponent foodComponent = stack.get(DataComponentTypes.FOOD);
        return foodComponent != null ? DrinkAlcohol(world, stack, user, foodComponent) : stack;
    }

    public ItemStack DrinkAlcohol(World world, ItemStack stack, LivingEntity user, FoodComponent foodComponent){
        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                user.getEatSound(stack),
                SoundCategory.NEUTRAL,
                1.0F,
                1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F
        );
        int bottle_size = stack.get(ModComponents.ALCOHOL_COMPONENT).millilitres();
        stack.set(ModComponents.ALCOHOL_COMPONENT, new AlcoholComponent(bottle_size - 25, 1f, 40f, "WHITE"));
        user.emitGameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type){
        if(stack.contains(ModComponents.ALCOHOL_COMPONENT)){
            int bottleSize = stack.get(ModComponents.ALCOHOL_COMPONENT).millilitres();
            float alcoholPercent = stack.get(ModComponents.ALCOHOL_COMPONENT).ABV();
            float units = stack.get(ModComponents.ALCOHOL_COMPONENT).unitsPerShot();
            tooltip.add(Text.translatable("item.mixcraft.bottle_size.info", bottleSize).formatted(Formatting.ITALIC.GRAY));
            tooltip.add(Text.translatable("item.mixcraft.abv.info", alcoholPercent).formatted(Formatting.ITALIC.GRAY));
            tooltip.add(Text.translatable("item.mixcraft.units.info", units).formatted(Formatting.ITALIC.GRAY));
        }
    }

}

package net.fabricmc.example;

import net.minecraft.block.Blocks;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.UseAction;

public class EmeraldBowItem extends BowItem {

    final Item type = Items.EMERALD;

    public EmeraldBowItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) { // left right
        return ingredient.getItem().equals(type);
    }

}

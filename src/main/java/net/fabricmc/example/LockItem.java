package net.fabricmc.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class LockItem extends Item {

    public LockItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        // todo: open/update linked number to door or button

//        if (context.getBlockPos())
//        context.getStack().getItem().equals(ExampleMod.LOCK_BUTTON_BLOCK);

        return ActionResult.SUCCESS;

    }



}

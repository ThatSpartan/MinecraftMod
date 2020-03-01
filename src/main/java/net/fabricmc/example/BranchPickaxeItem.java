package net.fabricmc.example;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BranchPickaxeItem extends PickaxeItem {

    protected BranchPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {

        BlockBreaker.breakInRadius(world, miner);

        return true;

//        return super.canMine(state, world, pos, miner);
    }

    //    @Override
//    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
////        return super.postMine(stack, world, state, pos, miner);
//
//        if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
//            stack.damage(1, miner, (e) -> {
//                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
//            });
//        }
//        return true;
//    }
}

package net.fabricmc.example;

import net.minecraft.block.BlockState;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LockButtonBlock extends WoodButtonBlock {

    protected LockButtonBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (!(Boolean) state.get(POWERED)) {

            // todo: cycle door linked to this button
            System.out.println("Button");

        }

        return super.onUse(state, world, pos, player, hand, hit);

    }
}

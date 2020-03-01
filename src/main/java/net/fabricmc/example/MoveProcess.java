package net.fabricmc.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Queue;

public class MoveProcess {

    class Step {

        BlockPos blockPos; // block to move to
        boolean jump; // requires jump or not

        Step(BlockPos blockPos, boolean jump) {
            this.blockPos = blockPos;
            this.jump = jump;
        }

    }

    PlayerController playerController = new PlayerController(MinecraftClient.getInstance().player);
    Queue<Step> steps;
    BlockPos targetBlock;

    public void setMoveTarget(BlockPos blockPos) {
        // set the move to block target
        targetBlock = blockPos;
    }

    public void tick() {
        // run tick (make decision)
    }

    private void chooseDirection() {
        // choose direction we should look at

    }

    private void setStrategy() {
        // set a block list we should traverse
//        steps.add(new Step(BlockPos.fromLong(BlockPos.offset(1, Direction.SOUTH)), false)); //example of addign a block to the strategy



    }

}

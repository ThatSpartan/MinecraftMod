package net.fabricmc.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Direction;

public class PlayerController {

    PlayerEntity player;

    public PlayerController(PlayerEntity player) {
        this.player = player;
    }

    public void MoveForward(boolean value) {
        // set the player walking -> value
        MinecraftClient.getInstance().options.keyForward.setPressed(value);
    }

    public void Look(Direction direction) {
        // set the player looking at a direction
        player.refreshPositionAndAngles(player.getBlockPos(), getYaw(direction), 90);
    }

    private float getYaw(Direction direction) {

        float yaw = 0;

        if (direction == Direction.NORTH) {
            yaw = 180;
        } else if (direction == Direction.EAST) {
            yaw = 270;
        } else if (direction == Direction.SOUTH) {
            yaw = 0;
        } else if (direction == Direction.WEST) {
            yaw = 90;
        }

        return yaw;

    }

    public Direction getDirection() {
        // get the direction player is facing
        return player.getHorizontalFacing();
    }

    public void Jump() {
        // make the player jump
        player.jump();
    }

}

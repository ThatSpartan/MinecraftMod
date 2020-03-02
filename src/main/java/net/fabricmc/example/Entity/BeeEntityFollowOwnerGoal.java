package net.fabricmc.example.Entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.EnumSet;

public class BeeEntityFollowOwnerGoal extends Goal {
    private final AttackBeeEntity beeEntity;
    private LivingEntity owner;
    private final WorldView world;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;
    private float oldWaterPathfindingPenalty;
    private final boolean leavesAllowed;

    public BeeEntityFollowOwnerGoal(AttackBeeEntity beeEntity, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        this.beeEntity = beeEntity;
        this.world = beeEntity.world;
        this.speed = speed;
        this.navigation = beeEntity.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.leavesAllowed = leavesAllowed;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        if (!(beeEntity.getNavigation() instanceof MobNavigation) && !(beeEntity.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.beeEntity.getOwner();
        if (livingEntity == null) {
            return false;
        } else if (livingEntity.isSpectator()) {
            return false;
        } else if (this.beeEntity.method_24345()) {
            return false;
        } else if (this.beeEntity.squaredDistanceTo(livingEntity) < (double)(this.minDistance * this.minDistance)) {
            return false;
        } else {
            this.owner = livingEntity;
            return true;
        }
    }

    public boolean shouldContinue() {
        if (this.navigation.isIdle()) {
            return false;
        } else if (this.beeEntity.method_24345()) {
            return false;
        } else {
            return this.beeEntity.squaredDistanceTo(this.owner) > (double)(this.maxDistance * this.maxDistance);
        }
    }

    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.beeEntity.getPathfindingPenalty(PathNodeType.WATER);
        this.beeEntity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.beeEntity.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    public void tick() {
        this.beeEntity.getLookControl().lookAt(this.owner, 10.0F, (float)this.beeEntity.getLookPitchSpeed());
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;
            if (!this.beeEntity.isLeashed() && !this.beeEntity.hasVehicle()) {
                if (this.beeEntity.squaredDistanceTo(this.owner) >= 144.0D) {
                    this.tryTeleport();
                } else {
                    this.navigation.startMovingTo(this.owner, this.speed);
                }

            }
        }
    }

    private void tryTeleport() {
        BlockPos blockPos = new BlockPos(this.owner);

        for(int i = 0; i < 10; ++i) {
            int j = this.getRandomInt(-3, 3);
            int k = this.getRandomInt(-1, 1);
            int l = this.getRandomInt(-3, 3);
            boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
            if (bl) {
                return;
            }
        }

    }

    private boolean tryTeleportTo(int x, int y, int z) {
        if (Math.abs((double)x - this.owner.getX()) < 2.0D && Math.abs((double)z - this.owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        } else {
            this.beeEntity.refreshPositionAndAngles((double)((float)x + 0.5F), (double)y, (double)((float)z + 0.5F), this.beeEntity.yaw, this.beeEntity.pitch);
            this.navigation.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getPathNodeType(this.world, pos.getX(), pos.getY(), pos.getZ());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        } else {
            BlockState blockState = this.world.getBlockState(pos.down());
            if (!this.leavesAllowed && blockState.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockPos = pos.subtract(new BlockPos(this.beeEntity));
                return this.world.doesNotCollide(this.beeEntity, this.beeEntity.getBoundingBox().offset(blockPos));
            }
        }
    }

    private int getRandomInt(int min, int max) {
        return this.beeEntity.getRandom().nextInt(max - min + 1) + min;
    }
}

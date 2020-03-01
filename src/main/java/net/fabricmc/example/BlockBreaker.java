package net.fabricmc.example;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockBreaker {

    public static void breakInRadius(World world, PlayerEntity playerEntity) {

        if (!world.isClient) {

            List<BlockPos> brokenBlocks = getBreakBlocks(world, playerEntity);

            for (BlockPos pos : brokenBlocks) {

                BlockState state = world.getBlockState(pos);

                world.breakBlock(pos, true); // todo : set custom drop function

                if (!playerEntity.isCreative()) {

                    // do stuff todo : finish

                    playerEntity.inventory.getMainHandStack().damage(1, playerEntity, player -> {});

                }

            }

        }

    }

    public static List<BlockPos> getBreakBlocks(World world, PlayerEntity playerEntity) {

        ArrayList<BlockPos> potentialBrokenBlocks = new ArrayList<>();

        Vec3d cameraPos = playerEntity.getCameraPosVec(1);
        Vec3d rotation = playerEntity.getRotationVec(1);
        Vec3d combined = cameraPos.add(rotation.x * 5, rotation.y * 5, rotation.z * 5);

        BlockHitResult blockHitResult = world.rayTrace(new RayTraceContext(cameraPos, combined, RayTraceContext.ShapeType.OUTLINE, RayTraceContext.FluidHandling.NONE, playerEntity));

        if (blockHitResult.getType() == HitResult.Type.BLOCK) {

            // jessica's addons
//            for (int i = 0; i < 3; i++) {

            Direction.Axis axis = blockHitResult.getSide().getAxis();
            Direction.AxisDirection axisDirection = blockHitResult.getSide().getDirection(); System.out.println("\n\n" + axisDirection + "\n" + axis + "\n\n");
            ArrayList<BlockPos> positions = new ArrayList<>();

            int size = 1;
            int length = 15;

            if (axis == Direction.Axis.Y) {

                if (axisDirection == Direction.AxisDirection.POSITIVE) {
                    for (int y = 0; y <= length; y++) {
                        for (int x = -size; x <= size; x++) {
                            for (int z = -size; z <= size; z++) {
                                positions.add(new BlockPos(x, -y, z));
                            }
                        }
                    }
                } else if (axisDirection == Direction.AxisDirection.NEGATIVE) {
                    for (int y = 0; y <= length; y++) {
                        for (int x = -size; x <= size; x++) {
                            for (int z = -size; z <= size; z++) {
                                positions.add(new BlockPos(x, y, z));
                            }
                        }
                    }
                }

            } else if (axis == Direction.Axis.X) {

                if (axisDirection == Direction.AxisDirection.POSITIVE) {
                    for (int x = 0; x <= length; x++) {
                        for (int y = -size; y <= size; y++) {
                            for (int z = -size; z <= size; z++) {
                                positions.add(new BlockPos(-x, y, z));
                            }
                        }
                    }
                } else if (axisDirection == Direction.AxisDirection.NEGATIVE) {
                    for (int x = 0; x <= length; x++) {
                        for (int y = -size; y <= size; y++) {
                            for (int z = -size; z <= size; z++) {
                                positions.add(new BlockPos(x, y, z));
                            }
                        }
                    }
                }

            } else if (axis == Direction.Axis.Z) {

                if (axisDirection == Direction.AxisDirection.POSITIVE) {
                    for (int z = 0; z <= length; z++) {
                        for (int y = -size; y <= size; y++) {
                            for (int x = -size; x <= size; x++) {
                                positions.add(new BlockPos(x, y, -z));
                            }
                        }
                    }
                } else if (axisDirection == Direction.AxisDirection.NEGATIVE) {
                    for (int z = 0; z <= length; z++) {
                        for (int y = -size; y <= size; y++) {
                            for (int x = -size; x <= size; x++) {
                                positions.add(new BlockPos(x, y, z));
                            }
                        }
                    }
                }

            }

            BlockPos origin = blockHitResult.getBlockPos();

            for (BlockPos pos : positions) {

                potentialBrokenBlocks.add(origin.add(pos));
            }

//                int size = 3;
//                for (int x = -size; x <= size; x++) {
//                    for (int y = -size; y <= size; y++) {
//                        for (int z = -size; z <= size; z++) {
//                            positions.add(new BlockPos(x, y, z));
//                        }
//                    }
//                }
//
//                BlockPos origin = blockHitResult.getBlockPos();
//
//                for (BlockPos pos : positions) {
//
//                    if (axis == Direction.Axis.Y) {
//                        if (pos.getY() <= 0 && axisDirection == Direction.AxisDirection.NEGATIVE) {
//                            potentialBrokenBlocks.add(origin.add(pos));
//                        } else if (pos.getY() >= 0 && axisDirection == Direction.AxisDirection.POSITIVE) {
//                            potentialBrokenBlocks.add(origin.add(pos));
//                        }
//                    } else if (axis == Direction.Axis.X) {
//                        if (pos.getX() <= 0 && axisDirection == Direction.AxisDirection.NEGATIVE) {
//                            potentialBrokenBlocks.add(origin.add(pos));
//                        } else if (pos.getX() >= 0 && axisDirection == Direction.AxisDirection.POSITIVE) {
//                            potentialBrokenBlocks.add(origin.add(pos));
//                        }
//                    } else if (axis == Direction.Axis.Z) {
//                        if (pos.getZ() <= 0 && axisDirection == Direction.AxisDirection.NEGATIVE) {
//                            potentialBrokenBlocks.add(origin.add(pos));
//                        } else if (pos.getZ() >= 0 && axisDirection == Direction.AxisDirection.POSITIVE) {
//                            potentialBrokenBlocks.add(origin.add(pos));
//                        }
//                    }
//
//                }

//            }

        }

        System.out.println("\n\nDONE\n\n");

        return potentialBrokenBlocks;

    }
}

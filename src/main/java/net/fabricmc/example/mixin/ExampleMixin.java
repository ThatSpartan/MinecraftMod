package net.fabricmc.example.mixin;

import net.fabricmc.example.BranchPickaxeItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class ExampleMixin {

	@Shadow private static void drawShapeOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float g, float h, float i, float j) {

	}

	@Shadow @Final private MinecraftClient client;

	@Shadow private double lastCameraX;

	@Shadow private double lastCameraY;

	@Shadow private double lastCameraZ;

	@Inject(at = @At("HEAD"), method = "drawBlockOutline", cancellable = true)
	private void drawBlockOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, CallbackInfo info) {

		if (this.client.player.getMainHandStack().getItem() instanceof BranchPickaxeItem) {
			if (client.crosshairTarget instanceof BlockHitResult) {

				BlockHitResult crosshairTarget = (BlockHitResult) client.crosshairTarget;
				BlockPos blockPos1 = crosshairTarget.getBlockPos();
				BlockState blockState1 = client.world.getBlockState(blockPos1);

				if (!blockState1.isAir() && client.world.getWorldBorder().contains(blockPos1)) {

					if (crosshairTarget.getSide().getAxis() == Direction.Axis.Y) {
						// -x is west
						// x is east
						// -z is north
						// z is south

						VoxelShape shape = VoxelShapes.empty();

						for (int x = -1; x <= 1; x++) {
							for (int z = -1; z <= 1; z++) {
								if (client.world.getBlockState(blockPos1.add(x, 0, z)) != Blocks.AIR.getDefaultState()) {
									shape = VoxelShapes.union(shape, client.world.getBlockState(blockPos1.add(x, 0, z)).getOutlineShape(client.world, blockPos1.add(x, 0, z)).offset(x, 0, z));
								}
							}
						}

						drawShapeOutline(matrixStack, vertexConsumer, shape, (double) blockPos1.getX() - lastCameraX, (double) blockPos1.getY() - lastCameraY, (double) blockPos1.getZ() - lastCameraZ, 0.7F, 0.2F, 0.4F, 0.6F);
						info.cancel();

					} else if (crosshairTarget.getSide().getAxis() == Direction.Axis.X) {

						VoxelShape shape = VoxelShapes.empty();

						for (int y = -1; y <= 1; y++) {
							for (int z = -1; z <= 1; z++) {
								if (client.world.getBlockState(blockPos1.add(0, y, z)) != Blocks.AIR.getDefaultState()) {
									shape = VoxelShapes.union(shape, client.world.getBlockState(blockPos1.add(0, y, z)).getOutlineShape(client.world, blockPos1.add(0, y, z)).offset(0, y, z));
								}
							}
						}

						drawShapeOutline(matrixStack, vertexConsumer, shape, (double) blockPos1.getX() - lastCameraX, (double) blockPos1.getY() - lastCameraY, (double) blockPos1.getZ() - lastCameraZ, 0.7F, 0.2F, 0.4F, 0.6F);
						info.cancel();

					} else if (crosshairTarget.getSide().getAxis() == Direction.Axis.Z) {

						VoxelShape shape = VoxelShapes.empty();

						for (int x = -1; x <= 1; x++) {
							for (int y = -1; y <= 1; y++) {
								if (client.world.getBlockState(blockPos1.add(x, y, 0)) != Blocks.AIR.getDefaultState()) {
									shape = VoxelShapes.union(shape, client.world.getBlockState(blockPos1.add(x, y, 0)).getOutlineShape(client.world, blockPos1.add(x, y, 0)).offset(x, y, 0));
								}
							}
						}

						drawShapeOutline(matrixStack, vertexConsumer, shape, (double) blockPos1.getX() - lastCameraX, (double) blockPos1.getY() - lastCameraY, (double) blockPos1.getZ() - lastCameraZ, 0.7F, 0.2F, 0.4F, 0.6F);
						info.cancel();

					}

				}

			}
		}

	}
}

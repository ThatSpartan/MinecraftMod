package net.fabricmc.example;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

public class TeleportItem extends Item {


    public static double x=0, y=0, z=0;

    public TeleportItem(Settings settings) {
        super(settings);
    }

    private Direction.Axis getAxis(PlayerEntity playerEntity, World world) {
        Vec3d cameraPos = playerEntity.getCameraPosVec(1);
        Vec3d rotation = playerEntity.getRotationVec(1);
        Vec3d combined = cameraPos.add(rotation.x * 5, rotation.y * 5, rotation.z * 5);

        BlockHitResult blockHitResult = world.rayTrace(new RayTraceContext(cameraPos, combined, RayTraceContext.ShapeType.OUTLINE, RayTraceContext.FluidHandling.NONE, playerEntity));

        return blockHitResult.getSide().getAxis();
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
//
//        HitResult hitResult = miner.rayTrace(4, 0.0f, false);
//
//        if (hitResult.getType() == HitResult.Type.BLOCK) {
//            Vec3d vec3d = hitResult.getPos();
//            TeleportItem.x = vec3d.getX();
//            TeleportItem.y = vec3d.getY();
//            TeleportItem.z = vec3d.getZ();
//        }
//
        return false;
//
    }

    @Override
    public float getMiningSpeed(ItemStack stack, BlockState state) {
//        return super.getMiningSpeed(stack, state);
        return 1.5F;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        Direction.Axis axis = getAxis(user, world);

        if (axis == Direction.Axis.Y) {

            HitResult hitResult = user.rayTrace(4, 0.0f, false);

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                Vec3d vec3d = hitResult.getPos();
                TeleportItem.x = vec3d.getX();
                TeleportItem.y = vec3d.getY();
                TeleportItem.z = vec3d.getZ();
            }

        } else {

            ItemStack itemStack = user.getMainHandStack();
            itemStack.decrement(1);

            if (x != 0 && y != 0 && z != 0) {
                //            user.requestTeleport(x, y, z);
                user.teleport(x, y, z);

//                ((ServerPlayerEntity) user).networkHandler.requestTeleport(x, y, z, user.yaw, user.pitch);

            }

        }

//        return TypedActionResult.success(itemStack);
        return TypedActionResult.success(user.getMainHandStack());
    }
}

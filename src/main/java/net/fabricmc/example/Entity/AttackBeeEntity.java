package net.fabricmc.example.Entity;

import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AttackGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("EntityConstructor")
public class AttackBeeEntity extends BeeEntity {
    public AttackBeeEntity(EntityType<? extends BeeEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new AttackBeeEntity.StingGoal(this, 1.399999976158142D, true));
        this.goalSelector.add(1, new FollowOwnerGoal(this, 1.399999976158142D, 3, 1, true));

    }

    class StingGoal extends MeleeAttackGoal {
        StingGoal(MobEntityWithAi mob, double speed, boolean bl) {
            super(mob, speed, bl);
        }

        public boolean canStart() {
            return super.canStart();
        }

        public boolean shouldContinue() {
            return super.shouldContinue();
        }
    }

    class FollowOwnerGoal extends BeeEntityFollowOwnerGoal {

        public FollowOwnerGoal(AttackBeeEntity beeEntity, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
            super(beeEntity, speed, minDistance, maxDistance, leavesAllowed);
        }
    }



    // TAMEABLE THINGS


    @Override
    public boolean interactMob(PlayerEntity player, Hand hand) {
        if (!this.isTamed()) {
            this.setOwner(player);
        }
        return true;
    }

    protected static final TrackedData<Byte> TAMEABLE_FLAGS;
    protected static final TrackedData<Optional<UUID>> OWNER_UUID;
    private boolean field_21974;

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TAMEABLE_FLAGS, (byte)0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        if (this.getOwnerUuid() == null) {
            tag.putString("OwnerUUID", "");
        } else {
            tag.putString("OwnerUUID", this.getOwnerUuid().toString());
        }

        tag.putBoolean("Sitting", this.field_21974);
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        String string3;
        if (tag.contains("OwnerUUID", 8)) {
            string3 = tag.getString("OwnerUUID");
        } else {
            String string2 = tag.getString("Owner");
            string3 = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string2);
        }

        if (!string3.isEmpty()) {
            try {
                this.setOwnerUuid(UUID.fromString(string3));
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }

        this.field_21974 = tag.getBoolean("Sitting");
//        this.setSitting(this.field_21974);
    }

    public boolean isTamed() {
        return ((Byte)this.dataTracker.get(TAMEABLE_FLAGS) & 4) != 0;
    }

    public void setTamed(boolean tamed) {
        byte b = (Byte)this.dataTracker.get(TAMEABLE_FLAGS);
        if (tamed) {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte)(b | 4));
        } else {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte)(b & -5));
        }

        this.onTamedChanged();
    }

    protected void onTamedChanged() {
    }

    public UUID getOwnerUuid() {
        return (UUID)((Optional)this.dataTracker.get(OWNER_UUID)).orElse((Object)null);
    }

    public void setOwnerUuid(UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public void setOwner(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerUuid(player.getUuid());
        if (player instanceof ServerPlayerEntity) {
            Criterions.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
        }

    }

    public LivingEntity getOwner() {
        try {
            UUID uUID = this.getOwnerUuid();
            return uUID == null ? null : this.world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public boolean canTarget(LivingEntity target) {
        return this.isOwner(target) ? false : super.canTarget(target);
    }

    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }

    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        return true;
    }

    public void onDeath(DamageSource source) {
        if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
            this.getOwner().sendMessage(this.getDamageTracker().getDeathMessage());
        }

        super.onDeath(source);
    }

    public boolean method_24345() {
        return this.field_21974;
    }

    public void method_24346(boolean bl) {
        this.field_21974 = bl;
    }

    static {
        TAMEABLE_FLAGS = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BYTE);
        OWNER_UUID = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    }
}

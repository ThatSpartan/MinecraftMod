package net.fabricmc.example.Entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TrackTargetGoal;

import java.util.EnumSet;

public class BeeEntityAttackWithOwnerGoal extends TrackTargetGoal {
    private final AttackBeeEntity beeEntity;
    private LivingEntity attacking;
    private int lastAttackTime;

    public BeeEntityAttackWithOwnerGoal(AttackBeeEntity beeEntity) {
        super(beeEntity, false);
        this.beeEntity = beeEntity;
        this.setControls(EnumSet.of(Control.TARGET));
    }

    public boolean canStart() {
        if (this.beeEntity.isTamed() && !this.beeEntity.method_24345()) {
            LivingEntity livingEntity = this.beeEntity.getOwner();
            if (livingEntity == null) {
                return false;
            } else {
                this.attacking = livingEntity.getAttacking();
                int i = livingEntity.getLastAttackTime();
                return i != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT) && this.beeEntity.canAttackWithOwner(this.attacking, livingEntity);
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.attacking);
        LivingEntity livingEntity = this.beeEntity.getOwner();
        if (livingEntity != null) {
            this.lastAttackTime = livingEntity.getLastAttackTime();
        }

        super.start();
    }
}

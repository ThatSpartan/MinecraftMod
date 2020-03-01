package net.fabricmc.example.Entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class AttackBeeEntity extends BeeEntity {
    public AttackBeeEntity(EntityType<? extends BeeEntity> type, World world) {
        super(type, world);
    }
}

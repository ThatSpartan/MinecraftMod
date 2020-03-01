package net.fabricmc.example.Entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;

public class DoggyEntity extends WolfEntity {
    public DoggyEntity(EntityType<? extends WolfEntity> entityType, World world) {
        super(entityType, world);
    }

}

package net.fabricmc.example.Entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AttackBeeRenderer extends MobEntityRenderer<BeeEntity, BeeEntityModel<BeeEntity>> {
    public AttackBeeRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new BeeEntityModel<>(), 0.5f);
    }

    @Override
    public Identifier getTexture(BeeEntity entity) {
        return new Identifier("modid:textures/entity/attack_bee.png");
    }
}

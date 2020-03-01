package net.fabricmc.example.Entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.util.Identifier;

public class DoggyRenderer extends MobEntityRenderer<DoggyEntity, WolfEntityModel<DoggyEntity>> {
    public DoggyRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new WolfEntityModel<DoggyEntity>(), 1);
    }

    @Override
    public Identifier getTexture(DoggyEntity entity) {
        return new Identifier("modid", "doggy_entity2.png");
    }
}

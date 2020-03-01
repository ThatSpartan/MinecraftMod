package net.fabricmc.example.Entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DoggyRenderer extends MobEntityRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {
    public DoggyRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new WolfEntityModel(), 0.5F);
    }

    @Override
    protected float getAnimationProgress(WolfEntity entity, float tickDelta) {
        return 15;
    }

    @Override
    public Identifier getTexture(WolfEntity entity) {
        return new Identifier("modid:textures/entity/doggy_entity.png");
    }


}

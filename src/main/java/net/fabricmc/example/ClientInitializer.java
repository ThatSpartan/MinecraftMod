package net.fabricmc.example;

import net.fabricmc.example.Entity.DoggyRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class ClientInitializer implements net.fabricmc.api.ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.INSTANCE.register(ExampleMod.DOGGY_ENTITY, (dispatcher, context) -> new DoggyRenderer(dispatcher));
    }
}

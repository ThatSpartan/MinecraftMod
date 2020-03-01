package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.Entity.DoggyEntity;
import net.fabricmc.example.Entity.DoggyRenderer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.File;

public class ExampleMod implements ModInitializer {

	// an instance of our new item
//	public static final LockButtonBlock LOCK_BUTTON_BLOCK = new LockButtonBlock(new FabricBlockSettings().);

	private EntityType<DoggyEntity> myDoggy;
//	public static final EntityType<DoggyEntity> DOGGY_ENTITY = Registry.register(Registry.ENTITY_TYPE, new Identifier("modid", "doggy_entity3"), FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, DoggyEntity::new).size(EntityDimensions.fixed(2, 1)).build());
	public static final EntityType<DoggyEntity> DOGGY_ENTITY = FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, DoggyEntity::new).size(EntityDimensions.fixed(0.6F, 0.85F)).build();

	public static final EmeraldBowItem EMERALD_BOW_ITEM = new EmeraldBowItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final TeleportItem TELEPORT_ITEM = new TeleportItem(new Item.Settings().group(ItemGroup.MISC).maxCount(10));
	public static final FabricItem FABRIC_ITEM = new FabricItem(new Item.Settings().group(ItemGroup.MISC));
	public static final BranchPickaxeItem BRANCH_PICKAXE_ITEM = new BranchPickaxeItem(new ToolMaterial() {
		@Override
		public int getDurability() {
			return 14000;
		}

		@Override
		public float getMiningSpeed() {
			return 8.0F / 3.5f;
		}

		@Override
		public float getAttackDamage() {
			return 0;
		}

		@Override
		public int getMiningLevel() {
			return 3;
		}

		@Override
		public int getEnchantability() {
			return 30;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.ofItems(Items.DIAMOND);
		}
	}, 10, -4f, new Item.Settings().group(ItemGroup.MISC).maxCount(1));


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");

//		myDoggy = FabricEntityTypeBuilder.create(DoggyEntity.class, (w) -> new DoggyEntity(myDoggy, w)).build();
//		EntityRendererRegistry.INSTANCE.register(ExampleMod.DOGGY_ENTITY, (entityRenderDispatcher, context) -> new DoggyRenderer(entityRenderDispatcher));
		Registry.register(Registry.ENTITY_TYPE, new Identifier("modid", "doggy_entity"), DOGGY_ENTITY);

		Registry.register(Registry.ITEM, new Identifier("modid", "doggy_spawn_egg"), new SpawnEggItem(ExampleMod.DOGGY_ENTITY, 0x6CA1C9, 0xA1B2C7, new Item.Settings().group(ItemGroup.MISC)));

		Registry.register(Registry.ITEM, new Identifier("modid", "emerald_bow"), EMERALD_BOW_ITEM);
		Registry.register(Registry.ITEM, new Identifier("modid", "teleport_item"), TELEPORT_ITEM);
		Registry.register(Registry.ITEM, new Identifier("modid", "fabric_item"), FABRIC_ITEM);
		Registry.register(Registry.ITEM, new Identifier("modid", "branch_pickaxe_item"), BRANCH_PICKAXE_ITEM);

		ClientTickCallback.EVENT.register(e -> {
			if (e.player == null) return;
			if (e.player.inventory.getMainHandStack().getItem().equals(ExampleMod.FABRIC_ITEM)) {
				// Toggle message
//				TranslatableText msg = new TranslatableText("ITEM");
				// Display msg above hotbar, set false to display in text chat
//				e.player.addChatMessage(msg, false);



			}
		});

	}

//	@Override
//	public void onInitializeClient() {
//		EntityRendererRegistry.INSTANCE.register(ExampleMod.DOGGY_ENTITY, ((entityRenderDispatcher, context) -> new DoggyRenderer(entityRenderDispatcher)));
//
//	}


//	public static File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), "config_1.json");

}

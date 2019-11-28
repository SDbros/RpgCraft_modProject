package com.sdbros.rpgcraft.init;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entity.RedCreeperEntity;
import com.sdbros.rpgcraft.entity.ZombieVariantEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final LazyLoadBase<EntityType<RedCreeperEntity>> RED_CREEPER = makeType("red_creeper", RedCreeperEntity::new);
    public static final LazyLoadBase<EntityType<ZombieVariantEntity>> ZOMBIE_VARIANT = makeType("zombie_variant", ZombieVariantEntity::new);


    public static void registerTypes(RegistryEvent.Register<EntityType<?>> event) {
        registerType("red_creeper", RED_CREEPER.getValue());
        registerType("zombie_variant", ZOMBIE_VARIANT.getValue());
        EntitySpawnPlacementRegistry.register(RED_CREEPER.getValue(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, RedCreeperEntity::canSpawnAt);
        EntitySpawnPlacementRegistry.register(ZOMBIE_VARIANT.getValue(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, ZombieVariantEntity::canSpawnAt);

    }

    private static <T extends Entity> LazyLoadBase<EntityType<T>> makeType(String name, EntityType.IFactory<T> factory) {
        return new LazyLoadBase(() -> EntityType.Builder.create(factory, EntityClassification.MONSTER).build(RpgCraft.getId(name).toString()));
    }

    public static void registerSpawns() {
        registerEntityWorldSpawn(RED_CREEPER.getValue(), ModBiomes.MAGICMOUNTAINS);
        registerEntityWorldSpawn(ZOMBIE_VARIANT.getValue(), ModBiomes.MAGICMOUNTAINS);
    }

    private static void registerEntityWorldSpawn(EntityType<?> entity, Biome... biomes) {
        for (Biome biome : biomes) {
            if (biome != null) {
                biome.getSpawns(entity.getClassification()).add(new Biome.SpawnListEntry(entity, 100, 1, 10));
            }
        }
    }

    public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll
                (
                        ModItems.red_creeper_spawn_egg = registerEntitySpawnEgg(RED_CREEPER.getValue(), 0xd12e2e, 0x000000, "red_creeper_spawn_egg"),
                        ModItems.zombie_variant_spawn_egg = registerEntitySpawnEgg(ZOMBIE_VARIANT.getValue(), 0x4e9154, 0x000000, "zombie_variant_spawn_egg")
                );

    }

    public static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name) {
        SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(RpgCraft.ITEM_GROUP));
        item.setRegistryName(RpgCraft.getId(name));
        return item;
    }

    private static void registerType(String name, EntityType<?> type) {
        ResourceLocation id = RpgCraft.getId(name);
        type.setRegistryName(id);
        ForgeRegistries.ENTITIES.register(type);
    }
}
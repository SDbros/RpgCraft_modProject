package com.sdbros.rpgcraft.init;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entity.mobs.ClusterCreeperEntity;
import com.sdbros.rpgcraft.entity.mobs.LumberjackEntity;
import com.sdbros.rpgcraft.entity.mobs.RedCreeperEntity;
import com.sdbros.rpgcraft.entity.mobs.ZombieVariantEntity;
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
    //types
    public static final LazyLoadBase<EntityType<ClusterCreeperEntity>> CLUSTER_CREEPER = makeType("cluster_creeper", ClusterCreeperEntity::new);
    public static final LazyLoadBase<EntityType<RedCreeperEntity>> RED_CREEPER = makeType("red_creeper", RedCreeperEntity::new);
    public static final LazyLoadBase<EntityType<ZombieVariantEntity>> ZOMBIE_VARIANT = makeType("zombie_variant", ZombieVariantEntity::new);
    public static final LazyLoadBase<EntityType<LumberjackEntity>> LUMBERJACK = makeType("lumberjack", LumberjackEntity::new);

    //eggs
    static Item cluster_creeper_spawn_egg;
    static Item red_creeper_spawn_egg;
    static Item zombie_variant_spawn_egg;
    static Item lumberjack_spawn_egg;

    public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
        //register types
        registerEntity("cluster_creeper", CLUSTER_CREEPER.getValue());
        registerEntity("red_creeper", RED_CREEPER.getValue());
        registerEntity("zombie_variant", ZOMBIE_VARIANT.getValue());
        registerEntity("lumberjack", LUMBERJACK.getValue());

        //register placement
        EntitySpawnPlacementRegistry.register(CLUSTER_CREEPER.getValue(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, ClusterCreeperEntity::canSpawnAt);
        EntitySpawnPlacementRegistry.register(RED_CREEPER.getValue(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, RedCreeperEntity::canSpawnAt);
        EntitySpawnPlacementRegistry.register(ZOMBIE_VARIANT.getValue(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, ZombieVariantEntity::canSpawnAt);
    }

    private static <T extends Entity> LazyLoadBase<EntityType<T>> makeType(String name, EntityType.IFactory<T> factory) {
        return new LazyLoadBase(() -> EntityType.Builder.create(factory, EntityClassification.MONSTER).build(RpgCraft.getId(name).toString()));
    }

    public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll
                (
                        cluster_creeper_spawn_egg = registerEntitySpawnEgg(CLUSTER_CREEPER.getValue(), 0x1B8DA0, 0x152323, "cluster_creeper_spawn_egg"),
                        red_creeper_spawn_egg = registerEntitySpawnEgg(RED_CREEPER.getValue(), 0xd12e2e, 0x000000, "red_creeper_spawn_egg"),
                        zombie_variant_spawn_egg = registerEntitySpawnEgg(ZOMBIE_VARIANT.getValue(), 0x4e9154, 0x000001, "zombie_variant_spawn_egg"),
                        lumberjack_spawn_egg = registerEntitySpawnEgg(LUMBERJACK.getValue(), 0x963a33, 0xffffff, "lumberjack_spawn_egg")
                );
    }


    private static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name) {
        SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(RpgCraft.ITEM_GROUP));
        item.setRegistryName(RpgCraft.getId(name));
        return item;
    }

    private static void registerEntity(String name, EntityType<?> type) {
        ResourceLocation id = RpgCraft.getId(name);
        type.setRegistryName(id);
        ForgeRegistries.ENTITIES.register(type);
    }
}
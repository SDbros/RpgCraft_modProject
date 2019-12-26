package com.sdbros.rpgcraft.init;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entity.*;
import com.sdbros.rpgcraft.entity.boss.CrazedSummonerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    //types
    public static final LazyLoadBase<EntityType<ClusterCreeperEntity>> CLUSTER_CREEPER = makeType("cluster_creeper", ClusterCreeperEntity::new);
    public static final LazyLoadBase<EntityType<RedCreeperEntity>> RED_CREEPER = makeType("red_creeper", RedCreeperEntity::new);
    public static final LazyLoadBase<EntityType<MutantZombieEntity>> MUTANT_ZOMBIE = makeType("mutant_zombie", MutantZombieEntity::new);
    public static final LazyLoadBase<EntityType<LumberjackEntity>> LUMBERJACK = makeType("lumberjack", LumberjackEntity::new);
    public static final LazyLoadBase<EntityType<CrazedSummonerEntity>> CRAZED_SUMMONER = makeType("crazed_summoner", CrazedSummonerEntity::new);
    public static final LazyLoadBase<EntityType<CloudSlimeEntity>> CLOUD_SLIME = makeType("cloud_slime", CloudSlimeEntity::new);

    //eggs
    static Item cluster_creeper_spawn_egg;
    static Item red_creeper_spawn_egg;
    static Item mutant_zombie_variant_spawn_egg;
    static Item lumberjack_spawn_egg;
    static Item crazed_summoner_spawn_egg;
    static Item cloud_slime_spawn_egg;

    public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
        //register types
        registerEntity("cluster_creeper", CLUSTER_CREEPER.getValue());
        registerEntity("red_creeper", RED_CREEPER.getValue());
        registerEntity("mutant_zombie", MUTANT_ZOMBIE.getValue());
        registerEntity("lumberjack", LUMBERJACK.getValue());
        registerEntity("crazed_summoner", CRAZED_SUMMONER.getValue());
        registerEntity("cloud_slime", CLOUD_SLIME.getValue());

        //register placement
        EntitySpawnPlacementRegistry.register(CLUSTER_CREEPER.getValue(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, ClusterCreeperEntity::canSpawnAt);
        EntitySpawnPlacementRegistry.register(RED_CREEPER.getValue(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, RedCreeperEntity::canSpawnAt);
        EntitySpawnPlacementRegistry.register(MUTANT_ZOMBIE.getValue(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, MutantZombieEntity::canSpawnAt);
        EntitySpawnPlacementRegistry.register(CLOUD_SLIME.getValue(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CloudSlimeEntity::canSpawnAt);
    }

    private static <T extends Entity> LazyLoadBase<EntityType<T>> makeType(String name, EntityType.IFactory<T> factory) {
        return new LazyLoadBase(() -> EntityType.Builder.create(factory, EntityClassification.MONSTER).build(RpgCraft.getId(name).toString()));
    }

    public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll
                (
                        cluster_creeper_spawn_egg = registerEntitySpawnEgg(CLUSTER_CREEPER.getValue(), 0x1B8DA0, 0x152323, "cluster_creeper_spawn_egg"),
                        red_creeper_spawn_egg = registerEntitySpawnEgg(RED_CREEPER.getValue(), 0xd12e2e, 0x000000, "red_creeper_spawn_egg"),
                        mutant_zombie_variant_spawn_egg = registerEntitySpawnEgg(MUTANT_ZOMBIE.getValue(), 0x4e9154, 0x000001, "mutant_zombie_spawn_egg"),
                        lumberjack_spawn_egg = registerEntitySpawnEgg(LUMBERJACK.getValue(), 0x963a33, 0xffffff, "lumberjack_spawn_egg"),
                        crazed_summoner_spawn_egg = registerEntitySpawnEgg(CRAZED_SUMMONER.getValue(), 0x1B8DA0, 0x4e9154, "crazed_summoner_spawn_egg"),
                        cloud_slime_spawn_egg = registerEntitySpawnEgg(CLOUD_SLIME.getValue(), 0xd9ecff, 0x326da8, "cloud_slime_spawn_egg")
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
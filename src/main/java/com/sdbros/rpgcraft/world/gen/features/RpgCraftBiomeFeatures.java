package com.sdbros.rpgcraft.world.gen.features;

import com.sdbros.rpgcraft.init.ModBiomes;
import com.sdbros.rpgcraft.init.ModEntities;
import com.sdbros.rpgcraft.init.ModFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sdbros.rpgcraft.block.Ores.COPPER;
import static com.sdbros.rpgcraft.block.Ores.RUNITE;

public class RpgCraftBiomeFeatures {

    public static void init() {
        generateOres();
        generateStructures();
        registerEntitySpawns();
        generateBiomeSpecifics();
    }

    //used to add Ores to all biomes
    private static void generateOres() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(FillerBlockType.NATURAL_STONE, COPPER.getOreBlock().getDefaultState(), 10), Placement.COUNT_RANGE, new CountRangeConfig(10, 20, 0, 100)));
            biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(FillerBlockType.NATURAL_STONE, RUNITE.getOreBlock().getDefaultState(), 10), Placement.COUNT_RANGE, new CountRangeConfig(5, 20, 0, 36)));
        }
    }

    //used to add Structures to all biomes
    private static void generateStructures() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            biome.addStructure(ModFeatures.BROKEN_STRUCTURE, IFeatureConfig.NO_FEATURE_CONFIG);
            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(ModFeatures.BROKEN_STRUCTURE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, NoPlacementConfig.NO_PLACEMENT_CONFIG));
        }
    }

    //used to add Mobs to all biomes
    private static void registerEntitySpawns() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            biome.getSpawns(ModEntities.CLUSTER_CREEPER.getValue().getClassification()).add(new Biome.SpawnListEntry(ModEntities.CLUSTER_CREEPER.getValue(), 80, 1, 1));
            biome.getSpawns(ModEntities.ZOMBIE_VARIANT.getValue().getClassification()).add(new Biome.SpawnListEntry(ModEntities.ZOMBIE_VARIANT.getValue(), 50, 1, 2));
        }
    }

    //used to add something to specified biomes only
    private static void generateBiomeSpecifics() {
        registerBiomeSpecificSpawns(ModEntities.RED_CREEPER.getValue(), 95, 1, 3, ModBiomes.MAGICMOUNTAINS);
        generateBiomeSpecificStructures(ModFeatures.MAGIC_HOUSE_STRUCTURE, IFeatureConfig.NO_FEATURE_CONFIG, ModBiomes.MAGICMOUNTAINS, Biomes.OCEAN);
        //generateBiomeSpecificStructures(ModFeatures.MAGIC_HOUSE_STRUCTURE, IFeatureConfig.NO_FEATURE_CONFIG, ModBiomes.MAGICMOUNTAINS, Biomes.OCEAN);
        generateBiomeSpecificFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(ModFeatures.BROKEN_STRUCTURE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, NoPlacementConfig.NO_PLACEMENT_CONFIG), ModBiomes.MAGICMOUNTAINS, Biomes.OCEAN);
        //generateBiomeSpecificFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(ModFeatures.BROKEN_STRUCTURE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, NoPlacementConfig.NO_PLACEMENT_CONFIG), ModBiomes.MAGICMOUNTAINS, Biomes.OCEAN);

    }

    private static void registerBiomeSpecificSpawns(EntityType<?> entity, int weight, int minGroupCountIn, int maxGroupCountIn, Biome... biomes) {
        for (Biome biome : biomes) {
            if (biome != null) {
                biome.getSpawns(entity.getClassification()).add(new Biome.SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
            }
        }
    }

    private static void generateBiomeSpecificStructures(Structure<NoFeatureConfig> structure, NoFeatureConfig noFeatureConfig, Biome... biomes) {
        for (Biome biome : biomes) {
            if (biome != null) {
                biome.addStructure(structure, noFeatureConfig);
            }
        }
    }

    private static void generateBiomeSpecificFeatures(Decoration decoration ,ConfiguredFeature<?> decoratedFeature, Biome... biomes) {
        for (Biome biome : biomes) {
            if (biome != null) {
                biome.addFeature(decoration, decoratedFeature);
            }
        }
    }

}

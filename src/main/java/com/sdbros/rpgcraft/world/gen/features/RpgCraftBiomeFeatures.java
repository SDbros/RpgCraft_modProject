package com.sdbros.rpgcraft.world.gen.features;

import com.sdbros.rpgcraft.init.ModBiomes;
import com.sdbros.rpgcraft.init.ModEntities;
import com.sdbros.rpgcraft.init.ModFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sdbros.rpgcraft.block.Ores.COPPER;
import static com.sdbros.rpgcraft.block.Ores.RUNITE;

public class RpgCraftBiomeFeatures {

    public static void init() {
        generateOres();
        generateStructures();
        registerEntitySpawns();
        registerBiomeSpecificSpawns();
    }

    private static void generateOres() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(FillerBlockType.NATURAL_STONE, COPPER.getOreBlock().getDefaultState(), 10), Placement.COUNT_RANGE, new CountRangeConfig(10, 20, 0, 100)));
            biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(FillerBlockType.NATURAL_STONE, RUNITE.getOreBlock().getDefaultState(), 10), Placement.COUNT_RANGE, new CountRangeConfig(5, 20, 0, 36)));
        }
    }

    private static void generateStructures() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            biome.addStructure(ModFeatures.BROKEN_STRUCTURE, IFeatureConfig.NO_FEATURE_CONFIG);
            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(ModFeatures.BROKEN_STRUCTURE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, NoPlacementConfig.NO_PLACEMENT_CONFIG));
        }
    }

    //used to add mob to all biomes
    private static void registerEntitySpawns() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            biome.getSpawns(ModEntities.RED_CREEPER.getValue().getClassification()).add(new Biome.SpawnListEntry(ModEntities.RED_CREEPER.getValue(), 70, 1, 3));
            biome.getSpawns(ModEntities.ZOMBIE_VARIANT.getValue().getClassification()).add(new Biome.SpawnListEntry(ModEntities.ZOMBIE_VARIANT.getValue(), 80, 1, 1));
        }
    }

    //used to add mob to specific biome
    private static void registerBiomeSpecificSpawns() {
        registerEntityWorldSpawn(ModEntities.RED_CREEPER.getValue(), 120, 2, 5, ModBiomes.MAGICMOUNTAINS);
    }

    private static void registerEntityWorldSpawn(EntityType<?> entity, int weight, int minGroupCountIn, int maxGroupCountIn, Biome... biomes) {
        for (Biome biome : biomes) {
            if (biome != null) {
                biome.getSpawns(entity.getClassification()).add(new Biome.SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
                //int weight, int minGroupCountIn, int maxGroupCountIn
            }
        }
    }
}

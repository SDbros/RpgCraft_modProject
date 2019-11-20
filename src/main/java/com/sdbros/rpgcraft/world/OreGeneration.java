package com.sdbros.rpgcraft.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sdbros.rpgcraft.block.Ores.COPPER;
import static com.sdbros.rpgcraft.block.Ores.RUNITE;

public class OreGeneration {

    public static void register() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(FillerBlockType.NATURAL_STONE, COPPER.getOreBlock().getDefaultState(), 10), Placement.COUNT_RANGE, new CountRangeConfig(10, 20, 0, 100)));
            biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(FillerBlockType.NATURAL_STONE, RUNITE.getOreBlock().getDefaultState(), 10), Placement.COUNT_RANGE, new CountRangeConfig(5, 20, 0, 36)));
        }
    }
}

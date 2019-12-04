package com.sdbros.rpgcraft.world.gen.biomes;

import com.sdbros.rpgcraft.init.ModEntities;
import com.sdbros.rpgcraft.init.ModFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;


public class MagicMountains extends Biome {
    public MagicMountains() {
        super((new Biome.Builder())
                .surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState())))
                .precipitation(RainType.RAIN)
                .category(Category.NETHER)
                .downfall(0.2f)
                .depth(0.4f)
                .scale(1.3f)
                .temperature(0.8f)
                .waterColor(0x34e8eb)
                .waterFogColor(0x34e8eb)
                .parent(null));

        //this.addStructure(ModFeatures.BROKEN_STRUCTURE, IFeatureConfig.NO_FEATURE_CONFIG);
        //this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(ModFeatures.BROKEN_STRUCTURE, IFeatureConfig.NO_FEATURE_CONFIG,  Placement.NOPE, NoPlacementConfig.NO_PLACEMENT_CONFIG));
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addGrass(this);
        DefaultBiomeFeatures.addForestTrees(this);
        DefaultBiomeFeatures.addBirchTrees(this);
        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addSedimentDisks(this);
        DefaultBiomeFeatures.addMushrooms(this);
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createDecoratedFeature(Feature.SEAGRASS, new SeaGrassConfig(48, 0.4D), Placement.TOP_SOLID_HEIGHTMAP, IPlacementConfig.NO_PLACEMENT_CONFIG));


        //MineCraft
        this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 2, 1, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 10, 1, 5));
        this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 1, 8, 8));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 20, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 20, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.DROWNED, 20, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 10, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 20, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 10, 1, 1));
    }

    public MagicMountains(Builder parent) {
        super(parent);
    }
}

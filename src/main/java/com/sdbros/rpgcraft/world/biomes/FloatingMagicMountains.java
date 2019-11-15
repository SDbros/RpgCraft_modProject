package com.sdbros.rpgcraft.world.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FloatingMagicMountains extends MagicMountains {
    public FloatingMagicMountains() {
        super((new Biome.Builder())
                .surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)
                .precipitation(RainType.RAIN)
                .category(Biome.Category.THEEND)
                .depth(0.1F)
                .scale(0.2F)
                .temperature(0.5F)
                .downfall(0.5F)
                .waterColor(4159204)
                .waterFogColor(329011)
                .parent(null));

        this.addFeature(GenerationStage.Decoration.RAW_GENERATION, createDecoratedFeature(Feature.END_ISLAND, IFeatureConfig.NO_FEATURE_CONFIG, Placement.END_ISLAND, IPlacementConfig.NO_PLACEMENT_CONFIG));
    }

    /**
     * takes temperature, returns color
     */
    @OnlyIn(Dist.CLIENT)
    public int getSkyColorByTemp(float currentTemperature) {
        return 0;
    }
}


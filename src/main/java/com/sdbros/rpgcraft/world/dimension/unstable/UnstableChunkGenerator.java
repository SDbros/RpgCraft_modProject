package com.sdbros.rpgcraft.world.dimension.unstable;

import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;


public class UnstableChunkGenerator extends NoiseChunkGenerator<GenerationSettings> {

    //todo make the bottom of the island round like in the end
    public UnstableChunkGenerator(IWorld world, BiomeProvider biomeProvider, GenerationSettings settings) {
        super(world, biomeProvider, 8, 4, 128,settings, true);
    }

    @Override
    protected void func_222548_a(double[] p_222548_1_, int p_222548_2_, int p_222548_3_) {
        double d0 = 1368.824D;
        double d1 = 684.412D;
        double d2 = 17.110300000000002D;
        double d3 = 4.277575000000001D;
        int i = 64;
        int j = -3000;
        this.func_222546_a(p_222548_1_, p_222548_2_, p_222548_3_, 1368.824D, 684.412D, 17.110300000000002D, 4.277575000000001D, 64, -3000);
    }

    @Override
    protected double[] func_222549_a(int p_222549_1_, int p_222549_2_) {
        return new double[]{(double)this.biomeProvider.func_222365_c(p_222549_1_, p_222549_2_), 0.0D};
    }

    @Override
    protected double func_222545_a(double p_222545_1_, double p_222545_3_, int p_222545_5_) {
        return 8.0D - p_222545_1_;
    }

    @Override
    public int getGroundHeight() {
        return 1;
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public boolean hasStructure(Biome biomeIn, Structure<? extends IFeatureConfig> structureIn) {
        return false;
    }
}

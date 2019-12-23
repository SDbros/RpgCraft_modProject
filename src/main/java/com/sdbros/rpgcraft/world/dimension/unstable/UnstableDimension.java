package com.sdbros.rpgcraft.world.dimension.unstable;

import com.sdbros.rpgcraft.config.DimensionConfig;
import com.sdbros.rpgcraft.init.ModBiomes;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class UnstableDimension extends Dimension {

    public UnstableDimension(World world, DimensionType type) {
        super(world, type);
    }

    //todo generate floating magic mountains instead of plains biome
    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        return ModBiomes.generatorType.create(this.world, ModBiomes.biomeProviderType.create(ModBiomes.biomeProviderType.createSettings().setBiome(Biomes.PLAINS)), ModBiomes.generatorType.createSettings());
    }

    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos chunkPos, boolean checkValid) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(int x, int z, boolean checkValid) {
        return null;
    }

    //todo fix sky box and sun/moon not showing
    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        if (DimensionConfig.day.get()) {
            int j = 6000;
            float f1 = (j + partialTicks) / 24000.0F - 0.25F;

            if (f1 < 0.0F) {
                f1 += 1.0F;
            }

            if (f1 > 1.0F) {
                f1 -= 1.0F;
            }

            float f2 = f1;
            f1 = 1.0F - (float) ((Math.cos(f1 * Math.PI) + 1.0D) / 2.0D);
            f1 = f2 + (f1 - f2) / 3.0F;
            return f1;
        } else {
            int i = (int) (worldTime % 24000L);
            float f = ((float) i + partialTicks) / 24000.0F - 0.25F;

            if (f < 0.0F) {
                ++f;
            }

            if (f > 1.0F) {
                --f;
            }

            float f1 = 1.0F - (float) ((Math.cos((double) f * Math.PI) + 1.0D) / 2.0D);
            f = f + (f1 - f) / 3.0F;
            return f;
        }
    }


    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        float f = MathHelper.cos(p_76562_1_ * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        float f1 = 0.7529412F;
        float f2 = 0.84705883F;
        float f3 = 1.0F;
        f1 = f1 * (f * 0.94F + 0.06F);
        f2 = f2 * (f * 0.94F + 0.06F);
        f3 = f3 * (f * 0.91F + 0.09F);
        return new Vec3d((double) f1, (double) f2, (double) f3);
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }

    @Override
    public long getWorldTime() {
        long ret = super.getWorldTime();
        return ret;
    }

    @Override
    public boolean hasSkyLight() {
        return true;
    }
}

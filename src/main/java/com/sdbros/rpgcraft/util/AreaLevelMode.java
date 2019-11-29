package com.sdbros.rpgcraft.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.Locale;
import java.util.Random;

public enum AreaLevelMode {

    DISTANCE_FROM_SPAWN {
        @Override
        public double getAreaLevel(World world, BlockPos pos, int radius) {
            double dx = Math.abs(pos.getX() - world.getSpawnPoint().getX());
            double dz = Math.abs(pos.getZ() - world.getSpawnPoint().getZ());
            double distance = (Math.pow((Math.pow(dz, 2) + Math.pow(dx, 2)), 0.5) / 1000);
            double areaLevel = 1.5 + (-0.065 * Math.pow(distance, 3)) + Math.pow(distance, 2) + 0.6 * distance;
            Random random = new Random();
            switch (random.nextInt(5)) {
                case 0:
                    return areaLevel * 0.8;
                case 1:
                    return areaLevel * 0.9;
                case 2:
                    return areaLevel;
                case 3:
                    return areaLevel * 1.1;
                case 4:
                    return areaLevel * 1.2;
            }
            return areaLevel;
        }
    },

    DIMENSION_WIDE {
        @Override
        public double getAreaLevel(World world, BlockPos pos, int radius) {
            return Level.source(world).getLevel();
        }
    };

    public abstract double getAreaLevel(World world, BlockPos pos, int radius);

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("config.rpgcraft.area_level." + name().toLowerCase(Locale.ROOT));
    }

    public static AreaLevelMode fromOrdinal(int ordinal) {
        return values()[MathHelper.clamp(ordinal, 0, values().length - 1)];
    }
}

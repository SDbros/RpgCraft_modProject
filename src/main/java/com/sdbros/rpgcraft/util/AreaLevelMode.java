/*
 * Scaling Health
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.sdbros.rpgcraft.util;

import com.sdbros.rpgcraft.capability.IMobData;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.Locale;
import java.util.Random;

public enum AreaLevelMode {
    MIN_LEVEL {
        @Override
        public double getAreaLevel(World world, BlockPos pos, int radius) {
            double min = Level.maxValue(world);
            for (Tuple<BlockPos, IMobData> tuple : Level.sources(world, pos, radius)) {
                min = Math.min(tuple.getB().getLevel(), min);
            }
            return min;
        }
    },
    MAX_LEVEL {
        @Override
        public double getAreaLevel(World world, BlockPos pos, int radius) {
            double max = 1;
            for (Tuple<BlockPos, IMobData> tuple : Level.sources(world, pos, radius)) {
                max = Math.max(tuple.getB().getLevel(), max);
            }
            return max;
        }
    },
    DISTANCE_FROM_SPAWN {
        @Override
        public double getAreaLevel(World world, BlockPos pos, int radius) {
            double dx = Math.abs(pos.getX() - world.getSpawnPoint().getX());
            double dz = Math.abs(pos.getZ() - world.getSpawnPoint().getZ());
            double distance = (Math.pow((Math.pow(dz , 2) + Math.pow(dx , 2)), 0.5)/1000);
            double result = 1.5;
            double result1 = (-0.065 * Math.pow(distance,3));
            double result2 = Math.pow(distance,2);
            double result3 = (0.6 * distance);
            double result4 = result + result1 + result2 + result3;
            Random random = new Random();
            switch(random.nextInt(5)){
                case 0:
                    return result4 * 0.8;

                case 1:
                    return result4 * 0.9;
                case 2:
                    return result4;
                case 3:
                    return result4 * 1.1;
                case 4:
                    return result4 * 1.2;
            }

            return result4;

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

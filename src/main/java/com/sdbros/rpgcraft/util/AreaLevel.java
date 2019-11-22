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

public enum AreaLevel {
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
            double dx = Math.abs(pos.getX() - world.getSpawnPoint().getX()) / 160F;
            double dz = Math.abs(pos.getZ() - world.getSpawnPoint().getZ()) / 160F;
            return dx + dz;
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

    public static AreaLevel fromOrdinal(int ordinal) {
        return values()[MathHelper.clamp(ordinal, 0, values().length - 1)];
    }
}

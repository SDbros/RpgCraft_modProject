package com.sdbros.rpgcraft.util;

import com.sdbros.rpgcraft.capabilities.MobCapability;
import com.sdbros.rpgcraft.capabilities.MobCapability.*;
import com.sdbros.rpgcraft.config.DimensionConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public final class Level {

    public static IMobCapabilityHandler source(ICapabilityProvider source) {
        return source.getCapability(MobCapability.MOB_INSTANCE)
                .orElseGet(MobCapabilityData::new);
    }

    public static double areaLevel(World world, BlockPos pos) {
        return AreaLevelMode.DISTANCE_FROM_SPAWN.getAreaLevel(world, pos, searchRadius(world));
    }

    //todo use Config
    public static AreaLevelMode getAreaLevelMode() {
        return AreaLevelMode.DISTANCE_FROM_SPAWN;
    }

    public static int searchRadius(IWorldReader world) {
        final int radius = DimensionConfig.searchRadius.get();
        return radius <= 0 ? Integer.MAX_VALUE : radius;
    }

    public static double maxLevel(IWorldReader world) {
        return DimensionConfig.maxLevel.get();
    }

//    public static boolean allowsDifficultyChanges(MobEntity entity) {
//        return true;
//    }

//    public static int startingLevel(MobEntity mob) {
//        return 1;
//    }
}

package com.sdbros.rpgcraft.util;

import com.sdbros.rpgcraft.capability.MobCapability;
import com.sdbros.rpgcraft.capability.MobCapability.*;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IEntityReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public final class Level {

    private Level() {
        throw new IllegalAccessError("Utility class");
    }

    public static IMobCapabilityHandler source(ICapabilityProvider source) {
        return source.getCapability(MobCapability.INSTANCE)
                .orElseGet(MobCapabilityData::new);
    }

    public static double areaLevel(World world, BlockPos pos) {
        return AreaLevelMode.DISTANCE_FROM_SPAWN.getAreaLevel(world, pos, searchRadius(world));
    }

    //todo use Config
    public static AreaLevelMode getAreaLevelMode() {
        return AreaLevelMode.DISTANCE_FROM_SPAWN;
    }

    //todo use Config
    public static int searchRadius(IWorldReader world) {
        //final int radius = Config.get(world).difficulty.searchRadius.get();
        //return radius <= 0 ? Integer.MAX_VALUE : radius;
        return 50;
    }

    //todo use Config
    public static double maxValue(IWorldReader world) {
        //return Config.get(world).level.maxValue.get();
        return 50;
    }

    //todo use Config
    public static boolean allowsDifficultyChanges(MobEntity entity) {
        return true;
    }

    public static int startingLevel(MobEntity mob) {
        return 1;
    }
}

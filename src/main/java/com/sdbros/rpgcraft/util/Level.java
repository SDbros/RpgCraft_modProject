package com.sdbros.rpgcraft.util;

import com.sdbros.rpgcraft.capability.IMobData;
import com.sdbros.rpgcraft.capability.MobDataCapability;
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

    public static IMobData affected(ICapabilityProvider entity) {
        return entity.getCapability(MobDataCapability.INSTANCE)
                .orElseGet(MobDataCapability::new);
    }

    public static IMobData source(ICapabilityProvider source) {
        return source.getCapability(MobDataCapability.INSTANCE)
                .orElseGet(MobDataCapability::new);
    }

    public static Collection<Tuple<BlockPos, IMobData>> sources(IEntityReader world, Vec3i center, long radius) {
        Collection<Tuple<BlockPos, IMobData>> list = new ArrayList<>();

        // Get players
        playersInRange(world, center, radius).forEach(player -> {
            player.getCapability(MobDataCapability.INSTANCE).ifPresent(source -> {
                list.add(new Tuple<>(player.getPosition(), source));
            });
        });
        return list;
    }

    public static Stream<? extends PlayerEntity> playersInRange(IEntityReader world, Vec3i center, long radius) {
        long radiusSquared = radius * radius;
        return world.getPlayers().stream().filter(p -> radius <= 0); //|| MCMathUtils.distanceSq(p, center) < radiusSquared);
    }


    public static double areaLevel(World world, BlockPos pos) {
        return AreaLevelMode.DISTANCE_FROM_SPAWN.getAreaLevel(world, pos, searchRadius(world));
    }

    public static AreaLevelMode getAreaLevelMode(){
        return AreaLevelMode.DISTANCE_FROM_SPAWN;
    }

    public static int searchRadius(IWorldReader world) {
//        final int radius = Config.get(world).difficulty.searchRadius.get();
//        return radius <= 0 ? Integer.MAX_VALUE : radius;
        return 50;
    }

    public static double minValue(IWorldReader world) {
        //return Config.get(world).level.minValue.get();
        return 1;
    }

    public static double maxValue(IWorldReader world) {
        //return Config.get(world).level.maxValue.get();
        return 50;
    }
    public static boolean allowsDifficultyChanges(MobEntity entity) {
        return true;
    }

    public static double damageBoostScale(MobEntity entity) {
        //return Config.get(entity).mobs.damageBoostScale.get();
        return 1;
    }

    public static double maxDamageBoost(MobEntity entity) {
        //return Config.get(entity).mobs.maxDamageBoost.get();
        return 40;
    }

    public static int startingLevel(MobEntity mob) {
        return 1;
    }
}

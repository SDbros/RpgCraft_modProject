package com.sdbros.rpgcraft.util;

import com.sdbros.rpgcraft.capability.IMobData;
import com.sdbros.rpgcraft.capability.MobDataCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

    public static double ofEntity(Entity entity) {
        if (entity instanceof PlayerEntity)
            return source(entity).getLevel();
        return affected(entity).getLevel();
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

    public static boolean enabledIn(World world) {
        //return Config.get(world).difficulty.maxValue.get() > 0 /*&& ModGameRules.DIFFICULTY.getBoolean(world)*/;
        return true;
    }

    public static double areaDifficulty(World world, BlockPos pos) {
        return areaDifficulty(world, pos, true);
    }

    public static double areaDifficulty(World world, BlockPos pos, boolean groupBonus) {
        //return areaMode(world).getAreaDifficulty(world, pos, groupBonus);
        return 5;
    }

    public static double locationMultiplier(IWorldReader world, BlockPos pos) {
        //return Config.get(world).difficulty.getLocationMultiplier(world, pos);
        return 2;
    }

//    public static double lunarMultiplier(World world) {
//        DimensionConfig config = Config.get(world);
//        if (!config.difficulty.lunarCyclesEnabled.get()) return 1.0;
//        List<? extends Double> values = config.difficulty.lunarCycleMultipliers.get();
//        if (values.isEmpty()) return 1.0;
//        int phase = world.getDimension().getMoonPhase(world.getGameTime());
//        return values.get(MathHelper.clamp(phase, 0, values.size() - 1));
//    }

//    public static double withGroupBonus(World world, BlockPos pos, double difficulty) {
//        DimensionConfig config = Config.get(world);
//        Expression expression = config.difficulty.groupAreaBonus.get();
//        return difficulty * EvalVars.apply(config, world, pos, null, expression);
//    }

//    public static AreaDifficultyMode areaMode(IWorldReader world) {
//        return Config.get(world).difficulty.areaMode.get();
//    }

    public static double clamp(IWorldReader world, double difficulty) {
        return MathHelper.clamp(difficulty, minValue(world), maxValue(world));
    }

    public static int searchRadius(IWorldReader world) {
//        final int radius = Config.get(world).difficulty.searchRadius.get();
//        return radius <= 0 ? Integer.MAX_VALUE : radius;
        return 50;
    }

    public static long searchRadiusSquared(IWorldReader world) {
        final long radius = searchRadius(world);
        return radius * radius;
    }

    public static double distanceFactor(IWorldReader world) {
        //return Config.get(world).level.distanceFactor.get();
        return 2;
    }

    public static double minValue(IWorldReader world) {
        //return Config.get(world).level.minValue.get();
        return 1;
    }

    public static double maxValue(IWorldReader world) {
        //return Config.get(world).level.maxValue.get();
        return 50;
    }

    public static double changePerSecond(IWorldReader world) {
        //return Config.get(world).difficulty.changePerSecond.get();
        return 5;
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

}

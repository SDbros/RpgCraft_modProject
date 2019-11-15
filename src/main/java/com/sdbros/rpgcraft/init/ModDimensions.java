package com.sdbros.rpgcraft.init;

import com.sdbros.rpgcraft.RpgCraft;

import com.sdbros.rpgcraft.world.dimension.unstable.UnstableDimension;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.BiFunction;

@ObjectHolder(RpgCraft.MOD_ID)
public final class ModDimensions {

    public static ModDimension UNSTABLE_DIMENSION = new ModDimension() {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return UnstableDimension::new;
        }
    }.setRegistryName(RpgCraft.getId("unstable_dimension"));

}


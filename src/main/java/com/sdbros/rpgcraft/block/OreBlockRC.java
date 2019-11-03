package com.sdbros.rpgcraft.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;


public class OreBlockRC extends OreBlock {

    public OreBlockRC() {
        this(Properties.create(Material.ROCK).hardnessAndResistance(3, 3));
    }

    public OreBlockRC(Properties builder) {
        super(builder);
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? MathHelper.nextInt(RANDOM, 1, 5) : 0;
    }
    @Override
    public int getHarvestLevel(BlockState state) {
        // Todo FIXME: Not working?
        return 2;
    }
}

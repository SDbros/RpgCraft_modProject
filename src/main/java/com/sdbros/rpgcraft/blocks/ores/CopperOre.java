package com.sdbros.rpgcraft.blocks.ores;


import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

import java.util.Random;


public class CopperOre extends OreBlock {
    public CopperOre() {
        super(Block.Properties.create(Material.ROCK)
                        .hardnessAndResistance(3.0f));

        setRegistryName("copperore");
    }

    @Override
    protected int getExperience(Random random) {
        return MathHelper.nextInt(random, 0, 2);
    }


}

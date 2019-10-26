package com.sdbros.rpgcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class FirstBlock extends Block {
    public FirstBlock() {
        super(Properties.create(Material.PACKED_ICE)
                .sound(SoundType.LADDER)
                .hardnessAndResistance(6.0f)
                .lightValue(7)
        );
        setRegistryName("firstblock");
    }
}

package com.sdbros.rpgcraft.block;

import com.sdbros.rpgcraft.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.registries.ObjectHolder;


@ObjectHolder("rpgcraft")
public class BlocksRC {
    public static final Block TEST = register("test", new Block(Block.Properties.create(Material.GLASS, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)));
    public static final Block GRANITE2 = register("granite2", new Block(Block.Properties.create(Material.ROCK, MaterialColor.DIRT).hardnessAndResistance(1.5F, 6.0F)));

    private static <T extends Block> T register(String name, T block) {
        ModBlocks.BLOCKS_TO_REGISTER.put(name, block);
        return block;
    }
}

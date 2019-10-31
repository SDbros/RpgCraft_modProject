package com.sdbros.rpgcraft.blocks;

import com.sdbros.rpgcraft.blocks.ores.CopperOre;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder("rpgcraft:firstblock")
    public static FirstBlock FIRSTBLOCK;

    @ObjectHolder("rpgcraft:backpack")
    public static BackPack BACKPACK;

    @ObjectHolder("rpgcraft:copperore")
    public static CopperOre COPPER_ORE;

    @ObjectHolder("rpgcraft:backpack")
    public static TileEntityType<BackPackTile> BACKPACK_TILE;

    @ObjectHolder("rpgcraft:backpack")
    public static ContainerType<BackPackContainer> BACKPACK_CONTAINER;
}

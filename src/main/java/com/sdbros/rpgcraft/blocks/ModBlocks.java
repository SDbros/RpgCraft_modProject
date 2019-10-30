package com.sdbros.rpgcraft.blocks;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder("rpgcraft:firstblock")
    public static FirstBlock FIRSTBLOCK;

    @ObjectHolder("rpgcraft:backpack")
    public static BackPack BACKPACK;

    @ObjectHolder("rpgcraft:backpack")
    public static TileEntityType<BackPackTile> BACKPACK_TILE;

    @ObjectHolder("rpgcraft:backpack")
    public static ContainerType<BackPackContainer> BACKPACK_CONTAINER;
}

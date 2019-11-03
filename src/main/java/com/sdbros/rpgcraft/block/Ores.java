package com.sdbros.rpgcraft.block;

import com.sdbros.rpgcraft.RpgCraft;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.*;

import java.util.Locale;

public enum Ores {
    RUNITE,
    COPPER;

    private final LazyLoadBase<OreBlockRC> oreBlock;
    private final LazyLoadBase<Block> storageBlock;
    private final LazyLoadBase<Item> oreItem;

    Ores() {
        oreBlock = new LazyLoadBase<>(OreBlockRC::new);
        storageBlock = new LazyLoadBase<>(() -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(5, 6).sound(SoundType.METAL)));
        oreItem = new LazyLoadBase<>(() -> new Item(new Item.Properties().group(RpgCraft.ITEM_GROUP)));
    }

    public OreBlockRC getOreBlock() {
        return this.oreBlock.getValue();
    }

    public Block getStorageBlock() {
        return storageBlock.getValue();
    }

    public String getName() {
        // Locale.ROOT will ensure consistent behavior (prevent crashes) on all locales
        return name().toLowerCase(Locale.ROOT);
    }

    public Item getOreItem() {
        return oreItem.getValue();
    }

}
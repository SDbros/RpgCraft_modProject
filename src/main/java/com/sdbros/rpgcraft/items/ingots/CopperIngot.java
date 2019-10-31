package com.sdbros.rpgcraft.items.ingots;

import com.sdbros.rpgcraft.RpgCraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class CopperIngot extends Item {
    public CopperIngot() {
        super(new Item.Properties()
                .maxStackSize(64)
                .group(RpgCraft.setup.itemGroup));
        setRegistryName("copperingot");
    }
}

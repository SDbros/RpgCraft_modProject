package com.sdbros.rpgcraft.items;

import com.sdbros.rpgcraft.RpgCraft;
import net.minecraft.item.Item;

public class FirstItem extends Item {

    public FirstItem() {
        super(new Item.Properties()
                .maxStackSize(1)
                .group(RpgCraft.setup.itemGroup));
        setRegistryName("firstitem");
    }
}

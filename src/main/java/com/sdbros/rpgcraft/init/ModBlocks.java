package com.sdbros.rpgcraft.init;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.block.Ores;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;


import javax.annotation.Nullable;

import static com.sdbros.rpgcraft.RpgCraft.ITEM_GROUP;

public final class ModBlocks {

    public static IItemProvider copperIngot;

    private ModBlocks() {
    }

    public static void registerAll(RegistryEvent.Register<Block> event) {

        for (Ores ore : Ores.values()) {
            // Names will be: ruby_block, sapphire_block
            // This comment is, of course, not necessary, so you can remove it
            register(ore.getName() + "_block", ore.getStorageBlock());
        }

        for (Ores ore : Ores.values()) {
            register(ore.getName()+ "_ore", ore.getOreBlock());
        }
    }

    private static <T extends Block> T register(String name, T block) {
        BlockItem item = new BlockItem(block, new Item.Properties().group(ITEM_GROUP));
        return register(name, block, item);
    }

    private static <T extends Block> T register(String name, T block, @Nullable BlockItem item) {
        ResourceLocation id = RpgCraft.getId(name);
        block.setRegistryName(id);
        ForgeRegistries.BLOCKS.register(block);
        if (item != null) {
            ModItems.BLOCKS_TO_REGISTER.put(name, item);
        }
        return block;
    }
}

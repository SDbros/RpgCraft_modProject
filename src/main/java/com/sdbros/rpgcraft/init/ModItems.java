package com.sdbros.rpgcraft.init;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.block.Ores;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModItems {

    static final Map<String, BlockItem> BLOCKS_TO_REGISTER = new LinkedHashMap<>();

    public static Item red_creeper_spawn_egg;

    private ModItems() {
    }

    public static void registerAll(RegistryEvent.Register<Item> event) {

        // Blocks
        BLOCKS_TO_REGISTER.forEach(ModItems::register);

        // Items
        for (Ores ore : Ores.values()) {
            register(ore.getName() + "_ingot", ore.getOreItem());
        }
    }

    private static <T extends Item> T register(String name, T item) {
        ResourceLocation id = RpgCraft.getId(name);
        item.setRegistryName(id);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }
}
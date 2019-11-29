package com.sdbros.rpgcraft.init;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.block.Ores;
import com.sdbros.rpgcraft.item.Armours;
import com.sdbros.rpgcraft.item.ItemsRC;
import com.sdbros.rpgcraft.item.Tools;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModItems {

    //HashMaps for quick registration
    static final Map<String, BlockItem> BLOCKITEMS_TO_REGISTER = new LinkedHashMap<>();
    public static final Map<String, Item> ITEMS_TO_REGISTER = new LinkedHashMap<>();

    public static void registerAll(RegistryEvent.Register<Item> event) {

        // BlocksRC
        BLOCKITEMS_TO_REGISTER.forEach(ModItems::register);

        new ItemsRC();
        ITEMS_TO_REGISTER.forEach(ModItems::register);

        // Items
        for (Ores ore : Ores.values()) {
            register(ore.getName() + "_ingot", ore.getOreItem());
        }

        for (Tools tool : Tools.values()) {
            register(tool.getName() + "_axe", tool.getAxeItem());
            register(tool.getName() + "_pickaxe", tool.getPickaxeItem());
            register(tool.getName() + "_sword", tool.getSwordItem());
            register(tool.getName() + "_hoe", tool.getHoeItem());
            register(tool.getName() + "_shovel", tool.getShovelItem());
        }

        for (Armours armour : Armours.values()){
            register(armour.getName() + "_head", armour.getHeadArmourItem());
            register(armour.getName() + "_chest", armour.getChestArmourItem());
            register(armour.getName() + "_legs", armour.getLegsArmourItem());
            register(armour.getName() + "_feet", armour.getFeetArmourItem());
        }
    }

    private static <T extends Item> void register(String name, T item) {
        ResourceLocation id = RpgCraft.getId(name);
        item.setRegistryName(id);
        ForgeRegistries.ITEMS.register(item);
    }
}
package com.sdbros.rpgcraft.item;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.block.UnstableMatterBlock;
import com.sdbros.rpgcraft.init.ModBlocks;
import com.sdbros.rpgcraft.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(RpgCraft.MOD_ID)
public class ItemsRC {
    public static  final UnstableMatterItem UNSTABLE_MATTER = register("unstable_matter_item", new UnstableMatterItem((new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
    public static  final Item RED_GUNPOWDER = register("red_gunpowder", new Item((new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
    public static  final Item BLUE_GUNPOWDER = register("blue_gunpowder", new Item((new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
    public static  final Item PURPLE_GOO = register("purple_goo", new Item((new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
    public static  final Item ACTIVATED_RUNITE_INGOT = register("activated_runite_ingot", new Item((new Item.Properties()).group(RpgCraft.ITEM_GROUP)));


    private static <T extends Item> T register(String name, T item) {
        ModItems.ITEMS_TO_REGISTER.put(name, item);
        return item;
    }
}
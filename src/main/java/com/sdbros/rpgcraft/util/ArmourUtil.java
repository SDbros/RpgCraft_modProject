package com.sdbros.rpgcraft.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

public class ArmourUtil
{
    public static boolean isFullSetEquipped(PlayerEntity player, Item helmet, Item chestplate, Item leggings, Item boots)
    {
        if(player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == helmet
                && player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == chestplate
                && player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == leggings
                && player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == boots) return true;

        else return false;
    }
}

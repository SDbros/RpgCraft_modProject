package com.sdbros.rpgcraft.item.armours;

import com.sdbros.rpgcraft.util.ArmourUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class RuniteArmour extends ArmorItem {

    public static Item runite_helmet;
    public static Item runite_chestplate;
    public static Item runite_leggings;
    public static Item runite_boots;


    public RuniteArmour(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (player.world.getGameTime() % 100 == 0) {
            if (ArmourUtil.isFullSetEquipped(player, runite_helmet, runite_chestplate, runite_leggings, runite_boots)) {
                player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 100, 0, true, false));
            }
        }
    }
}




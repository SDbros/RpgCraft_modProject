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

import java.util.Random;

public class SummonerArmour extends ArmorItem {

    public static Item summoner_helmet;
    public static Item summoner_chestplate;
    public static Item summoner_leggings;
    public static Item summoner_boots;

    Random random = new Random();

    public SummonerArmour(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (player.world.getGameTime() % 100 == 0 && !world.isRemote) {

            if (ArmourUtil.isFullSetEquipped(player, summoner_helmet, summoner_chestplate, summoner_leggings, summoner_boots) && stack.getItem() == summoner_chestplate) {

                switch (random.nextInt(7)) {
                    case 0:
                        player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 300, 1, true, true, true));
                        break;
                    case 1:
                        player.addPotionEffect(new EffectInstance(Effects.SPEED, 300, 2, true, true, true));
                        break;
                    case 2:
                        player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 300, 2, true, true, true));
                        break;
                    case 3:
                        player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 300, 2, true, true, true));
                        break;
                    case 4:
                        player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 300, 1, true, true, true));
                        break;
                    case 5:
                        player.addPotionEffect(new EffectInstance(Effects.HASTE, 300, 2, true, true, true));
                        break;
                    case 6:
                        player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 300, 1, true, true, true));
                        break;
                }
            }
        }
    }
}




package com.sdbros.rpgcraft.item;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.block.Ores;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.Locale;

public enum Armours implements IArmorMaterial {


    RUNITE(8, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0F, Ores.RUNITE.getOreItem(), Effects.ABSORPTION);

    private final LazyLoadBase<ArmorItem> headArmourItem;
    private final LazyLoadBase<ArmorItem> chestArmourItem;
    private final LazyLoadBase<ArmorItem> legsArmourItem;
    private final LazyLoadBase<ArmorItem> feetArmourItem;

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final Item repairMaterial;
    private final Effect armourEffect;

    Armours(int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughness, Item repairMaterial, Effect armourEffect) {
        this.maxDamageFactor = maxDamageFactorIn;
        this.damageReductionAmountArray = damageReductionAmountsIn;
        this.enchantability = enchantabilityIn;
        this.soundEvent = equipSoundIn;
        this.toughness = toughness;
        this.repairMaterial = repairMaterial;
        this.armourEffect = armourEffect;

        headArmourItem = new LazyLoadBase<>(() -> new ArmorItem(this, EquipmentSlotType.HEAD, (new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
        chestArmourItem = new LazyLoadBase<>(() -> new ArmorItem(this, EquipmentSlotType.CHEST, (new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
        legsArmourItem = new LazyLoadBase<>(() -> new ArmorItem(this, EquipmentSlotType.LEGS, (new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
        feetArmourItem = new LazyLoadBase<>(() -> new ArmorItem(this, EquipmentSlotType.FEET, (new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
    }

    public ArmorItem getHeadArmourItem() {
        return this.headArmourItem.getValue();
    }

    public ArmorItem getChestArmourItem() {
        return this.chestArmourItem.getValue();
    }

    public ArmorItem getLegsArmourItem() {
        return this.legsArmourItem.getValue();
    }

    public ArmorItem getFeetArmourItem() {
        return this.feetArmourItem.getValue();
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(this.repairMaterial);
    }

    public EffectInstance getSetBonus(int set) {
        if (set > 3) {
            return new EffectInstance(armourEffect, 100, 0, true, false);
        }
        return new EffectInstance(armourEffect, 0, 0, false, true);
    }

    public String getName() {
        // Locale.ROOT will ensure consistent behavior (prevent crashes) on all locales
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }
}



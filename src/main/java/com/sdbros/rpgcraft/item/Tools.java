package com.sdbros.rpgcraft.item;


import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.block.Ores;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;

import java.util.Locale;


public enum Tools implements IItemTier {

    COPPER(2, 8.0F, 175, 2, 21, Ores.COPPER.getOreItem());

    private final LazyLoadBase<AxeItem> axeItem;
    private final LazyLoadBase<SwordItem> swordItem;
    private final LazyLoadBase<PickaxeItem> pickaxeItem;
    private final LazyLoadBase<HoeItem> hoeItem;
    private final LazyLoadBase<ShovelItem> shovelItem;

    private float attackDamage, efficiency;
    private int durability, harvestLevel, enchantability;
    private Item repairMaterial;

    Tools(float attackDamage, float efficiency, int durability, int harvestLevel, int enchantability, Item repairMaterial) {
        this.attackDamage = attackDamage;
        this.efficiency = efficiency;
        this.durability = durability;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;

        //IItemTier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builder
        axeItem = new LazyLoadBase<>(() -> new AxeItem(this, 6.0F, -3.1F, (new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
        swordItem = new LazyLoadBase<>(() -> new SwordItem(this, 3, -2.4F, (new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
        pickaxeItem = new LazyLoadBase<>(() -> new PickaxeItem(this, 1, -2.8F, (new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
        hoeItem = new LazyLoadBase<>(() -> new HoeItem(this, -2.8F, (new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
        shovelItem = new LazyLoadBase<>(() -> new ShovelItem(this, 1, -2.8F, (new Item.Properties()).group(RpgCraft.ITEM_GROUP)));
    }

    public AxeItem getAxeItem() {
        return this.axeItem.getValue();
    }

    public SwordItem getSwordItem() {
        return this.swordItem.getValue();
    }

    public PickaxeItem getPickaxeItem() {
        return this.pickaxeItem.getValue();
    }

    public HoeItem getHoeItem() {
        return this.hoeItem.getValue();
    }

    public ShovelItem getShovelItem() {
        return this.shovelItem.getValue();
    }

    public String getName() {
        // Locale.ROOT will ensure consistent behavior (prevent crashes) on all locales
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public int getMaxUses() {
        return this.durability;
    }

    @Override
    public float getEfficiency() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(this.repairMaterial);
    }
}

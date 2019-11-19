package com.sdbros.rpgcraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.UUID;

public final class ModifierHandler {
    private static final UUID MODIFIER_ID_HEALTH = UUID.fromString("c0bef565-35f6-4dc5-bb4c-3644c382e6ce");
    private static final UUID MODIFIER_ID_DAMAGE = UUID.fromString("d3560b15-c459-451c-86a8-0247015ae899");
    private static final String MODIFIER_NAME_HEALTH = "RpgCraft.HealthModifier";
    private static final String MODIFIER_NAME_DAMAGE = "RpgCraft.DamageModifier";

    private ModifierHandler() {throw new IllegalAccessError("Utility class");}

    public static void setModifier(LivingEntity entity, IAttribute attribute, UUID uuid, String name, double amount, AttributeModifier.Operation op) {
        IAttributeInstance instance = entity.getAttribute(attribute);
        //noinspection ConstantConditions -- instance CAN be null!
        if (instance == null) return;
        AttributeModifier mod = instance.getModifier(uuid);
        if (mod != null) instance.removeModifier(mod);
        instance.applyModifier(new AttributeModifier(uuid, name, amount, op));
    }

    public static void addMaxHealth(LivingEntity entity, double amount, AttributeModifier.Operation op) {
        double oldMax = entity.getMaxHealth();
        setModifier(entity, SharedMonsterAttributes.MAX_HEALTH, MODIFIER_ID_HEALTH, MODIFIER_NAME_HEALTH, amount, op);
        double newMax = entity.getMaxHealth();
        // Heal entity when increasing max health
        if (newMax > oldMax) {
            float healAmount = (float) (newMax - oldMax);
            entity.heal(healAmount);
        } else if (entity.getHealth() > newMax) {
            entity.setHealth((float) newMax);
        }
    }

    public static void addAttackDamage(LivingEntity entity, double amount, AttributeModifier.Operation op) {
        setModifier(entity, SharedMonsterAttributes.ATTACK_DAMAGE, MODIFIER_ID_DAMAGE, MODIFIER_NAME_DAMAGE, amount, op);
    }
}

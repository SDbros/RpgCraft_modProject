package com.sdbros.rpgcraft.util;

import com.sdbros.rpgcraft.capability.MobCapability;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.IMob;


import java.util.Random;

public final class MobLevelHandler {

    public static void process(MobEntity entity, MobCapability.MobCapabilityData data) {
        // Already dead?
        if (!entity.isAlive()) return;

        int level = data.getLevel();
        setEntityProperties(entity, level);
    }

    public static void setEntityProperties(MobEntity entity, int level) {

        IAttributeInstance attributeMaxHealth = entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        IAttributeInstance attributeAttackDamage = entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

        double damageBoost = 0;

        //noinspection ConstantConditions
        if (attributeAttackDamage != null) {
            damageBoost = attributeAttackDamage.getBaseValue() * Math.pow(1.15, level - 1) - attributeAttackDamage.getBaseValue();
        }

        double healthBoost = attributeMaxHealth.getBaseValue() * Math.pow(1.15, level - 1) - attributeMaxHealth.getBaseValue();

        // Apply extra health and damage.
        ModifierHandler.addMaxHealth(entity, healthBoost, AttributeModifier.Operation.ADDITION);
        //RpgCraft.LOGGER.info("HealthBoost is " + healthBoost);
        ModifierHandler.addAttackDamage(entity, damageBoost, AttributeModifier.Operation.ADDITION);
        //RpgCraft.LOGGER.info("DamageBoost is " + damageBoost);
    }
}

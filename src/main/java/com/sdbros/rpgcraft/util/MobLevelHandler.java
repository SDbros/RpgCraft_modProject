package com.sdbros.rpgcraft.util;

import com.sdbros.rpgcraft.capability.IMobData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.math.MathHelper;


import java.util.Random;

public final class MobLevelHandler {

    private static Random random = new Random();

    private MobLevelHandler() {
    }

    public static void process(MobEntity entity, IMobData data) {
        // Already dead?
        if (!entity.isAlive()) return;

        int level = data.getLevel();
        setEntityProperties(entity, level);
    }

    public static void setEntityProperties(MobEntity entity, int level) {

        boolean isHostile = entity instanceof IMob;

        double healthBoost = level;
        double damageBoost = 0;

        IAttributeInstance attributeMaxHealth = entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        double baseMaxHealth = attributeMaxHealth.getBaseValue();
        double healthMultiplier = isHostile
                ? 1.5 //Config.Mob.Health.hostileHealthMultiplier
                : 1.25; //Config.Mob.Health.peacefulHealthMultiplier;

        healthBoost *= healthMultiplier;

        if (level > 1) {
            double diffIncrease = 2 * healthMultiplier * level * random.nextFloat();
            healthBoost += diffIncrease;
        }

        // Increase attack damage.
        if (level > 1) {
            float diffIncrease = level * random.nextFloat();
            damageBoost = diffIncrease * Level.damageBoostScale(entity);
            // Clamp the value so it doesn't go over the maximum config.
            double max = Level.maxDamageBoost(entity);
            if (max > 0f) {
                damageBoost = MathHelper.clamp(damageBoost, 0, max);
            }
        }

        // Apply extra health and damage.
        ModifierHandler.addMaxHealth(entity, healthBoost, AttributeModifier.Operation.ADDITION);
        ModifierHandler.addAttackDamage(entity, damageBoost, AttributeModifier.Operation.ADDITION);
    }
}

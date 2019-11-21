package com.sdbros.rpgcraft.util;

import com.sdbros.rpgcraft.capability.IMobData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


import java.util.Random;

public final class MobLevelHandler {

    private static Random random = new Random();

    private MobLevelHandler() {
    }

    public static void process(MobEntity entity, IMobData data) {
        // Already dead?
        if (!entity.isAlive()) return;

        float level = data.getLevel();
    }

    public static void setEntityProperties(MobEntity entity, IMobData data, float difficulty, boolean makeBlight) {
        if (!entity.isAlive()) return;

        World world = entity.world;
        boolean isHostile = entity instanceof IMob;

        final float totalDifficulty = difficulty;

        double healthBoost = difficulty;
        double damageBoost = 0;

        IAttributeInstance attributeMaxHealth = entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        double baseMaxHealth = attributeMaxHealth.getBaseValue();
        double healthMultiplier = isHostile
                ? 0.5 //Config.Mob.Health.hostileHealthMultiplier
                : 0.25; //Config.Mob.Health.peacefulHealthMultiplier;

        healthBoost *= healthMultiplier;

        if (difficulty > 0) {
            double diffIncrease = 2 * healthMultiplier * difficulty * random.nextFloat();
            healthBoost += diffIncrease;
        }

        // Increase attack damage.
        if (difficulty > 0) {
            float diffIncrease = difficulty * random.nextFloat();
            damageBoost = diffIncrease * Level.damageBoostScale(entity);
            // Clamp the value so it doesn't go over the maximum config.
            double max = Level.maxDamageBoost(entity);
            if (max > 0f) {
                damageBoost = MathHelper.clamp(damageBoost, 0, max);
            }
        }

        // Random potion effect
        //Config.get(entity).mobs.randomPotions.tryApply(entity, totalDifficulty);

        // Apply extra health and damage.
        ModifierHandler.addMaxHealth(entity, 10, AttributeModifier.Operation.ADDITION);


//        MobHealthMode mode = EntityGroup.from(entity).getHealthMode(entity);
//        double healthModAmount = mode.getModifierValue(healthBoost, baseMaxHealth);
//        ModifierHandler.addMaxHealth(entity, healthModAmount, mode.getOperator());
//        ModifierHandler.addAttackDamage(entity, damageBoost, AttributeModifier.Operation.ADDITION);
    }
}

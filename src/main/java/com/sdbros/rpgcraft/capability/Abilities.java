
package com.sdbros.rpgcraft.capability;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;
import java.util.Random;

public class Abilities {
    static Random rand;

    public static IForgeRegistry<EntityAbilityData> ENTITY_ABILITY_REGISTRY;

    public static final EntityAbilityData ABSORPTION = new EntityAbilityData("absorption", Effects.ABSORPTION);
    public static final EntityAbilityData JUMP_BOOST = new EntityAbilityData("jump_boost", Effects.JUMP_BOOST);
    public static final EntityAbilityData INVISIBILITY = new EntityAbilityData("invisibility", Effects.INVISIBILITY);
    public static final EntityAbilityData SPEED = new EntityAbilityData("speed", Effects.SPEED);

    public static final EntityAbilityData SLOWAOE = new EntityAbilityData("slow_aoe", null) {
        private final EntityPredicate ENTITY_PREDICATE = (new EntityPredicate()).setDistance(20.0D);

        //todo fix index out of bound exception
        @Override
        public void runAbility(LivingEntity entity) {
            if (entity.isAlive()) {
                List<LivingEntity> list = entity.world.getTargettableEntitiesWithinAABB(LivingEntity.class, ENTITY_PREDICATE, entity, entity.getBoundingBox().grow(20.0D, 8.0D, 20.0D));

                for (int i = 0; i < 10 && !list.isEmpty(); ++i) {
                    LivingEntity livingentity = list.get(rand.nextInt(list.size()));
                    if (livingentity instanceof PlayerEntity) {
                        if (!((PlayerEntity) livingentity).abilities.disableDamage) {
                            livingentity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10, 2));
                        }
                    }
                    list.remove(livingentity);
                }
            }
        }
    };
}



package com.sdbros.rpgcraft.capability;

import com.google.common.collect.Sets;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;
import java.util.Set;

public class Abilities {

    public static IForgeRegistry<AbilityData> ABILITY_REGISTRY;

    public static final AbilityData ABSORPTION = new AbilityData("absorption", Effects.ABSORPTION);
    public static final AbilityData NIGHT_VISION = new AbilityData("night_vision", Effects.NIGHT_VISION);
    public static final AbilityData JUMP_BOOST = new AbilityData("jump_boost", Effects.JUMP_BOOST);
    public static final AbilityData INVISIBILITY = new AbilityData("invisibility", Effects.INVISIBILITY);
    public static final AbilityData SPEED_BOOST = new AbilityData("speed", Effects.SPEED);

    public static final AbilityData SLOW_AOE = new AbilityData("slow_aoe", null) {
        private final EntityPredicate ENTITY_PREDICATE = (new EntityPredicate()).setDistance(15.0D);

        //todo Create separate method from runMob else potion effect are skipped
        @Override
        public void runMob(LivingEntity entity) {
            if (entity.isAlive()) {
                List<LivingEntity> list = entity.world.getTargettableEntitiesWithinAABB(LivingEntity.class, ENTITY_PREDICATE, entity, entity.getBoundingBox().grow(20.0D, 8.0D, 20.0D));

                for (int i = 0; i < list.size() && !list.isEmpty(); ++i) {
                    LivingEntity livingentity = list.get(i);
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

    //Temp fix to get correct names for AbilityCommand
    //name and value need to be the same
    public enum Ability {
        absorption("absorption"),
        night_vision("night_vision"),
        jump_boost("jump_boost"),
        invisibility("invisibility"),
        speed("speed"),
        slow_aoe("slow_aoe");

        public static Set<String> getNames() {
            Set<String> names = Sets.newHashSet();
            for (Abilities.Ability a : values()) {
                names.add(a.name);
            }
            return names;
        }

        String name;

        Ability(String name) {
            this.name = name;
        }
    }
}


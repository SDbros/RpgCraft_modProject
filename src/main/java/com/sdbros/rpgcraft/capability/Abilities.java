
package com.sdbros.rpgcraft.capability;

import com.google.common.collect.Sets;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.LazyLoadBase;

import java.util.Set;

public enum Abilities {
    none("none", null),
    absorption("absorption", Effects.ABSORPTION),
    slowAoe("slowAoe", null);

    private final LazyLoadBase<EntityAbilityData> ability;

    private String name;

    Abilities(String name, Effect potionEffect) {
        this.name = name;
        ability = new LazyLoadBase<>(() -> new EntityAbilityData(name, potionEffect));
    }

    public static Set<String> getNames() {
        Set<String> names = Sets.newHashSet();
        for (Abilities a : values()) {
            names.add(a.name);
        }
        return names;
    }

    public EntityAbilityData getAbility() {
        return ability.getValue();
    }
}


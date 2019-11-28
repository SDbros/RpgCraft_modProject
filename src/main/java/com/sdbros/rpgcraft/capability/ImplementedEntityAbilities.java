
package com.sdbros.rpgcraft.capability;

import net.minecraftforge.registries.IForgeRegistry;


public class ImplementedEntityAbilities {

    public static IForgeRegistry<EntityAbilityData> ENTITY_ABILITY_REGISTRY;

    public static final EntityAbilityData NONE = new EntityAbilityData("rgpcraft:empty", "ability.rpgcraft.empty.name");
    public static final EntityAbilityData ABSORPTION = new EntityAbilityData("minecraft:absorption", "ability.rpgcraft.absorption.name").setPotion(true);


    public static final EntityAbilityData SLOW_AOE = new EntityAbilityData("rpgcraft:slow_aoe", "ability.rpgcraft.slow_aoe.name") {

    };
}

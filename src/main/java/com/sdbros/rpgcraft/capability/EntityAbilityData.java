package com.sdbros.rpgcraft.capability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.List;

import static com.sdbros.rpgcraft.capability.ImplementedEntityAbilities.ENTITY_ABILITY_REGISTRY;
import static com.sdbros.rpgcraft.capability.MobCapability.INSTANCE;

public class EntityAbilityData extends ForgeRegistryEntry<EntityAbilityData> {

    /**
     * Ability's name
     */
    private String name;

    public EntityAbilityData(String rl, String name) {
        this(new ResourceLocation(rl), name);
    }
    public EntityAbilityData(ResourceLocation rl, String name) {
        this.setRegistryName(rl);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public EntityAbilityData getData() {
        return this;
    }

    private boolean isPotion = false;

    public boolean isPotion() {
        return isPotion;
    }

    public EntityAbilityData setPotion(boolean potion) {
        isPotion = potion;
        return this;
    }

    /**
     * Applies a potion effect to the entity
     */
    public void applyPotionToEntity(LivingEntity entity, Effect potionEffect) {
        if (entity.world.getGameTime() % 100 == 0) {
            entity.addPotionEffect(new EffectInstance(potionEffect, 100, 0, true, false));
        }
    }

    public void onTick(LivingEntity entity) {
        //No need for abstraction
    }

    /**
     * Triggers when the player kills an entity {@link LivingDeathEvent}
     */
    public void onLivingKillEntity(LivingDeathEvent event) {
        //No need for abstraction
    }

    /**
     * Triggers just before the wearer is being hurt {@link LivingDamageEvent}
     */
    public void onLivingDamaged(LivingDamageEvent event) {
        //No need for abstraction
    }


    /**
     * @param resourceLocation the registry name of the ability
     * @return AbilityData corresponding to the {@param resourceLocation} from {@link EntityAbilityData#getRegistryName()}
     */
    public static EntityAbilityData getData(ResourceLocation resourceLocation) {
        return ENTITY_ABILITY_REGISTRY.getValue(resourceLocation);
    }

    /**
     * @param data the ability data that we want to get the resource location from
     * @return the resource location for the given {@param data} ability
     */
    public static ResourceLocation getResourceLocation(EntityAbilityData data) {
        return ENTITY_ABILITY_REGISTRY.getKey(data);
    }

    /**
     * @param handler the ability handler
     * @return true if the abilityList isn't empty, otherwise false
     */
    public static boolean hasAbilities(MobCapability.IMobCapabilityHandler handler) {
        return !handler.getAbilities().isEmpty();
    }

    public static boolean contains(LivingEntity entity, ResourceLocation rl) {
        if (!entity.isAlive()) return false;
        return entity.getCapability(INSTANCE).map(handler -> {
                    List<EntityAbilityData> list = handler.getAbilities();
                    return list.contains(getData(rl));
                }
        ).orElse(false);
    }

    public static boolean contains(LivingEntity entity, EntityAbilityData data) {
        return contains(entity, data.getRegistryName());
    }

    public static void provideEntityAbilities(LivingEntity entity) {
        if (!entity.isAlive()) return;
        entity.getCapability(INSTANCE).filter(EntityAbilityData::hasAbilities).ifPresent(handler -> {
                    for (EntityAbilityData data : handler.getAbilities()) {
                        if (data.isPotion()) {
                            data.applyPotionToEntity(entity, null);
                        }
                        //todo run abilities here
                    }
                }
        );
    }
}

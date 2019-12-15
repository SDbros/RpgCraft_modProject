package com.sdbros.rpgcraft.capability;

import com.sdbros.rpgcraft.RpgCraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.registries.ForgeRegistryEntry;

import static com.sdbros.rpgcraft.capability.MobCapability.MOB_INSTANCE;
import static com.sdbros.rpgcraft.capability.PlayerCapability.PLAYER_INSTANCE;

public class AbilityData extends ForgeRegistryEntry<AbilityData> {

    /**
     * Ability's name
     */
    private String name;
    private Effect potionEffect;
    private boolean isPotion;

    public AbilityData(String name, Effect potionEffect) {
        this.name = name;
        this.setRegistryName(RpgCraft.getId(name));
        setPotionEffect(potionEffect);
    }

    public String getName() {
        return this.name;
    }

    public AbilityData getData() {
        return this;
    }

    public Effect getPotionEffect() {
        return potionEffect;
    }

    public boolean isPotion() {
        return isPotion;
    }

    public void setPotionEffect(Effect potionEffect) {
        if (potionEffect != null) {
            this.potionEffect = potionEffect;
            isPotion = true;
        } else isPotion = false;
    }

    /**
     * Applies a potion effect to the entity
     */
    public void applyPotionToEntity(LivingEntity entity, Effect potionEffect) {
        entity.addPotionEffect(new EffectInstance(potionEffect, 200, 0, false, true));
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
     * @param handler the ability handler
     * @return true if the abilityList isn't empty, otherwise false
     */
    public static boolean mobAbilities(MobCapability.IMobCapabilityHandler handler) {
        return !handler.getAbilities().isEmpty();
    }

    /**
     * @param handler the ability handler
     * @return true if the abilityList isn't empty, otherwise false
     */
    public static boolean playerAbilities(PlayerCapability.IPlayerCapabilityHandler handler) {
        return !handler.getAbilities().isEmpty();
    }

    /**
     * Applies a potion effect to the entity by default
     * Overwrite if you want something else to happen
     *
     * @param entity the entity to run the ability on
     */
    public void runMob(LivingEntity entity) {
        if (!entity.isAlive()) return;
        entity.getCapability(MOB_INSTANCE).filter(AbilityData::mobAbilities).ifPresent(handler -> {
                    for (AbilityData data : handler.getAbilities()) {
                        if (data.isPotion() && entity.world.getGameTime() % 100 == 0) {
                            data.applyPotionToEntity(entity, potionEffect);
                        }
                    }
                }
        );
    }

    public void runPlayer(PlayerEntity player) {
        if (!player.isAlive()) return;
        player.getCapability(PLAYER_INSTANCE).filter(AbilityData::playerAbilities).ifPresent(handler -> {
                    for (AbilityData data : handler.getAbilities()) {
                        if (data.isPotion() && player.world.getGameTime() % 100 == 0) {
                            data.applyPotionToEntity(player, potionEffect);
                        }
                    }
                }
        );
    }
}

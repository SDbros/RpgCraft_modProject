package com.sdbros.rpgcraft.capability;

import com.sdbros.rpgcraft.util.Level;
import net.minecraft.entity.MobEntity;

public interface IMobData {
    int getLevel();

    default int getLevelModifier(MobEntity mob) {
        return getLevel() + Level.startingLevel(mob);
    }

    void setLevel(int value);

    default int getDisplayLevel() {
        return (int) (getLevel() / 2);
    }

    void setProcessed(boolean value);

    void tick(MobEntity entity);

    /**
     * Add (or subtract if amount is negative) this amount to current difficulty.
     *
     * @param amount Amount to add. May be negative.
     */
    default void addLevel(int amount) {
        setLevel(getLevel() + amount);
    }
}

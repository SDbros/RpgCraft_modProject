package com.sdbros.rpgcraft.capability;

import net.minecraft.entity.MobEntity;

public interface IMobData {
    int getLevel();

    void setLevel(int value);

    default int getDisplayLevel() {
        return (int) (getLevel() / 3);
    }

    void setProcessed(boolean value);

    void tick(MobEntity entity);

    /**
     * Add (or subtract if amount is negative) this amount to current difficulty.
     *
     * @param amount Amount to add. May be negative.
     */
    default void addDifficulty(int amount) {
        setLevel(getLevel() + amount);
    }
}

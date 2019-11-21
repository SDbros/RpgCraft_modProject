package com.sdbros.rpgcraft.capability;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.util.ModifierHandler;
import com.sdbros.rpgcraft.util.Players;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerDataCapability implements IPlayerData, ICapabilitySerializable<CompoundNBT> {
    @CapabilityInject(IPlayerData.class)
    public static Capability<IPlayerData> INSTANCE = null;
    public static ResourceLocation NAME = RpgCraft.getId("player_data");

    private static final String NBT_HEARTS = "Hearts";

    private final LazyOptional<IPlayerData> holder = LazyOptional.of(() -> this);

    private int extraHearts;

    @Override
    public int getExtraHearts() {
        return extraHearts;
    }

    @Override
    public void setExtraHearts(PlayerEntity player, int amount) {
        extraHearts = Players.clampExtraHearts(player, amount);
        ModifierHandler.addMaxHealth(player, getHealthModifier(player), AttributeModifier.Operation.ADDITION);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == null)
            return LazyOptional.empty();
        return INSTANCE.orEmpty(cap, holder);
    }

    @Override
    public void updateStats(PlayerEntity player) {
        ModifierHandler.addMaxHealth(player, getHealthModifier(player), AttributeModifier.Operation.ADDITION);
    }

    public static boolean canAttachTo(ICapabilityProvider entity) {
        if (!(entity instanceof PlayerEntity)) {
            return false;
        }
        try {
            if (entity.getCapability(INSTANCE).isPresent()) {
                return false;
            }
        } catch (NullPointerException ex) {
            // Forge seems to be screwing up somewhere?
            RpgCraft.LOGGER.error("Failed to get capabilities from {}", entity);
            return false;
        }
        return true;
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IPlayerData.class, new Storage(), PlayerDataCapability::new);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(NBT_HEARTS, extraHearts);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        extraHearts = nbt.getInt(NBT_HEARTS);
    }

    private static class Storage implements Capability.IStorage<IPlayerData> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IPlayerData> capability, IPlayerData instance, Direction side) {
            if (instance instanceof PlayerDataCapability) {
                return ((PlayerDataCapability) instance).serializeNBT();
            }
            return new CompoundNBT();
        }

        @Override
        public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, Direction side, INBT nbt) {
            if (instance instanceof PlayerDataCapability) {
                ((PlayerDataCapability) instance).deserializeNBT((CompoundNBT) nbt);
            }
        }
    }

    @Override
    public String toString() {
        return String.valueOf(INSTANCE);
    }
}

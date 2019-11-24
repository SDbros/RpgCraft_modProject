package com.sdbros.rpgcraft.capability;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.event.LevelEvents;
import com.sdbros.rpgcraft.util.Level;
import com.sdbros.rpgcraft.util.MobLevelHandler;
import net.minecraft.entity.MobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MobDataCapability implements IMobData, ICapabilitySerializable<CompoundNBT> {
    @CapabilityInject(IMobData.class)
    public static Capability<IMobData> INSTANCE = null;
    public static ResourceLocation NAME = RpgCraft.getId("mob_data");

    private final LazyOptional<IMobData> holder = LazyOptional.of(() -> this);

    private static final String NBT_LEVEL = "Level";

    private int level;
    private boolean processed;

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int value) {
        level = value;
    }

    @Override
    public void setProcessed(boolean value) {
        this.processed = value;
    }

    @Override
    public void tick(MobEntity entity) {
        if (!processed && entity.isAlive() && entity.ticksExisted > 2) {
            level = (int) Level.areaLevel(entity.world, entity.getPosition());
            MobLevelHandler.process(entity, this);
            processed = true;
            //RpgCraft.LOGGER.debug(LevelEvents.MARKER, "Processed {} -> level={}", entity, level);
        }
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return INSTANCE.orEmpty(cap, holder);
    }

    public static boolean canAttachTo(ICapabilityProvider entity) {
        return entity instanceof MobEntity
                && !entity.getCapability(INSTANCE).isPresent()
                && Level.allowsDifficultyChanges((MobEntity) entity);
    }


    public static void register() {
        CapabilityManager.INSTANCE.register(IMobData.class, new Storage(), MobDataCapability::new);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(NBT_LEVEL, level);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        level = nbt.getInt(NBT_LEVEL);
    }

    private static class Storage implements Capability.IStorage<IMobData> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IMobData> capability, IMobData instance, Direction side) {
            if (instance instanceof MobDataCapability) {
                return ((MobDataCapability) instance).serializeNBT();
            }
            return new CompoundNBT();
        }

        @Override
        public void readNBT(Capability<IMobData> capability, IMobData instance, Direction side, INBT nbt) {
            if (instance instanceof MobDataCapability) {
                ((MobDataCapability) instance).deserializeNBT((CompoundNBT) nbt);
            }
        }
    }


    @Override
    public String toString() {
        return String.valueOf(INSTANCE);
    }
}

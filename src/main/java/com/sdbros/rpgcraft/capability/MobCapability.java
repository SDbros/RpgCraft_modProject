package com.sdbros.rpgcraft.capability;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.util.Level;
import com.sdbros.rpgcraft.util.MobLevelHandler;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MobCapability {

    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IMobCapabilityHandler.class)
    public static Capability<IMobCapabilityHandler> INSTANCE;
    public static ResourceLocation NAME = RpgCraft.getId("mob_data");


    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IMobCapabilityHandler.class, new Storage(), new Factory());
        MinecraftForge.EVENT_BUS.register(new MobCapability());
    }

    // Simple wrapper to get the handler from an entity.
    public static IMobCapabilityHandler getHandler(ItemStack stack) {
        return stack.getCapability(INSTANCE).map(handler -> handler).orElseThrow(() -> new RuntimeException("No Capability"));
    }

    public interface IMobCapabilityHandler {

        /**
         * Returns Level currently stored.
         */
        int getLevel();

        /**
         * Returns the list of abilities currently stored.
         */
        List<AbilityData> getAbilities();

        IMobCapabilityHandler setAbilities(List<AbilityData> ability);

        /**
         * Adds an ability from the list.
         *
         * @param ability the ability that is going to be added.
         */
        IMobCapabilityHandler addAbility(AbilityData ability);

        /**
         * Removes an ability from the list.
         *
         * @param ability the ability that is going to be removed.
         */
        IMobCapabilityHandler removeAbility(AbilityData ability);

        void tick(MobEntity entity);
    }


    // The default implementation of the capability. Holds all the logic.
    public static class MobCapabilityData extends ForgeRegistryEntry<MobCapabilityData> implements IMobCapabilityHandler, ICapabilitySerializable<CompoundNBT> {
        public MobCapabilityData() {
        }

        private int level;
        private boolean processed;
        private List<AbilityData> abilityList = new ArrayList<>();
        private static final String NBT_LEVEL = "Level";

        LazyOptional<IMobCapabilityHandler> holder = LazyOptional.of(MobCapabilityData::new);


        @Override
        public int getLevel() {
            return level;
        }

        @Override
        public List<AbilityData> getAbilities() {
            return this.abilityList;
        }

        @Override
        public IMobCapabilityHandler setAbilities(List<AbilityData> abilities) {
            this.abilityList.clear();
            this.abilityList.addAll(abilities);
            return this;
        }

        @Override
        public IMobCapabilityHandler addAbility(AbilityData ability) {
            if (!abilityList.contains(ability)) {
                this.abilityList.add(ability);
            }
            return this;
        }

        @Override
        public IMobCapabilityHandler removeAbility(AbilityData ability) {
            if (!this.abilityList.isEmpty()) {
                this.abilityList.remove(ability);
            }
            return this;
        }

        @Override
        public void tick(MobEntity entity) {
            if (!processed && entity.isAlive() && entity.ticksExisted > 2) {
                level = (int) Level.areaLevel(entity.world, entity.getPosition());
                MobLevelHandler.process(entity, this);
                processed = true;
                //RpgCraft.LOGGER.debug(LevelEventHandler.MARKER, "Processed {} -> level={}", entity, level);
            }
            for (AbilityData ability : abilityList) {
                ability.runAbility(entity);
            }
        }

        public static boolean canAttachTo(ICapabilityProvider entity) {
            return entity instanceof MobEntity
                    && !entity.getCapability(INSTANCE).isPresent()
                    && Level.allowsDifficultyChanges((MobEntity) entity);
        }

        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return INSTANCE.orEmpty(cap, holder);
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

        @Override
        public String toString() {
            return String.valueOf(INSTANCE);
        }
    }

    public static class Storage implements Capability.IStorage<IMobCapabilityHandler> {

        @Override
        public INBT writeNBT(Capability<IMobCapabilityHandler> capability, IMobCapabilityHandler instance, Direction side) {

            if (instance instanceof MobCapabilityData) {
                return ((MobCapabilityData) instance).serializeNBT();
            }
            return new CompoundNBT();
        }

        @Override
        public void readNBT(Capability<IMobCapabilityHandler> capability, IMobCapabilityHandler instance, Direction side, INBT nbt) {
            if (instance instanceof MobCapabilityData) {
                ((MobCapabilityData) instance).deserializeNBT((CompoundNBT) nbt);
            }
        }
    }

    public static class Factory implements Callable<IMobCapabilityHandler> {

        @Override
        public IMobCapabilityHandler call() {
            return new MobCapabilityData();
        }
    }
}

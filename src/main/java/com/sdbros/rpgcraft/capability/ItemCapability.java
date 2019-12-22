package com.sdbros.rpgcraft.capability;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.util.Level;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
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

public class ItemCapability {

    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IItemCapabilityHandler.class)
    public static Capability<IItemCapabilityHandler> ITEM_INSTANCE;
    public static ResourceLocation NAME = RpgCraft.getId("item_data");


    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IItemCapabilityHandler.class, new Storage(), new Factory());
        MinecraftForge.EVENT_BUS.register(new ItemCapability());
    }

    // Simple wrapper to get the handler from an entity.
    public static IItemCapabilityHandler getHandler(ItemStack stack) {
        return stack.getCapability(ITEM_INSTANCE).map(handler -> handler).orElseThrow(() -> new RuntimeException("No Capability"));
    }

    public interface IItemCapabilityHandler {

        /**
         * Returns Level currently stored.
         */
        int getLevel();

        /**
         * Returns the list of abilities currently stored.
         */
        List<AbilityData> getAbilities();

        IItemCapabilityHandler setAbilities(List<AbilityData> ability);

        /**
         * Adds an ability from the list.
         *
         * @param ability the ability that is going to be added.
         */
        IItemCapabilityHandler addAbility(AbilityData ability);

        /**
         * Removes an ability from the list.
         *
         * @param ability the ability that is going to be removed.
         */
        IItemCapabilityHandler removeAbility(AbilityData ability);

        IItemCapabilityHandler clearAbilities();

    }


    // The default implementation of the capability. Holds all the logic.
    public static class ItemCapabilityData extends ForgeRegistryEntry<ItemCapabilityData> implements IItemCapabilityHandler, ICapabilitySerializable<CompoundNBT> {
        public ItemCapabilityData() {
        }

        private int level;
        private boolean processed;
        private List<AbilityData> abilityList = new ArrayList<>();
        private static final String NBT_LEVEL = "Level";

        LazyOptional<IItemCapabilityHandler> holder = LazyOptional.of(ItemCapabilityData::new);


        @Override
        public int getLevel() {
            return level;
        }

        @Override
        public List<AbilityData> getAbilities() {
            return this.abilityList;
        }

        @Override
        public IItemCapabilityHandler setAbilities(List<AbilityData> abilities) {
            this.abilityList.clear();
            this.abilityList.addAll(abilities);
            return this;
        }

        @Override
        public IItemCapabilityHandler addAbility(AbilityData ability) {
            if (!abilityList.contains(ability)) {
                this.abilityList.add(ability);
            }
            return this;
        }

        @Override
        public IItemCapabilityHandler removeAbility(AbilityData ability) {
            if (!this.abilityList.isEmpty()) {
                this.abilityList.remove(ability);
            }
            return this;
        }

        @Override
        public IItemCapabilityHandler clearAbilities() {
            this.abilityList.clear();
            return this;
        }

        public static boolean canAttachTo(ICapabilityProvider item) {
            return item instanceof ItemStack
                    && !item.getCapability(ITEM_INSTANCE).isPresent();
        }

        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return ITEM_INSTANCE.orEmpty(cap, holder);
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
            return String.valueOf(ITEM_INSTANCE);
        }
    }

    public static class Storage implements Capability.IStorage<IItemCapabilityHandler> {

        @Override
        public INBT writeNBT(Capability<IItemCapabilityHandler> capability, IItemCapabilityHandler instance, Direction side) {

            if (instance instanceof ItemCapabilityData) {
                return ((ItemCapabilityData) instance).serializeNBT();
            }
            return new CompoundNBT();
        }

        @Override
        public void readNBT(Capability<IItemCapabilityHandler> capability, IItemCapabilityHandler instance, Direction side, INBT nbt) {
            if (instance instanceof ItemCapabilityData) {
                ((ItemCapabilityData) instance).deserializeNBT((CompoundNBT) nbt);
            }
        }
    }

    public static class Factory implements Callable<IItemCapabilityHandler> {

        @Override
        public IItemCapabilityHandler call() {
            return new ItemCapabilityData();
        }
    }
}


package com.sdbros.rpgcraft.capabilities;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.util.Level;
import com.sdbros.rpgcraft.util.ModifierHandler;
import com.sdbros.rpgcraft.util.Players;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.registries.ForgeRegistryEntry;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class PlayerCapability {

    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IPlayerCapabilityHandler.class)
    public static Capability<IPlayerCapabilityHandler> PLAYER_INSTANCE;
    public static ResourceLocation NAME = RpgCraft.getId("player_data");


    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IPlayerCapabilityHandler.class, new Storage(), new Factory());
        MinecraftForge.EVENT_BUS.register(new PlayerCapability());
    }

    // Simple wrapper to get the handler from an entity.
    public static IPlayerCapabilityHandler getHandler(ItemStack stack) {
        return stack.getCapability(PLAYER_INSTANCE).map(handler -> handler).orElseThrow(() -> new RuntimeException("No Capability"));
    }

    public interface IPlayerCapabilityHandler {

        /**
         * Returns the list of abilities currently stored.
         */
        List<AbilityData> getAbilities();

        IPlayerCapabilityHandler setAbilities(List<AbilityData> ability);

        /**
         * Adds an ability from the list.
         *
         * @param ability the ability that is going to be added.
         */
        IPlayerCapabilityHandler addAbility(AbilityData ability);

        /**
         * Removes an ability from the list.
         *
         * @param ability the ability that is going to be removed.
         */
        IPlayerCapabilityHandler removeAbility(AbilityData ability);

        IPlayerCapabilityHandler clearAbilities();

        int getExtraHearts();

        void setExtraHearts(PlayerEntity player, int amount);

        default void addHearts(PlayerEntity player, int amount) {
            setExtraHearts(player, getExtraHearts() + amount);
        }

        default int getHealthModifier(PlayerEntity player) {
            return getExtraHearts() + Players.startingHealth(player);
        }

        void updateStats(PlayerEntity player);

        void tick(PlayerEntity player);
    }

    public static class PlayerCapabilityData extends ForgeRegistryEntry<PlayerCapabilityData> implements IPlayerCapabilityHandler, ICapabilitySerializable<CompoundNBT> {
        public PlayerCapabilityData() {
        }

        private int extraHearts;
        private List<AbilityData> abilityList = new ArrayList<>();
        private static final String NBT_HEARTS = "Hearts";

        private final LazyOptional<IPlayerCapabilityHandler> holder = LazyOptional.of(() -> this);


        @Override
        public List<AbilityData> getAbilities() {
            return this.abilityList;
        }

        @Override
        public IPlayerCapabilityHandler setAbilities(List<AbilityData> abilities) {
            this.abilityList.clear();
            this.abilityList.addAll(abilities);
            return this;
        }

        @Override
        public IPlayerCapabilityHandler addAbility(AbilityData ability) {
            if (!abilityList.contains(ability)) {
                this.abilityList.add(ability);
            }
            return this;
        }

        @Override
        public IPlayerCapabilityHandler removeAbility(AbilityData ability) {
            if (!this.abilityList.isEmpty()) {
                this.abilityList.remove(ability);
            }
            return this;
        }

        @Override
        public IPlayerCapabilityHandler clearAbilities() {
            this.abilityList.clear();
            return this;
        }

        @Override
        public int getExtraHearts() {
            return extraHearts;
        }

        @Override
        public void setExtraHearts(PlayerEntity player, int amount) {
            extraHearts = Players.clampExtraHearts(player, amount);
            ModifierHandler.addMaxHealth(player, getHealthModifier(player), AttributeModifier.Operation.ADDITION);
        }

        @Override
        public void updateStats(PlayerEntity player) {
            ModifierHandler.addMaxHealth(player, getHealthModifier(player), AttributeModifier.Operation.ADDITION);
        }

        @Override
        public void tick(PlayerEntity player) {
            for (AbilityData ability : abilityList) {
                ability.runPlayer(player);
            }
        }

        public static boolean canAttachTo(ICapabilityProvider entity) {
            return entity instanceof PlayerEntity
                    && !entity.getCapability(PLAYER_INSTANCE).isPresent();
        }

        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PLAYER_INSTANCE.orEmpty(cap, holder);
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

        @Override
        public String toString() {
            return String.valueOf(PLAYER_INSTANCE);
        }
    }

    private static class Storage implements Capability.IStorage<IPlayerCapabilityHandler> {

        @Override
        public INBT writeNBT(Capability<IPlayerCapabilityHandler> capability, IPlayerCapabilityHandler instance, Direction side) {

            if (instance instanceof PlayerCapabilityData) {
                return ((PlayerCapabilityData) instance).serializeNBT();
            }
            return new CompoundNBT();
        }

        @Override
        public void readNBT(Capability<IPlayerCapabilityHandler> capability, IPlayerCapabilityHandler instance, Direction side, INBT nbt) {
            if (instance instanceof PlayerCapabilityData) {
                ((PlayerCapabilityData) instance).deserializeNBT((CompoundNBT) nbt);
            }
        }
    }

    public static class Factory implements Callable<IPlayerCapabilityHandler> {

        @Override
        public IPlayerCapabilityHandler call() {
            return new PlayerCapabilityData();
        }
    }
}

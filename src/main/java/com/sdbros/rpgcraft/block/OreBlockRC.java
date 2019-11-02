package com.sdbros.rpgcraft.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;
import java.util.List;

public abstract class OreBlockRC extends OreBlock {
    // FIXME: droppedItem is meaningless because of loot tables
    @Nullable
    private final IItemProvider droppedItem;
    private final int harvestLevel;

    /**
     * Constructor. If the ore drops itself, {@code droppedItem} should be null.
     *
     * @param droppedItem  The item dropped when mined without silk touch, or null if the block
     *                     should always drop itself.
     * @param harvestLevel The harvest level required
     * @param builder      The block properties
     */
    public OreBlockRC(@Nullable IItemProvider droppedItem, int harvestLevel, Properties builder) {
        super(builder);
        this.droppedItem = droppedItem;
        this.harvestLevel = harvestLevel;
    }

    /**
     * Gets the item dropped when mined with a non-silk touch tool
     */
    public IItemProvider getDroppedItem() {
        return this.droppedItem != null ? this.droppedItem : this;
    }

    /**
     * Get a random amount of XP to drop, assuming XP will be dropped.
     */
    public abstract int getExpRandom();

    @Override
    public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silkTouch) {
        return silkTouch == 0 ? getExpRandom() : 0;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return this.harvestLevel;
    }

}

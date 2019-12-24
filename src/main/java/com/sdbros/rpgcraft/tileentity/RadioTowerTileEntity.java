package com.sdbros.rpgcraft.tileentity;


import com.sdbros.rpgcraft.init.ModTileEntities;
import net.minecraft.inventory.IClearable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;


public class RadioTowerTileEntity extends TileEntity implements IClearable {

    private ItemStack record = ItemStack.EMPTY;

    public RadioTowerTileEntity() {
        super(ModTileEntities.RADIO_TOWER_TILE);
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("RecordItem", 10)) {
            this.setRecord(ItemStack.read(compound.getCompound("RecordItem")));
        }

    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (!this.getRecord().isEmpty()) {
            compound.put("RecordItem", this.getRecord().write(new CompoundNBT()));
        }

        return compound;
    }

    public ItemStack getRecord() {
        return this.record;
    }

    public void setRecord(ItemStack p_195535_1_) {
        this.record = p_195535_1_;
        this.markDirty();
    }

    public void clear() {
        this.setRecord(ItemStack.EMPTY);
    }
}


package com.sdbros.rpgcraft.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class ZombieVariantEntity extends ZombieEntity {

    public ZombieVariantEntity(EntityType<? extends ZombieEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0d);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25d);
    }

    @Override
    public void tick() {
        if (this.isAlive()) {


        }
        super.tick();
    }


    public static boolean canSpawnAt(EntityType<RedCreeperEntity> type, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL;
    }
}

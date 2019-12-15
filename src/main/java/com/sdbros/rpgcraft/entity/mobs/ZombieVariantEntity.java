package com.sdbros.rpgcraft.entity.mobs;

import com.sdbros.rpgcraft.capability.MobCapability;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static com.sdbros.rpgcraft.capability.Abilities.ABSORPTION;
import static com.sdbros.rpgcraft.capability.Abilities.SLOW_AOE;

public class ZombieVariantEntity extends ZombieEntity {

    public ZombieVariantEntity(EntityType<? extends ZombieEntity> type, World worldIn) {
        super(type, worldIn);

        this.getCapability(MobCapability.MOB_INSTANCE).ifPresent(affected -> {
            Random r = new Random();

            if (!affected.getAbilities().contains(ABSORPTION) && r.nextInt(100) > 90) {
                affected.addAbility(ABSORPTION);
            } else if (!affected.getAbilities().contains(SLOW_AOE) && r.nextInt(100) < 60) {
                affected.addAbility(SLOW_AOE);
            }
        });
    }


    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0d);
    }

    public static boolean canSpawnAt(EntityType<ZombieVariantEntity> type, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL;
    }
}




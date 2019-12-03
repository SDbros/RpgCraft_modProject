package com.sdbros.rpgcraft.entity.mobs;

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

public class ZombieVariantEntity extends ZombieEntity {

    private static final EntityPredicate ENTITY_PREDICATE = (new EntityPredicate()).setDistance(20.0D);


    public ZombieVariantEntity(EntityType<? extends ZombieEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0d);
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            List<LivingEntity> list = this.world.getTargettableEntitiesWithinAABB(LivingEntity.class, ENTITY_PREDICATE, this, this.getBoundingBox().grow(20.0D, 8.0D, 20.0D));

            for (int i = 0; i < 10 && !list.isEmpty(); ++i) {
                LivingEntity livingentity = list.get(this.rand.nextInt(list.size()));
                if (livingentity instanceof PlayerEntity) {
                    if (!((PlayerEntity) livingentity).abilities.disableDamage) {
                        livingentity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10, 2));
                    }
                }
                list.remove(livingentity);
            }
        }
        super.tick();
    }

    public static boolean canSpawnAt(EntityType<ZombieVariantEntity> type, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL;
    }
}




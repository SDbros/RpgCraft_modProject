package com.sdbros.rpgcraft.client.renderer.entity;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.capabilities.MobCapability;
import com.sdbros.rpgcraft.entity.MutantZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.ZombieVillagerModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

import static com.sdbros.rpgcraft.capabilities.Abilities.*;

public class MutantZombieRender extends MobRenderer<MutantZombieEntity, ZombieVillagerModel<MutantZombieEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/mutant_zombie_default.png");
    private static final ResourceLocation SLOW_TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/mutant_zombie_slow.png");
    private static final ResourceLocation ABSORB_TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/mutant_zombie_absorb.png");


    public MutantZombieRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ZombieVillagerModel<>(), 0.25F);

        this.addLayer(new HeldItemLayer(this));

    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(MutantZombieEntity entity) {
        AtomicInteger i = new AtomicInteger(0);
        entity.getCapability(MobCapability.MOB_INSTANCE).ifPresent(affected -> {
            if (affected.getAbilities().contains(SLOW_AOE)) {
                i.set(1);
            } else if (affected.getAbilities().contains(ABSORPTION)) {
                i.set(2);
            } else {
                i.set(0);
            }
        });
        switch (i.intValue()) {
            case 1:
                return SLOW_TEXTURE;
            case 2:
                return ABSORB_TEXTURE;

            default:
                return TEXTURE;
        }
    }

}

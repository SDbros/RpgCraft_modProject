package com.sdbros.rpgcraft.entity.render;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entity.mobs.LumberjackEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class LumberjackRender extends MobRenderer<LumberjackEntity, ZombieModel<LumberjackEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/lumberjack.png");

    public LumberjackRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ZombieModel<>(), 0.25F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(LumberjackEntity entity) {
        return TEXTURE;
    }

}


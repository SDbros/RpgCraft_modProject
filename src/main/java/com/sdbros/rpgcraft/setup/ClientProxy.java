package com.sdbros.rpgcraft.setup;

import com.sdbros.rpgcraft.blocks.BackPackScreen;
import com.sdbros.rpgcraft.blocks.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {


    @Override
    public void init() {
        ScreenManager.registerFactory(ModBlocks.BACKPACK_CONTAINER, BackPackScreen::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}

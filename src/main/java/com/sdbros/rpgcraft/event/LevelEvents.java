package com.sdbros.rpgcraft.event;

import com.sdbros.rpgcraft.RpgCraft;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod.EventBusSubscriber(modid = RpgCraft.MOD_ID)
public class LevelEvents {

    public static final Marker MARKER = MarkerManager.getMarker("Level");

}

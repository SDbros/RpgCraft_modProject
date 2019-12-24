package com.sdbros.rpgcraft.event;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.capability.AbilityData;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import static com.sdbros.rpgcraft.RpgCraft.MOD_ID;
import static com.sdbros.rpgcraft.capability.Abilities.*;
import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@Mod.EventBusSubscriber(bus = MOD, modid = MOD_ID)
public class AbilityEventHandler {

    @SubscribeEvent
    public static void registerCapability(RegistryEvent.Register.NewRegistry event) {
        if (ABILITY_REGISTRY == null) {
            ResourceLocation registryName = RpgCraft.getId("abilities");
            ABILITY_REGISTRY = new RegistryBuilder<AbilityData>().setType(AbilityData.class).setName(registryName).create();
        }
    }

    @SubscribeEvent
    public static void registerAbilities(RegistryEvent.Register<AbilityData> event) {
        registerAbility(event,
                ABSORPTION, SLOW_AOE, JUMP_BOOST, SPEED_BOOST, INVISIBILITY, NIGHT_VISION
        );
    }

    private static void registerAbility(RegistryEvent.Register<AbilityData> event, AbilityData... dataList) {
        for (AbilityData data : dataList) {
            IForgeRegistry<AbilityData> registry = event.getRegistry();
            if (!registry.containsValue(data)) {
                event.getRegistry().register(data);
            }
        }
    }
}

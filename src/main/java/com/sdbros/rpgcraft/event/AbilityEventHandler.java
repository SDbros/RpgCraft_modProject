package com.sdbros.rpgcraft.event;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.capability.EntityAbilityData;
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
        if (ENTITY_ABILITY_REGISTRY == null) {
            ResourceLocation registryName = RpgCraft.getId("armor_abilities");
            ENTITY_ABILITY_REGISTRY = new RegistryBuilder<EntityAbilityData>().setType(EntityAbilityData.class).setName(registryName).create();
        }
    }

    @SubscribeEvent
    public static void registerAbilities(RegistryEvent.Register<EntityAbilityData> event) {
        registerAbility(event,
                ABSORPTION, SLOWAOE
        );
    }

    private static void registerAbility(RegistryEvent.Register<EntityAbilityData> event, EntityAbilityData... dataList) {
        for (EntityAbilityData data : dataList) {
            IForgeRegistry<EntityAbilityData> registry = event.getRegistry();
            if (!registry.containsValue(data)) {
                event.getRegistry().register(data);
            }
        }
    }
}

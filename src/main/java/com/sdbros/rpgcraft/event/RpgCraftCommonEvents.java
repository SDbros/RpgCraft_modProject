package com.sdbros.rpgcraft.event;


import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.network.ClientLoginMessage;
import com.sdbros.rpgcraft.network.Network;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

@Mod.EventBusSubscriber(modid = RpgCraft.MOD_ID)
public final class RpgCraftCommonEvents {
    private RpgCraftCommonEvents() {
    }
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayerEntity)) return;
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        World world = player.world;
        RpgCraft.LOGGER.info("Sending login packet to player {}", player);
        ClientLoginMessage msg = new ClientLoginMessage(16F);
        Network.channel.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }




//    // Sets the PlayerHealth to 10 when they respawn
//    @SubscribeEvent
//    public static void changePlayerHealthOnSpawn(EntityJoinWorldEvent event) {
//
//        if (event.getEntity() instanceof PlayerEntity) {
//            PlayerEntity player = (PlayerEntity) event.getEntity();
//            if (player.getHealth() > 10) {
//                System.out.println("PlayerHealth set to " + player.getHealth());
//                player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("MAX_HEALTH_UUID", -0.5, AttributeModifier.Operation.byId(1)));
//                player.setHealth(10);
//                System.out.println("PlayerHealth set to " + player.getHealth());
//            }
//        }
//    }
}

package net.madmike.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.madmike.networking.ServerNetworking;
import net.madmike.gui.TradeTab;
import net.minecraft.network.PacketByteBuf;

public class TabChangeC2SPacket {
    public static void send(TradeTab tab) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(tab.ordinal());

        ClientPlayNetworking.send(ServerNetworking.TAB_CHANGE_PACKET, buf);
    }
}

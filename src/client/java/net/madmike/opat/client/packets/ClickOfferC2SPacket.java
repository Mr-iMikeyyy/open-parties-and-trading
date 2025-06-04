package net.madmike.opat.client.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.madmike.opat.server.networking.ServerNetworking;
import net.madmike.opat.server.gui.TradeTab;
import net.minecraft.network.PacketByteBuf;

public class ClickOfferC2SPacket {
    public static void send(TradeTab tab, int index) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(tab.ordinal());
        buf.writeInt(index);

        ClientPlayNetworking.send(ServerNetworking.CLICK_OFFER_PACKET, buf);
    }
}

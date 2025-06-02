package net.madmike.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.madmike.networking.TradingNetworking;
import net.madmike.gui.TradeTab;
import net.minecraft.network.PacketByteBuf;

public class ClickOfferC2SPacket {
    public static void send(TradeTab tab, int index) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(tab.ordinal());
        buf.writeInt(index);

        ClientPlayNetworking.send(TradingNetworking.CLICK_OFFER_PACKET, buf);
    }
}

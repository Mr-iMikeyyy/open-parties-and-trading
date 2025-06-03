package net.madmike.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.madmike.OpenPartiesAndTrading;
import net.madmike.trade.TradeOffer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class SyncAllOffersS2CPacket {
    public static final Identifier ID = new Identifier(OpenPartiesAndTrading.MOD_ID, "sync_offers");

    public static void send(ServerPlayerEntity player, List<TradeOffer> offers) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(offers.size());

        for (TradeOffer offer : offers) {
            offer.writeToBuf(buf);
        }

        ServerPlayNetworking.send(player, ID, buf);
    }
}

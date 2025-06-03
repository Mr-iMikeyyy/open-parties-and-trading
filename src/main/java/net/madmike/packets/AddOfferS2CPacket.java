package net.madmike.packets;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.madmike.OpenPartiesAndTrading;
import net.madmike.trade.TradeOffer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AddOfferS2CPacket {
    public static final Identifier ID = new Identifier(OpenPartiesAndTrading.MOD_ID, "add_offer");
    public static void send(MinecraftServer server, TradeOffer offer) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeNbt(offer.toNbt());

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            ServerPlayNetworking.send(player, ServerNetworking.ADD_OFFER_PACKET, buf);
        }
    }

    public static void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        TradeOffer newOffer = TradeOffer.fromNbt(buf.readNbt());
        TradingScreen.CLIENT_OFFERS.add(newOffer);
    }
}

package net.madmike.opat.client.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.madmike.opat.server.OpenPartiesAndTrading;
import net.minecraft.util.Identifier;

public class LeftPartyC2SPacket {
    public static final Identifier ID = new Identifier(OpenPartiesAndTrading.MOD_ID, "left_party");

    public static void send() {
        ClientPlayNetworking.send(ID, PacketByteBufs.empty());
    }
}

package net.madmike.trade;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public record TradeOffer(UUID seller, ItemStack item, long price, UUID sellerParty) {

    public void writeToBuf(PacketByteBuf buf) {
        buf.writeUuid(seller);        // seller UUID
        buf.writeItemStack(item);     // item being sold
        buf.writeVarLong(price);      // coin value
        buf.writeUuid(sellerParty);
    }

    public static TradeOffer readFromBuf(PacketByteBuf buf) {
        UUID seller = buf.readUuid();
        ItemStack item = buf.readItemStack();
        long price = buf.readVarLong();
        UUID sellerParty = buf.readUuid();
        return new TradeOffer(seller, item, price, sellerParty);
    }
}

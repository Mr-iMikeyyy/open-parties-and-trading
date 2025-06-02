package net.madmike.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.madmike.cache.TradingOfferCache;
import net.madmike.trade.TradeOffer;
import net.minecraft.util.Identifier;

import java.util.List;

public class TradingNetworking {
    public static final Identifier SYNC_OFFERS_PACKET = new Identifier("opat", "sync_offers");

    public static void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(SYNC_OFFERS_PACKET, (client, handler, buf, responseSender) -> {
            List<TradeOffer> offers = buf.readList(TradeOffer::readFromBuf);

            client.execute(() -> {
                TradingOfferCache.CLIENT_OFFERS.clear();
                TradingOfferCache.CLIENT_OFFERS.addAll(offers);
            });
        });
    }
}

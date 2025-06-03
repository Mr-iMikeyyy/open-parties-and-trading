package net.madmike.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.madmike.OpenPartiesAndTrading;
import net.madmike.trade.TradeOffer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static net.madmike.cache.TradingOffersCache.CLIENT_OFFERS;


public class ClientNetworking {
    public static final Identifier SYNC_OFFERS_PACKET = new Identifier(OpenPartiesAndTrading.MOD_ID, "sync_offers");

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(SYNC_OFFERS_PACKET, (client, handler, buf, responseSender) -> {
            List<TradeOffer> offers = new ArrayList<>();
            int count = buf.readInt();
            for (int i = 0; i < count; i++) {
                offers.add(TradeOffer.readFromBuf(buf));
            }

            // Run on main thread and apply to screen
            client.execute(() -> {
                CLIENT_OFFERS.clear();
                CLIENT_OFFERS.addAll(offers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver()
    }
}

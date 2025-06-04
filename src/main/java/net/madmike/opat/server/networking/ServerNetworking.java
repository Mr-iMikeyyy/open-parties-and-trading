package net.madmike.opat.server.networking;

import com.glisco.numismaticoverhaul.ModComponents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.madmike.opat.server.OpenPartiesAndTrading;
import net.madmike.opat.server.data.OfferStorageState;
import net.madmike.opat.server.gui.TradeTab;
import net.madmike.opat.server.packets.SyncAllOffersS2CPacket;
import net.madmike.trade.TradeManager;
import net.madmike.opat.server.trade.TradeOffer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ServerNetworking {
    public static final Identifier CLICK_OFFER_PACKET = new Identifier(OpenPartiesAndTrading.MOD_ID, "click_offer");
    public static final Identifier LIST_OFFER_PACKET = new Identifier(OpenPartiesAndTrading.MOD_ID, "list_offer");
    public static final Identifier LEFT_PARTY_PACKET = new Identifier(OpenPartiesAndTrading.MOD_ID, "left_party");



    public static void registerServerHandlers() {
        // Handle offer click (buy or cancel)
        ServerPlayNetworking.registerGlobalReceiver(CLICK_OFFER_PACKET, (server, player, handler, buf, responseSender) -> {
            int tabOrdinal = buf.readInt();
            int index = buf.readInt();
            TradeTab tab = TradeTab.values()[tabOrdinal];

            server.execute(() -> {
                List<TradeOffer> offers = TradeManager.getOffersFor(player.getUuid(), tab);
                if (index < 0 || index >= offers.size()) return;

                TradeOffer offer = offers.get(index);

                if (tab == TradeTab.MY_OFFERS && offer.seller().equals(player.getUuid())) {
                    TradeManager.removeOffer(offer);
                    player.getInventory().insertStack(offer.item().copy());
                    player.sendMessage(Text.literal("Offer canceled."), false);
                } else {
                    var wallet = ModComponents.CURRENCY.get(player);
                    if (wallet.getValue() >= offer.price()) {
                        wallet.modify(-offer.price());
                        player.getInventory().insertStack(offer.item().copy());
                        TradeManager.removeOffer(offer);
                        player.sendMessage(Text.literal("Purchase complete!"), false);
                    } else {
                        player.sendMessage(Text.literal("Not enough coins!"), false);
                    }
                }

                // Resend updated offers to reflect change
                List<TradeOffer> updated = TradeManager.getOffersFor(player.getUuid(), tab);
                SyncAllOffersS2CPacket.send(player, updated);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(LEFT_PARTY_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                // Perform cleanup or refresh on the server
                OfferStorageState.removeOffersFrom(player.getUuid());
                // Optionally send sync packet
                SyncAllOffersS2CPacket.sendToAll(server, OfferStorage.getAllOffers());
            });
        });
    }
}

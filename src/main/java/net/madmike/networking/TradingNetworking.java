package net.madmike.networking;

import com.glisco.numismaticoverhaul.ModComponents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.madmike.OpenPartiesAndTrading;
import net.madmike.gui.TradeTab;
import net.madmike.packets.SyncOffersS2CPacket;
import net.madmike.trade.TradeManager;
import net.madmike.trade.TradeOffer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class TradingNetworking {
    public static final Identifier TAB_CHANGE_PACKET = new Identifier(OpenPartiesAndTrading.MOD_ID, "tab_change");
    public static final Identifier CLICK_OFFER_PACKET = new Identifier(OpenPartiesAndTrading.MOD_ID, "click_offer");
    public static final Identifier SYNC_OFFERS_PACKET = new Identifier(OpenPartiesAndTrading.MOD_ID, "sync_offers");

    public static void registerServerHandlers() {
        // Handle tab switch from client
        ServerPlayNetworking.registerGlobalReceiver(TAB_CHANGE_PACKET, (server, player, handler, buf, responseSender) -> {
            int tabOrdinal = buf.readInt();
            TradeTab tab = TradeTab.values()[tabOrdinal];

            server.execute(() -> {
                List<TradeOffer> offers = TradeManager.getOffersFor(player.getUuid(), tab);
                SyncOffersS2CPacket.send(player, offers);
            });
        });

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
                SyncOffersS2CPacket.send(player, updated);
            });
        });
    }
}

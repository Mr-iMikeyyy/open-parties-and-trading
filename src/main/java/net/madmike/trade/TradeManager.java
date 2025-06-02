package net.madmike.trade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.madmike.gui.TradeTab;

public class TradeManager {
    private static final List<TradeOffer> globalOffers = new ArrayList<>();

    public static void addOffer(TradeOffer offer) {
        globalOffers.add(offer);
    }

    public static void removeOffer(TradeOffer offer) {
        globalOffers.remove(offer);
    }

    public static List<TradeOffer> getOffersFor(UUID viewer, TradeTab tab) {
        // For now, return unfiltered global list
        return new ArrayList<>(globalOffers);
    }

    public static List<TradeOffer> getAllOffers() {
        return globalOffers;
    }
}

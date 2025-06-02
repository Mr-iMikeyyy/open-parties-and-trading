package net.madmike.gui;

public enum TradeTab {
    SELL,
    MY_OFFERS,
    PARTY_OFFERS,
    ALLY_OFFERS,
    SCALLYWAG_OFFERS;

    public String getLabel() {
        return switch (this) {
            case SELL -> "Sell";
            case MY_OFFERS -> "My Offers";
            case PARTY_OFFERS -> "Party Offers";
            case ALLY_OFFERS -> "Ally Offers";
            case SCALLYWAG_OFFERS -> "Scallywag Sales";
        };
    }
}

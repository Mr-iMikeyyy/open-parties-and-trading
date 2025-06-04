package net.madmike.opat.server.data;

import net.madmike.opat.server.trade.TradeOffer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.PersistentState;

import java.util.ArrayList;
import java.util.List;

public class OfferStorageState extends PersistentState {
    public static final String SAVE_KEY = "open_parties_and_trading_offers";

    private final List<TradeOffer> offers = new ArrayList<>();

    public List<TradeOffer> getOffers() {
        return offers;
    }

    public void addOffer(TradeOffer offer) {
        offers.add(offer);
        markDirty(); // Important: mark the state dirty so it gets saved
    }

    public void removeOffer(TradeOffer offer) {
        offers.remove(offer);
        markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList offerList = new NbtList();
        for (TradeOffer offer : offers) {
            offerList.add(offer.toNbt());
        }
        nbt.put("Offers", offerList);
        return nbt;
    }

    public static OfferStorageState createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        OfferStorageState state = new OfferStorageState();
        NbtList offerList = tag.getList("Offers", NbtElement.COMPOUND_TYPE);
        for (NbtElement element : offerList) {
            if (element instanceof NbtCompound compound) {
                TradeOffer offer = TradeOffer.fromNbt(compound);
                state.offers.add(offer);
            }
        }
        return state;
    }
}

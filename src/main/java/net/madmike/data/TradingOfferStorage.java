package net.madmike.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.madmike.OpenPartiesAndTrading;
import net.madmike.networking.ServerNetworking;
import net.madmike.trade.TradeOffer;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TradingOfferStorage {

    private static final Path SAVE_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("open-parties-and-trading/offers.json");

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(TradeOffer.class, new TradeOfferAdapter())
            .setPrettyPrinting()
            .create();

    private static final List<TradeOffer> OFFERS = new ArrayList<>();

    public static void save() {
        try {
            Files.createDirectories(SAVE_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(SAVE_PATH)) {
                GSON.toJson(OFFERS, writer);
            }
        } catch (IOException e) {
            OpenPartiesAndTrading.LOGGER.error("Failed to save offers", e);
        }
    }

    public static void load() {
        if (!Files.exists(SAVE_PATH)) return;
        try (Reader reader = Files.newBufferedReader(SAVE_PATH)) {
            Type listType = new TypeToken<List<TradeOffer>>() {}.getType();
            List<TradeOffer> loaded = GSON.fromJson(reader, listType);
            OFFERS.clear();
            OFFERS.addAll(loaded);
        } catch (IOException e) {
            OpenPartiesAndTrading.LOGGER.error("Failed to load offers", e);
        }
    }

    public static void addOffer(TradeOffer offer) {
        OFFERS.add(offer);
        save(); // Optional: you could debounce or batch this instead
        ServerNetworking.sendOffersToAll(); // Optional: only call if you want instant sync
    }

    // your addOffer(), removeOffer(), syncAllOffers(), etc...
}

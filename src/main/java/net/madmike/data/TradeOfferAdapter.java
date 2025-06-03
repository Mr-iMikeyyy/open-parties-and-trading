package net.madmike.data;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.madmike.trade.TradeOffer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;

import java.io.IOException;
import java.util.UUID;

public class TradeOfferAdapter extends TypeAdapter<TradeOffer> {

    @Override
    public void write(JsonWriter out, TradeOffer offer) throws IOException {
        out.beginObject();
        out.name("seller").value(offer.seller().toString());
        out.name("price").value(offer.price());
        out.name("sellerParty").value(offer.sellerParty() != null ? offer.sellerParty().toString() : null);

        // Serialize item
        out.name("item");
        NbtCompound itemNbt = new NbtCompound();
        offer.item().writeNbt(itemNbt);
        out.jsonValue(itemNbt.toString());

        out.endObject();
    }

    @Override
    public TradeOffer read(JsonReader in) throws IOException {
        UUID seller = null;
        long price = 0;
        UUID sellerParty = null;
        ItemStack item = ItemStack.EMPTY;

        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "seller" -> seller = UUID.fromString(in.nextString());
                case "price" -> price = in.nextLong();
                case "sellerParty" -> {
                    String sp = in.nextString();
                    if (sp != null && !sp.isEmpty()) {
                        sellerParty = UUID.fromString(sp);
                    }
                }
                case "item" -> {
                    String nbtJson = in.nextString();
                    try {
                        NbtCompound tag = StringNbtReader.parse(nbtJson);
                        item = ItemStack.fromNbt(tag);
                    } catch (CommandSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        in.endObject();
        return new TradeOffer(seller, item, price, sellerParty);
    }
}

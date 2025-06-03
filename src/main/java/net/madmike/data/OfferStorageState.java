package net.madmike.data;

import net.madmike.trade.TradeOffer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.GameRules;
import net.minecraft.world.PersistentState;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OfferStorageState extends PersistentState {
    private List<TradeOffer> offers = new ArrayList<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putInt("totalDirtBlocksBroken", totalDirtBlocksBroken);
        return nbt;
    }
}

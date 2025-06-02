package net.madmike;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.madmike.packets.SyncOffersS2CPacket;
import net.madmike.trade.TradeOffer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.madmike.gui.TradingScreen;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OpenPartiesAndTradingClient implements ClientModInitializer {

	public static final List<TradeOffer> CLIENT_OFFERS = new ArrayList<>();

	@Override
	public void onInitializeClient() {
		OpenPartiesAndTrading.LOGGER.info("Client initialized");

		HandledScreens.register(ModScreens.TRADING_SCREEN_HANDLER, TradingScreen::new);

		ClientPlayNetworking.registerGlobalReceiver(SyncOffersS2CPacket.ID, (client, handler, buf, responseSender) -> {
			List<TradeOffer> offers = new ArrayList<>();
			int count = buf.readInt();
			for (int i = 0; i < count; i++) {
				UUID seller = buf.readUuid();
				ItemStack item = buf.readItemStack();
				long price = buf.readLong();
				offers.add(new TradeOffer(seller, item, price));
			}

			// Run on main thread and apply to screen
			client.execute(() -> {
				CLIENT_OFFERS.clear();
				CLIENT_OFFERS.addAll(offers);
			});
		});
	}
}
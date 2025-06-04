package net.madmike.opat.client;

import net.fabricmc.api.ClientModInitializer;
import net.madmike.opat.client.monitor.PartyLeaveMonitor;
import net.madmike.opat.client.networking.ClientNetworking;
import net.madmike.opat.server.ModScreens;
import net.madmike.opat.server.OpenPartiesAndTrading;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.madmike.opat.client.gui.TradingScreen;

public class OpenPartiesAndTradingClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		OpenPartiesAndTrading.LOGGER.info("Client initialized");

		HandledScreens.register(ModScreens.TRADING_SCREEN_HANDLER, TradingScreen::new);

		ClientNetworking.register();

		PartyLeaveMonitor.register();
	}
}
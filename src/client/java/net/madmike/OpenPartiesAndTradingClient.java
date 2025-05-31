package net.madmike;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.madmike.gui.TradingScreen;

public class OpenPartiesAndTradingClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		OpenPartiesAndTrading.LOGGER.info("Client initialized");

		HandledScreens.register(ModScreens.TRADING_SCREEN_HANDLER, TradingScreen::new);
	}
}
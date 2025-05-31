package net.madmike;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.madmike.net.TradingNetworking;

public class OpenPartiesAndTrading implements ModInitializer {
	public static final String MOD_ID = "open-parties-and-trading";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Trading Mod...");

		// Register networking
		TradingNetworking.registerServerHandlers();

		// Register screen handlers
		ModScreens.registerScreenHandlers();

		// Register commands
		TradeCommand.register();
	}
}
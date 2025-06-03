package net.madmike;

import net.fabricmc.api.ModInitializer;

import net.madmike.command.TradeCommand;
import net.madmike.networking.ServerNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenPartiesAndTrading implements ModInitializer {
	public static final String MOD_ID = "open-parties-and-trading";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Trading Mod...");

		// Register networking
		ServerNetworking.registerServerHandlers();

		// Register screen handlers
		ModScreens.registerScreenHandlers();

		// Register commands
		TradeCommand.register();
	}
}
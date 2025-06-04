package net.madmike.opat.server;

import net.fabricmc.api.ModInitializer;

import net.madmike.opat.server.command.TradeCommand;
import net.madmike.opat.server.data.OfferStorageState;
import net.madmike.opat.server.networking.ServerNetworking;
import net.minecraft.server.world.ServerWorld;
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

	public static OfferStorageState get(ServerWorld world) {
		return world.getPersistentStateManager().getOrCreate(
				(nbt, registryLookup) -> OfferStorageState.createFromNbt(nbt, registryLookup),
				OfferStorageState::new,
				OfferStorageState.SAVE_KEY
		);
	}
}
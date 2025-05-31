package net.madmike.gui;

import net.madmike.OpenPartiesAndTrading;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import net.madmike.gui.TradingScreenHandler;

public class ModScreens {
    public static ScreenHandlerType<TradingScreenHandler> TRADING_SCREEN_HANDLER;

    public static void registerScreenHandlers() {
        TRADING_SCREEN_HANDLER = Registry.register(
                Registries.SCREEN_HANDLER,
                new Identifier(OpenPartiesAndTrading.MOD_ID, "trading_screen"),
                new ScreenHandlerType<>(TradingScreenHandler::new)
        );
    }
}

package net.madmike.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.madmike.gui.TradingScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import static net.minecraft.server.command.CommandManager.literal;

public class TradeCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("trade").executes(context -> {
                ServerCommandSource source = context.getSource();

                if (source.getEntity() instanceof ServerPlayerEntity player) {
                    player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                            (syncId, inventory, p) -> new TradingScreenHandler(syncId, inventory),
                            Text.literal("Trading Terminal")
                    ));
                } else {
                    source.sendError(Text.literal("Only players can use /trade."));
                }

                return 1;
            }));
        });
    }
}

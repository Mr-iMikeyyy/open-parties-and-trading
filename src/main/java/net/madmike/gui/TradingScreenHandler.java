package net.madmike.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class TradingScreenHandler extends ScreenHandler {
    private final SimpleInventory inventory;

    public TradingScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreens.TRADING_SCREEN_HANDLER, syncId);
        this.inventory = new SimpleInventory(1); // 1 item listed for sale
        inventory.onOpen(playerInventory.player);

        // Slot for the item being listed
        this.addSlot(new Slot(inventory, 0, 80, 35)); // x/y are pixel coords in GUI

        // Player inventory slots (main)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Hotbar slots
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public ItemStack getListedItem() {
        return inventory.getStack(0);
    }

    public void setListedItem(ItemStack stack) {
        inventory.setStack(0, stack);
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }
}

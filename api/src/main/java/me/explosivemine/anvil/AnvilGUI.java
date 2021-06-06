/*MIT License
Copyright (c) 2016 Wesley Smith

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package me.explosivemine.anvil;

import me.explosivemine.anvil.version.VersionMatcher;
import me.explosivemine.anvil.version.abstraction.VersionWrapper;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AnvilGUI {
    private static final VersionWrapper WRAPPER = new VersionMatcher().match();

    private final Player player;

    private final String inventoryTitle;

    private final ItemStack inputLeft;
    private final ItemStack inputRight;

    private AnvilGUI(Player player, String inventoryTitle, ItemStack inputLeft, ItemStack inputRight) {
        this.player = player;
        this.inventoryTitle = inventoryTitle;
        this.inputLeft = inputLeft;
        this.inputRight = inputRight;

        openInventory();
    }

    private void openInventory() {
        WRAPPER.handleInventoryCloseEvent(player);
        WRAPPER.setActiveContainerDefault(player);

        final Object container = WRAPPER.newContainerAnvil(player, inventoryTitle);

        Inventory inventory = WRAPPER.toBukkitInventory(container);
        inventory.setItem(0, inputLeft);
        inventory.setItem(1, inputRight);

        int containerId = WRAPPER.getNextContainerId(player, container);
        WRAPPER.sendPacketOpenWindow(player, containerId, inventoryTitle);
        WRAPPER.setActiveContainer(player, container);
        WRAPPER.setActiveContainerId(container, containerId);
        WRAPPER.addActiveContainerSlotListener(container, player);
    }

    public static class Builder {
        private String title = "Repair & Name";

        private ItemStack itemLeft;

        private ItemStack itemRight;

        public Builder itemLeft(ItemStack item) {
            Validate.notNull(item, "item cannot be null");
            this.itemLeft = item;
            return this;
        }

        public Builder itemRight(ItemStack item) {
            this.itemRight = item;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public void open(Player player) {
            Validate.notNull(player, "Player cannot be null");
            new AnvilGUI(player, title, itemLeft, itemRight);
        }
    }
}
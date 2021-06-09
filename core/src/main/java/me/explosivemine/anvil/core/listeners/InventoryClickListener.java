package me.explosivemine.anvil.core.listeners;

import me.explosivemine.anvil.Anvil;
import me.explosivemine.anvil.Util;
import me.explosivemine.anvil.XSound;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClickListener implements Listener {
    private final Anvil plugin;
    private final boolean handleAnvilColours;

    public InventoryClickListener(boolean handleAnvilColours) {
        this.plugin = Anvil.getINSTANCE();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.handleAnvilColours = handleAnvilColours;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;

        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)) return;
        final Player player = (Player) entity;
        if (!(event.getInventory() instanceof AnvilInventory)) return;
        final int rawSlot = event.getRawSlot();
        if (rawSlot == event.getView().convertSlot(rawSlot) && rawSlot == 2) {
            player.playSound(player.getLocation(), XSound.BLOCK_ANVIL_USE.parseSound(), 0.5f, 0.5f);

            if (!handleAnvilColours) return;
            if (!player.hasPermission("anvil.colour")) return;

            ItemStack item = event.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Util.chat(meta.getDisplayName()));
            item.setItemMeta(meta);
        }
    }
}
package me.explosivemine.anvil.core.listeners;

import me.explosivemine.anvil.Anvil;
import me.explosivemine.anvil.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PrepareAnvilListener implements Listener {
    private final Anvil plugin;

    public PrepareAnvilListener() {
        this.plugin = Anvil.getINSTANCE();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPrepare(PrepareAnvilEvent event) {
        final Player player = (Player) event.getView().getPlayer();
        if (!player.hasPermission("anvil.colour")) return;

        ItemStack item = event.getResult();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.chat(meta.getDisplayName()));
        item.setItemMeta(meta);
    }
}

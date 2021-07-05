package me.explosivemine.anvil;

import lombok.Getter;
import me.explosivemine.anvil.core.AnvilCmd;
import me.explosivemine.anvil.core.ConfigData;
import me.explosivemine.anvil.core.listeners.InventoryClickListener;
import me.explosivemine.anvil.core.listeners.InventoryCloseListener;
import me.explosivemine.anvil.core.listeners.PlayerInteractListener;
import me.explosivemine.anvil.core.listeners.PrepareAnvilListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Anvil extends JavaPlugin {
    public static Anvil INSTANCE;
    private static String SERVER_VERSION;
    @Getter
    private ConfigData configData;
    @Getter
    private List<UUID> openPlayers;

    @Override
    public void onEnable() {
        INSTANCE = this;
        openPlayers = new ArrayList<>();
        SERVER_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

        configData = new ConfigData(INSTANCE);
        if (!configData.cont) return;

        registerCommandsAndListeners();
        setupMetrics();
    }

    private void setupMetrics() {
        final int pluginID = 11598;
        new Metrics(INSTANCE, pluginID);
    }

    private void registerCommandsAndListeners() {
        //Commands
        new AnvilCmd(INSTANCE);

        //Listeners
        new InventoryCloseListener(this);
        if (configData.isUnbreakableAnvils()) new PlayerInteractListener(INSTANCE);

        if (SERVER_VERSION.compareTo("1_10_R1") < 0) {
            if (configData.isAnvilColours()) new PrepareAnvilListener(INSTANCE);
            new InventoryClickListener(false, INSTANCE);
        } else {
            new InventoryClickListener(configData.isAnvilColours(), INSTANCE);
        }
    }
}
package me.explosivemine.anvil;

import lombok.Getter;
import me.explosivemine.anvil.core.AnvilCmd;
import me.explosivemine.anvil.core.ConfigData;
import me.explosivemine.anvil.core.listeners.InventoryClickListener;
import me.explosivemine.anvil.core.listeners.PlayerInteractListener;
import me.explosivemine.anvil.core.listeners.PrepareAnvilListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Anvil extends JavaPlugin {
    @Getter
    public static Anvil INSTANCE;
    @Getter
    private ConfigData configData;

    private String SERVER_VERSION;


    @Override
    public void onEnable() {
        INSTANCE = this;
        SERVER_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

        configData = new ConfigData();
        if (!configData.cont) return;

        registerCommandsAndListeners();
        setupMetrics();
    }

    private void setupMetrics() {
        final int pluginID = 11598;
        new Metrics(this, pluginID);
    }

    private void registerCommandsAndListeners() {
        //Commands
        new AnvilCmd();

        //Listeners
        if (configData.isUnbreakableAnvils()) new PlayerInteractListener();

        if (SERVER_VERSION.compareTo("1_10_R1") < 0) {
            if (configData.isAnvilColours()) new PrepareAnvilListener();
            new InventoryClickListener(false);
        } else {
            new InventoryClickListener(configData.isAnvilColours());
        }
    }
}
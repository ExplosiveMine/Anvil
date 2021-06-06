package me.explosivemine.anvil.core;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Anvil extends JavaPlugin {
    @Getter
    public static Anvil INSTANCE;
    @Getter
    private ConfigData configData;


    @Override
    public void onEnable() {
        INSTANCE = this;

        configData = new ConfigData();
        if (!configData.cont) return;

        new AnvilCmd();
    }
}

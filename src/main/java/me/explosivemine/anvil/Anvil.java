package me.explosivemine.anvil;

import lombok.Getter;
import me.explosivemine.anvil.core.AnvilCmd;
import me.explosivemine.anvil.core.ConfigData;
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

    @Override
    public void onDisable() {

    }
}

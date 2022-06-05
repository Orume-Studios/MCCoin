package id.orume.mccoin;

import org.bukkit.plugin.java.JavaPlugin;

public final class MCCoin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("MCCoin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

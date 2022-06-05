package id.orume.mccoin;

import id.orume.mccoin.config.MainConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCCoin extends JavaPlugin {
    @Getter private final MainConfig mainConfig = new MainConfig(this);

    @Override
    public void onEnable() {
        Utils.debugValue = mainConfig.isDebug();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

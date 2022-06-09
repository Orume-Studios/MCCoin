package id.orume.mccoin;

import id.orume.mccoin.config.MainConfig;
import id.orume.mccoin.utils.Utils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCCoin extends JavaPlugin {
    @Getter private final MainConfig mainConfig = new MainConfig(this);
    @Getter private final CoinCMD coinCMD = new CoinCMD(this);
    @Getter private final CoinDB coinDB = new CoinDB(this);;
    @Getter private final CoinManager coinManager = new CoinManager(this);


    @Override
    public void onEnable() {
        Utils.debugValue = mainConfig.isDebug();

        this.coinCMD.registerCommands();
        Utils.log("MCCoin loaded successfully");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        this.coinDB.closePool();

    }
}

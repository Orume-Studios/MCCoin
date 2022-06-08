package id.orume.mccoin;

import id.orume.mccoin.config.MainConfig;
import id.orume.mccoin.utils.Utils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCCoin extends JavaPlugin {
    @Getter private final MainConfig mainConfig = new MainConfig(this);
    @Getter private final MCCoinCMD mcCoinCMD = new MCCoinCMD(this);
    @Getter private final MCCoinDB mcCoinDB = new MCCoinDB(this);;


    @Override
    public void onEnable() {
        Utils.debugValue = mainConfig.isDebug();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(mcCoinDB.getHikari() != null) {
            mcCoinDB.getHikari().close();
        }

    }
}

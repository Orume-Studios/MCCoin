package id.orume.mccoin.config;

import id.orume.mccoin.MCCoin;
import id.orume.mccoin.config.main.DatabaseConfig;
import lombok.Getter;

public class MainConfig extends Config {
    @Getter private boolean debug;
    @Getter private DatabaseConfig databaseConfig;

    public MainConfig(MCCoin plugin) {
        super(plugin, "main.yml");
    }

    @Override
    void reload() {
        this.load();
        this.debug = this.yamlConf.getBoolean("debug");
        this.databaseConfig = new DatabaseConfig(plugin);

    }
}

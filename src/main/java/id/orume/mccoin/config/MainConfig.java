package id.orume.mccoin.config;

import id.orume.mccoin.MCCoin;
import lombok.Getter;

public class MainConfig extends Config {
    @Getter private boolean debug;

    public MainConfig(MCCoin plugin) {
        super(plugin, "main.yml");

    }

    @Override
    void reload() {
        this.load();
        this.debug = this.yamlConf.getBoolean("debug");
    }
}

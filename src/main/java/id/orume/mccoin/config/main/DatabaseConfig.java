package id.orume.mccoin.config.main;

import id.orume.mccoin.MCCoin;
import lombok.Getter;

public class DatabaseConfig {
    @Getter private final String host;
    @Getter private final String port;
    @Getter private final String database;
    @Getter private final String username;
    @Getter private final String password;

    public DatabaseConfig(MCCoin plugin) {
        this.host = plugin.getConfig().getString("database.host");
        this.port = plugin.getConfig().getString("database.port");
        this.database = plugin.getConfig().getString("database.database");
        this.username = plugin.getConfig().getString("database.username");
        this.password = plugin.getConfig().getString("database.password");

    }
}

package id.orume.mccoin.config.main;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseConfig {
    @Getter private final String host;
    @Getter private final String port;
    @Getter private final String databaseName;
    @Getter private final String username;
    @Getter private final String password;
    @Getter private final String storageMethod;

    public DatabaseConfig(FileConfiguration config) {
        this.host = config.getString("database.host");
        this.port = config.getString("database.port");
        this.databaseName = config.getString("database.database-name");
        this.username = config.getString("database.username");
        this.password = config.getString("database.password");
        this.storageMethod = config.getString("database.storage-method");

    }
}

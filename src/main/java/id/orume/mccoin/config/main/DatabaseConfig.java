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
    @Getter private final String tablePrefix;
    @Getter private final int maximumPoolSize;
    @Getter private final int minimumIdle;
    @Getter private final int maximumLifetime;
    @Getter private final int keepAliveTime;
    @Getter private final int connectionTimeout;
    @Getter private final String collation;
    @Getter private final boolean useSSL;

    public DatabaseConfig(FileConfiguration config) {
        this.host = config.getString("database.host");
        this.port = config.getString("database.port");
        this.databaseName = config.getString("database.database-name");
        this.username = config.getString("database.username");
        this.password = config.getString("database.password");
        this.storageMethod = config.getString("database.storage-method");
        this.maximumPoolSize = config.getInt("database.pool-settings.maximum-pool-size");
        this.minimumIdle = config.getInt("database.pool-settings.minimum-idle");
        this.maximumLifetime = config.getInt("database.pool-settings.maximum-lifetime");
        this.keepAliveTime = config.getInt("database.pool-settings.keep-alive-time");
        this.connectionTimeout = config.getInt("database.pool-settings.connection-timeout");
        this.useSSL = config.getBoolean("database.pool-settings.useSSL");
        this.tablePrefix = config.getString("database.table-prefix");
        this.collation = config.getString("database.collation");
    }


}

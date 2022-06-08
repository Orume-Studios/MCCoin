package id.orume.mccoin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import id.orume.mccoin.utils.Utils;
import lombok.Getter;

public class MCCoinDB {
    @Getter private final HikariDataSource hikari;

    public MCCoinDB(MCCoin plugin) {
        HikariConfig config = new HikariConfig();

        String host = plugin.getMainConfig().getDatabaseConfig().getHost();
        String port = plugin.getMainConfig().getDatabaseConfig().getPort();
        String databaseName = plugin.getMainConfig().getDatabaseConfig().getDatabaseName();
        String storageMethod = plugin.getMainConfig().getDatabaseConfig().getStorageMethod();
        if(storageMethod == null) {
            Utils.log(Utils.colorize("&cCannot initialize database connection. Storage method is not defined."));
            plugin.getPluginLoader().disablePlugin(plugin);
        } else  {
            if(storageMethod.equalsIgnoreCase("mysql")) {
                config.setDriverClassName("com.mysql.jdbc.Driver");
                config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + databaseName);
            } else if(storageMethod.equalsIgnoreCase("sqlite")) {
                config.setDriverClassName("org.sqlite.JDBC");
                config.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder() + "/" + databaseName + ".db");
            } else if(storageMethod.equalsIgnoreCase("postgresql")) {
                config.setDriverClassName("org.postgresql.Driver");
                config.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + databaseName);
            } else if(storageMethod.equalsIgnoreCase("h2")) {
                config.setDriverClassName("org.h2.Driver");
                config.setJdbcUrl("jdbc:h2:" + "./" + plugin.getDataFolder() + "/" + databaseName + ".db");
            } else if(storageMethod.equalsIgnoreCase("mariadb")) {
                config.setDriverClassName("org.mariadb.jdbc.Driver");
                config.setJdbcUrl("jdbc:mariadb://" + host + ":" + port + "/" + databaseName);
            } else {
                Utils.log(Utils.colorize("&cCannot initialize database connection. Unknown storage method: &e" + storageMethod));
                Utils.log(Utils.colorize("&cStorage method " + plugin.getMainConfig().getDatabaseConfig().getStorageMethod() + " is not supported"));
                plugin.getPluginLoader().disablePlugin(plugin);
            }
        }

        config.addDataSourceProperty("serverName", plugin.getMainConfig().getDatabaseConfig().getHost());
        config.addDataSourceProperty("port", plugin.getMainConfig().getDatabaseConfig().getPort());
        config.addDataSourceProperty("databaseName", plugin.getMainConfig().getDatabaseConfig().getDatabaseName());
        config.addDataSourceProperty("user", plugin.getMainConfig().getDatabaseConfig().getUsername());
        config.addDataSourceProperty("password", plugin.getMainConfig().getDatabaseConfig().getPassword());
        config.addDataSourceProperty("autoReconnect",true);
        config.setConnectionTestQuery("SELECT 1");
        config.setPoolName("Orume-MCCoin-Hikari");

        this.hikari = new HikariDataSource(config);
    }
}

package id.orume.mccoin.coinmanager;

import id.orume.mccoin.MCCoin;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CoinManager {
    private final MCCoin plugin;

    public CoinManager(MCCoin mcCoin) {
        this.plugin = mcCoin;
        this.init();
    }

    @SneakyThrows
    private void init() {

        Statement statement = plugin.getMcCoinDB().getConnection().createStatement();
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS `" + plugin.getMainConfig().getDatabaseConfig().getTablePrefix() + "coins` ("
                        + "`id` int(11) NOT NULL AUTO_INCREMENT," +
                        "`name` char(255) CHARACTER SET latin1 NOT NULL," +
                        "PRIMARY KEY (`id`)," +
                        "UNIQUE KEY `name` (`name`)" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;"
        );

        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS `" + plugin.getMainConfig().getDatabaseConfig().getTablePrefix() + "players` (" +
                        "  `coin_id` int(11) DEFAULT NULL," +
                        "  `username` text CHARACTER SET latin1 DEFAULT NULL," +
                        "  `value` bigint(20) DEFAULT NULL" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;"
        );
    }
}

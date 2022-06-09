package id.orume.mccoin;

import id.orume.mccoin.utils.Utils;
import lombok.SneakyThrows;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoinManager {
    private final MCCoin plugin;
    private final String coinKeyTable;
    private final String coinDataTable;

    @SneakyThrows
    public CoinManager(MCCoin mcCoin) {
        this.plugin = mcCoin;
        this.coinKeyTable = plugin.getMainConfig().getDatabaseConfig().getTablePrefix() + "keys";
        this.coinDataTable = plugin.getMainConfig().getDatabaseConfig().getTablePrefix() + "data";

        Statement statement = plugin.getCoinDB().getConnection().createStatement();
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS `" + this.coinKeyTable + "` ("
                        + "`id` int(11) NOT NULL AUTO_INCREMENT," +
                        "`name` char(255) CHARACTER SET latin1 NOT NULL," +
                        "PRIMARY KEY (`id`)," +
                        "UNIQUE KEY `name` (`name`)" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;"
        );

        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS `" + this.coinDataTable + "` (" +
                        "  `coin_id` int(11) DEFAULT NULL," +
                        "  `username` text CHARACTER SET latin1 DEFAULT NULL," +
                        "  `amount` bigint(20) DEFAULT NULL" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;"
        );
    }


    /**
     *
     * @param username - The player's username
     * @return - returns the coin current value, returns null if the data don't exist.
     */
    public Integer getPlayerCoinAmount(String username) {
        try {
            PreparedStatement preparedStatement = plugin.getCoinDB().getConnection().prepareStatement("SELECT amount FROM " + this.coinDataTable + " where username = ?;");
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("amount");
            }
        } catch (SQLException e) {
            Utils.debug("&cCannot get player's coin value with username " + username, e);
        }

        return null;
    }


    /**
     *
     * @param coinId - The Coin ID
     * @param username - The player's username
     * @param amount - The coin amount to set
     * @param currentCoinAmount - Current player coin amount, use {@link #getPlayerCoinAmount(String)} to get the current amount.
     * @return - returns true if the data operation success.
     */
    public boolean setPlayerCoinAmount(int coinId, String username, int amount, Integer currentCoinAmount) {
        try {
            PreparedStatement ps;
            if(currentCoinAmount == null) {
                ps = plugin.getCoinDB().getConnection().prepareStatement(
                        "INSERT INTO " + this.coinDataTable + " (coin_id, username, value) " + "VALUES (?, ?, ?);"
                );

                ps.setInt(1, coinId);
                ps.setString(2, username);
                ps.setInt(3, amount);
            } else {
                ps = plugin.getCoinDB().getConnection().prepareStatement(
                        "UPDATE " + this.coinDataTable + " SET value = ? where username = ?;"
                );

                ps.setInt(1, amount);
                ps.setString(2, username);
            }

            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            Utils.debug("&cCannot set player's coin amount with username " + username, e);
        }

        return false;
    }

    public List<String> getCoinNames() {
        try {
            ResultSet resultSet = plugin.getCoinDB().getConnection().createStatement().executeQuery("SELECT * FROM " + this.coinKeyTable + ";");

            List<String> coinNames = new ArrayList<>();
            while(resultSet.next()) {
                coinNames.add(resultSet.getString("name"));
            }

            return coinNames;

        } catch (SQLException e) {
            Utils.debug("Cannot get coin names", e);
        }

        return null;
    }


    /**
     *
     * @param name
     * @return - returns true if the data is created, returns false if the data is exist
     */
    public boolean addCoinKey(String name) {
        try {
            PreparedStatement ps = this.plugin.getCoinDB().getConnection().prepareStatement("INSERT INTO " + this.coinKeyTable + " (name) VALUES (?)");
            ps.setString(1, name);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            Utils.debug("Cannot create a coin " + name, e);
        }

        return false;
    }

    /**
     *
     * @param name
     * @return - returns the id
     */
    public Integer getCoinKeyId(String name) {
        try {
            PreparedStatement ps = this.plugin.getCoinDB().getConnection().prepareStatement("SELECT id FROM " + this.coinKeyTable + " where name = ?");
            ps.setString(1, name);
            ps.executeQuery();

            ResultSet resultSet = ps.getResultSet();
            if(resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            Utils.debug("Cannot get a coin " + name, e);
        }

        return null;
    }

    /**
     *
     * @param id - the coin id
     * @return - returns the name
     */
    public String getCoinKeyName(int id) {
        try {
            PreparedStatement ps = this.plugin.getCoinDB().getConnection().prepareStatement("SELECT name FROM " + this.coinKeyTable + " where id = ?");
            ps.setInt(1, id);
            ps.executeQuery();

            ResultSet resultSet = ps.getResultSet();
            if(resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            Utils.debug("Cannot get a coin with id " + id, e);
        }

        return null;
    }

    public boolean deleteCoinKey(int id) {
        try {
            PreparedStatement ps = this.plugin.getCoinDB().getConnection().prepareStatement("DELETE FROM " + this.coinKeyTable + " where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            Utils.debug("Cannot create a coin with id" + id, e);
        }

        return false;
    }

    public boolean deleteCoinKey(String name) {
        try {
            PreparedStatement ps = this.plugin.getCoinDB().getConnection().prepareStatement("DELETE FROM " + this.coinKeyTable + " where name = ?");
            ps.setString(1, name);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            Utils.debug("Cannot create a coin " + name, e);
        }

        return false;
    }
}

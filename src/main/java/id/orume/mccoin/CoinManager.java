package id.orume.mccoin;

import id.orume.mccoin.utils.Utils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoinManager {
    private final MCCoin plugin;
    private final String coinKeyTable;
    private final String coinDataTable;

    public CoinManager(MCCoin mcCoin) {
        this.plugin = mcCoin;
        this.coinKeyTable = plugin.getMainConfig().getDatabaseConfig().getTablePrefix() + "keys";
        this.coinDataTable = plugin.getMainConfig().getDatabaseConfig().getTablePrefix() + "data";

        try(Connection connection = plugin.getCoinDB().getConnection()) {
            Statement statement = connection.createStatement();
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
                            "  `username` char(255) CHARACTER SET latin1 DEFAULT NULL," +
                            "  `amount` bigint(20) DEFAULT NULL," +
                            " UNIQUE KEY `username` (`username`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;"
            );
        } catch (SQLException e) {
            Utils.log("Cannot migrate database: " + e.getMessage());
        }
    }


    /**
     *
     * @param username - The player's username
     * @return - returns the coin current amount, returns null if the data don't exist.
     */
    public Integer getPlayerCoinAmount(String username, int coinId) {
        try(Connection connection = plugin.getCoinDB().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT amount FROM " + this.coinDataTable + " where username = ? AND coin_id = ?;");
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, coinId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {

                return resultSet.getInt("amount");
            }
        } catch (SQLException e) {
            Utils.debug("&cCannot get player's coin amount with username " + username, e);
        }

        return null;
    }


    /**
     *
     * @param coinId - The Coin ID
     * @param username - The player's username
     * @param amount - The coin amount to set
     * @param currentCoinAmount - Current player coin amount, use {@link #getPlayerCoinAmount(String, int)} to get the current amount.
     * @return - returns true if the data operation success.
     */
    public boolean setPlayerCoinAmount(int coinId, String username, int amount, Integer currentCoinAmount) {
        try(Connection connection = plugin.getCoinDB().getConnection()) {
            PreparedStatement ps;
            if(currentCoinAmount == null) {
                ps = connection.prepareStatement(
                        "INSERT INTO " + this.coinDataTable + " (coin_id, username, amount) " + "VALUES (?, ?, ?);"
                );

                ps.setInt(1, coinId);
                ps.setString(2, username);
                ps.setInt(3, amount);
            } else {
                ps = connection.prepareStatement(
                        "UPDATE " + this.coinDataTable + " SET amount = ? where username = ?;"
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
        try(Connection connection = plugin.getCoinDB().getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + this.coinKeyTable + ";");

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
     * @param name - the coin key name
     * @return - returns true if the data is created, returns false if the data is exist
     */
    public boolean createCoinKey(String name) {
        try(Connection connection = plugin.getCoinDB().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " + this.coinKeyTable + " (name) VALUES (?)");
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
     * @param name - the coin key name
     * @return - returns the id
     */
    public Integer getCoinKeyId(String name) {
        try(Connection connection = plugin.getCoinDB().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM " + this.coinKeyTable + " where name = ?");
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
        try(Connection connection = plugin.getCoinDB().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT name FROM " + this.coinKeyTable + " where id = ?");
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
        try(Connection connection = plugin.getCoinDB().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM " + this.coinKeyTable + " where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            Utils.debug("Cannot create a coin with id" + id, e);
        }

        return false;
    }

    public boolean deleteCoinKey(String name) {

        try(Connection connection = plugin.getCoinDB().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM " + this.coinKeyTable + " where name = ?");
            ps.setString(1, name);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            Utils.debug("Cannot create a coin " + name, e);
        }

        return false;
    }
}

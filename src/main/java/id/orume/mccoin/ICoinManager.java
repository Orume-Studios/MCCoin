package id.orume.mccoin;

import java.util.List;

public interface ICoinManager {
    boolean createCoinKey(String name);
    boolean deleteCoinKey(String name);
    boolean deleteCoinKey(int id);
    boolean renameCoinKey(String oldName, String newName);
    Integer getCoinKeyId(String name);
    String getCoinKeyName(int id);
    List<String> getCoinNames();
    Integer getPlayerCoinAmount(String username, int coinId);
    boolean setPlayerCoinAmount(String username, int coinId, int amount, Integer currentCoinAmount);
}

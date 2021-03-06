package id.orume.mccoin.commands;

import id.orume.mccoin.MCCoin;
import id.orume.mccoin.utils.Lang;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ResetSubCmd extends SubCommand {
    public ResetSubCmd(MCCoin plugin) {
        super(plugin, "reset", "Reset the coins from a player", "<coin_name> <player>", "mccoin.reset", false);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NonNull List<String> args) {
        if(args.size() < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /mccoin add <coin_name> <player>");
            return;
        }

        String coinName = args.get(0);
        String playerName = args.get(1);

        Integer coinId = this.plugin.getCoinManager().getCoinKeyId(coinName);
        if(coinId == null) {
            sender.sendMessage(Lang.INVALID_ARGUMENT.getStringValue() + ", the coin name is not valid!");
            return;
        }


        Integer currentAmount = this.plugin.getCoinManager().getPlayerCoinAmount(playerName, coinId);
        boolean isSuccess = this.plugin.getCoinManager().setPlayerCoinAmount(playerName, coinId, 0, currentAmount);

        if(isSuccess) {
            sender.sendMessage(ChatColor.GREEN + "Successfully reset the coins from " + ChatColor.YELLOW + playerName);
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to reset the coins from " + ChatColor.YELLOW + playerName);
        }
    }

    @Override
    public List<String> onTabComplete(@NonNull  CommandSender sender, @NonNull  List<String> args) {
        if(args.size() == 0) {
            return this.plugin.getCoinManager().getCoinNames();
        }

        return null;
    }
}

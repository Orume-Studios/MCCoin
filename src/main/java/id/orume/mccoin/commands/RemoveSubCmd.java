package id.orume.mccoin.commands;

import id.orume.mccoin.MCCoin;
import id.orume.mccoin.utils.Lang;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RemoveSubCmd extends SubCommand {
    public RemoveSubCmd(MCCoin plugin) {
        super(plugin, "remove", "Remove coins from a player", "<coin_name> <player> <amount>", "mccoin.remove", false);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NonNull List<String> args) {
        if(args.size() < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /mccoin remove <coin_name> <player> <amount>");
            return;
        }

        String coinName = args.get(0);
        String playerName = args.get(1);
        int amount = Integer.parseInt(args.get(2));

        Integer coinId = this.plugin.getCoinManager().getCoinKeyId(coinName);
        if(coinId == null) {
            sender.sendMessage(Lang.INVALID_ARGUMENT.getStringValue() + ", the coin name is not valid!");
            return;
        }

        Integer currentAmount = this.plugin.getCoinManager().getPlayerCoinAmount(playerName, coinId);

        if(currentAmount == null) {
            sender.sendMessage(ChatColor.RED + "The player does not have any coin!");
            return;
        } else {
            amount = currentAmount - amount;
            if(amount < 0) {
                sender.sendMessage(Lang.INVALID_ARGUMENT.getStringValue() + ", the amount is too big!");
                return;
            }
        }


        boolean isSuccess = this.plugin.getCoinManager().setPlayerCoinAmount(playerName, coinId, amount, currentAmount);
        if(isSuccess) {
            sender.sendMessage(ChatColor.GREEN + "Successfully removed " + ChatColor.YELLOW + args.get(2)  + ChatColor.GREEN + " coins from " + ChatColor.YELLOW + playerName);
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to remove " + ChatColor.YELLOW + args.get(2) + ChatColor.RED + " coins from " + ChatColor.YELLOW + playerName);
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

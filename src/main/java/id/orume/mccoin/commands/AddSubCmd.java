package id.orume.mccoin.commands;

import id.orume.mccoin.MCCoin;
import id.orume.mccoin.utils.Lang;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AddSubCmd extends SubCommand {
    public AddSubCmd(MCCoin plugin) {
        super(plugin, "add", "Add coins to a player", "<coin_name> <player> <amount>", "mccoin.add", false);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NonNull List<String> args) {
        if(args.size() < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /mccoin add <coin_name> <player> <amount>");
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

        if(currentAmount != null) amount += currentAmount;
        boolean isSuccess = this.plugin.getCoinManager().setPlayerCoinAmount(playerName, coinId, amount, currentAmount);

        if(isSuccess) {
            sender.sendMessage(ChatColor.GREEN + "Successfully added " + ChatColor.YELLOW + args.get(2) + ChatColor.GREEN + " coins to " + ChatColor.YELLOW + playerName);
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to add " + ChatColor.YELLOW + args.get(2) + ChatColor.RED + " coins to " + ChatColor.YELLOW + playerName);
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

package id.orume.mccoin.commands;

import id.orume.mccoin.MCCoin;
import id.orume.mccoin.utils.Lang;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DeleteSubCmd extends SubCommand {
    public DeleteSubCmd(MCCoin plugin) {
        super(plugin, "delete", "Deletes a coin key", "<name>", "mccoin.delete", false);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NonNull List<String> args) {
        String name = args.get(0);
        if(name.length() > 255) {
            sender.sendMessage(Lang.INVALID_ARGUMENT.getStringValue() + ", the coin name should not be longer than 255 characters");
            return;
        } else if(name.length() == 0) {
            sender.sendMessage(Lang.MISSING_ARGUMENTS.getStringValue() + ", the coin name should be not empty!");
            return;
        }

        Integer coinId = this.plugin.getCoinManager().getCoinKeyId(name);
        if(coinId == null) {
            sender.sendMessage(ChatColor.RED + "Failed to delete the coin, a coin with the specified name doesn't exist!");
            return;
        }

        boolean result = this.plugin.getCoinManager().deleteCoinKey(name);
        if(result) {
            sender.sendMessage(ChatColor.GREEN + "Successfully deleted a coin with name " + ChatColor.YELLOW + name);
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to delete a coin with name " + ChatColor.YELLOW + name);
        }
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull List<String> args) {
        if(args.isEmpty()) {
            args.set(0, "<coin_name>");
            return args;
        }
        return null;
    }
}

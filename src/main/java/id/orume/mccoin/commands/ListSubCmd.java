package id.orume.mccoin.commands;

import id.orume.mccoin.MCCoin;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListSubCmd extends SubCommand{
    public ListSubCmd(MCCoin plugin) {
        super(plugin, "list", "Shows the coin key names", "<name>", "mccoin.create", false);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NonNull List<String> args) {
        List<String> coinNames = this.plugin.getCoinManager().getCoinNames();
        if(coinNames == null) {
            sender.sendMessage(ChatColor.RED + "There is no coin created yet!");
            return;
        }

        sender.sendMessage(ChatColor.YELLOW + "There is " + ChatColor.GREEN + coinNames.size() + ChatColor.YELLOW + " coins:");
        sender.sendMessage(ChatColor.AQUA + String.join(", ", coinNames));
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull List<String> args) {
        return null;
    }

}

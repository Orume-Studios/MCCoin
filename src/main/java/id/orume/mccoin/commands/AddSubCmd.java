package id.orume.mccoin.commands;

import id.orume.mccoin.MCCoin;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AddSubCmd extends SubCommand {
    public AddSubCmd(MCCoin plugin) {
        super(plugin, "add", "Add coins to a player", "<player> <amount>", "mccoin.add", false);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NonNull List<String> args) {

    }

    @Override
    public List<String> onTabComplete(@NonNull  CommandSender sender, @NonNull  List<String> args) {
        return null;
    }
}

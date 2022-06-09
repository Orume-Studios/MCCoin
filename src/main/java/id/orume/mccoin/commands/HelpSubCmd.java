package id.orume.mccoin.commands;

import id.orume.mccoin.MCCoin;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpSubCmd extends SubCommand {

    public HelpSubCmd(MCCoin plugin) {
        super(plugin, "help", "Shows the command list", "", "mccoin.help", false);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NonNull List<String> args) {
        sender.sendMessage(plugin.getCoinCMD().getCommandsDescription());
    }

    @Override
    public List<String> onTabComplete(@NonNull  CommandSender sender, @NonNull  List<String> args) {
        return null;
    }
}

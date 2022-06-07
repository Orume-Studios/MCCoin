package id.orume.mccoin.commands;

import id.orume.mccoin.MCCoin;
import id.orume.mccoin.MCCoinCMD;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HelpSubCmd extends SubCommand {

    public HelpSubCmd(MCCoin plugin) {
        super(plugin, "help", "Shows the command list", "", "mccoin.help", false);
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull List<String> args) {
        sender.sendMessage(MCCoinCMD.commandsDescription);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull List<String> args) {
        return null;
    }
}

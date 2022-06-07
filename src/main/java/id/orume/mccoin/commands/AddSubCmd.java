package id.orume.mccoin.commands;

import id.orume.mccoin.MCCoin;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddSubCmd extends SubCommand {
    public AddSubCmd(MCCoin plugin) {
        super(plugin, "add", "Add coins to a player", "", "mccoin.add", false);
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull List<String> args) {

    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull List<String> args) {
        return null;
    }
}

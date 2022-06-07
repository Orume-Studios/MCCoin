package id.orume.mccoin;

import id.orume.mccoin.commands.AddSubCmd;
import id.orume.mccoin.commands.HelpSubCmd;
import id.orume.mccoin.commands.SubCommand;
import id.orume.mccoin.utils.Lang;
import id.orume.mccoin.utils.Utils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MCCoinCMD implements CommandExecutor, TabExecutor {
    private final List<String> subCmdNameList = new ArrayList<>();
    private final List<SubCommand> subCommandList = new ArrayList<>();
    public static String commandsDescription = "";

    public MCCoinCMD(MCCoin plugin) {
        PluginCommand pluginCommand = plugin.getCommand("mccoin");
        if(pluginCommand == null) {
            Utils.log("Failed to register command");
            return;
        }

        pluginCommand.setExecutor(this);
        this.subCommandList.addAll(
                Arrays.asList(
                    new HelpSubCmd(plugin), new AddSubCmd(plugin)
                )
        );

        commandsDescription += Utils.colorize("&7&m----------------------------\n" +
                "&6&lMCCoin Commands\n" +
                "&7&m----------------------------\n" +
                "&7/mccoin help &7- &fShows the command list\n" +
                "&7/mccoin add &7- &fAdd coins to a player\n");

        this.subCommandList.forEach(subCmd -> {
            if(subCmd.getUsage().isEmpty()) {
                commandsDescription += Utils.colorize("&7/mccoin " + subCmd.getName() + " &7- &f" + subCmd.getDescription() + "\n");
            } else {
                commandsDescription += Utils.colorize("&7/mccoin " + subCmd.getName() + " " + subCmd.getUsage() + " &7- &f" + subCmd.getDescription() + "\n");
            }

            this.subCmdNameList.add(subCmd.getName());
        });

        commandsDescription += Utils.colorize("&7&m----------------------------");
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        if(args.length == 0) {
            commandSender.sendMessage(commandsDescription);
            return true;
        }

        SubCommand subCommand = this.subCommandList.stream().filter(subCmd -> subCmd.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
        if(subCommand == null) {
            commandSender.sendMessage(Lang.NO_SUBCOMMAND_FOUND.getStringValue().replace("%subcommand%", args[0]));
            return true;
        }

        if(!subCommand.getPermission().isEmpty() && !commandSender.hasPermission(subCommand.getPermission())) {
            commandSender.sendMessage(Lang.NO_PERMISSION_COMMAND.getStringValue());
            return true;
        }

        if(subCommand.isOnlyPlayer() && !(commandSender instanceof Player)) {
            commandSender.sendMessage(Lang.PLAYER_ONLY_COMMAND.getStringValue());
            return true;
        }

        subCommand.execute(commandSender, new ArrayList<>(Arrays.asList(args)));

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        if(args.length == 1) {
            return subCmdNameList;
        } else if(args.length > 1) {
            SubCommand subCommand = this.subCommandList.stream().filter(sc -> sc.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
            if(subCommand != null) {
                return subCommand.onTabComplete(commandSender, Arrays.asList(args));
            }
        }

        return null;
    }


}

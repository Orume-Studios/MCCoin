package id.orume.mccoin;

import id.orume.mccoin.commands.*;
import id.orume.mccoin.utils.Lang;
import id.orume.mccoin.utils.Utils;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoinCMD implements CommandExecutor, TabExecutor {
    private final List<String> subCmdNameList = new ArrayList<>();
    private final List<SubCommand> subCommandList = new ArrayList<>();
    private final MCCoin plugin;
    @Getter private String commandsDescription = "";

    public CoinCMD(MCCoin plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        PluginCommand pluginCommand = plugin.getCommand("mccoin");
        if(pluginCommand == null) {
            Utils.log("Failed to register command");
            return;
        }

        pluginCommand.setExecutor(this);
        this.subCommandList.addAll(
                Arrays.asList(
                        new HelpSubCmd(plugin), new AddSubCmd(plugin), new CreateSubCmd(plugin), new DeleteSubCmd(plugin), new ListSubCmd(plugin)
                )
        );

        this.commandsDescription += Utils.colorize("&7&m----------------------------\n" +
                "&6&lMCCoin Commands\n" +
                "&7&m----------------------------\n"
        );

        this.subCommandList.forEach(subCmd -> {
            if(subCmd.getUsage().isEmpty()) {
                this.commandsDescription += Utils.colorize("&7/mccoin " + subCmd.getName() + " &7- &f" + subCmd.getDescription() + "\n");
            } else {
                this.commandsDescription += Utils.colorize("&7/mccoin " + subCmd.getName() + " " + subCmd.getUsage() + " &7- &f" + subCmd.getDescription() + "\n");
            }

            this.subCmdNameList.add(subCmd.getName());
        });

        this.commandsDescription += Utils.colorize("&7&m----------------------------");
    }


    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, String[] args) {
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

        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        arguments.remove(0);

        subCommand.execute(commandSender, arguments);

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, String[] args) {
        if(args.length == 1) {
            return subCmdNameList;
        } else if(args.length > 1) {
            SubCommand subCommand = this.subCommandList.stream().filter(sc -> sc.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
            if(subCommand != null) {
                List<String> arguments = new ArrayList<>(Arrays.asList(args));
                arguments.remove(0);

                return subCommand.onTabComplete(commandSender, arguments);
            }
        }

        return null;
    }


}

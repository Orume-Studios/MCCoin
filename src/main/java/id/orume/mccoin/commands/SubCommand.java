package id.orume.mccoin.commands;

import id.orume.mccoin.MCCoin;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {
    protected final MCCoin plugin;
    @Getter private final String name;
    @Getter private final String description;
    @Getter private final String usage;
    @Getter private final String permission;
    @Getter private final boolean onlyPlayer;

    public SubCommand(MCCoin plugin, String name, String description, String usage, String permission, boolean onlyPlayer) {
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.onlyPlayer = onlyPlayer;
    }

    public abstract void execute(@NonNull CommandSender sender, @NonNull List<String> args);
    public abstract List<String> onTabComplete(@NonNull CommandSender sender, @NonNull List<String> args);

}

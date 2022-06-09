package id.orume.mccoin.utils;


import lombok.Getter;

public enum Lang {
    NO_PERMISSION_COMMAND(Utils.colorize("&cYou don't have enough permission to execute this command!")),
    NO_SUBCOMMAND_FOUND(Utils.colorize("&cThere is no subcommand found with name %subcommand%")),
    MISSING_ARGUMENTS(Utils.colorize("&cMissing arguments!")),
    INVALID_ARGUMENT(Utils.colorize("&cInvalid argument!")),
    PLAYER_ONLY_COMMAND(Utils.colorize("&cThis command is only executable for players!"));

    @Getter private final String stringValue;
    Lang(String value) {
        this.stringValue = value;
    }
}

package id.orume.mccoin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Utils {
    public static boolean debugValue;
    public static String prefix = colorize("&d[&6MCCoin&d]");
    public static void log(Object ...object) {
        StringBuilder objlog = new StringBuilder();
        for(Object o : object) {
            objlog.append(o).append(" ");
        }

        Bukkit.getLogger().info(prefix + " " + objlog);

    }

    public static void debug(Object ...object) {
        if(!debugValue) return;

        StringBuilder objlog = new StringBuilder();
        for(Object o : object) {
            objlog.append(o).append(" ");
        }

        Bukkit.getLogger().info(prefix + " " + objlog);

    }

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}

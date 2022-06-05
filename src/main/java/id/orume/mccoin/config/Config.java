package id.orume.mccoin.config;

import id.orume.mccoin.MCCoin;
import id.orume.mccoin.Utils;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class Config {
    protected final MCCoin plugin;
    protected final File file;
    protected YamlConfiguration yamlConf = new YamlConfiguration();
    @Getter private final String configName;

    public Config(MCCoin plugin, String configName) {
        this.plugin = plugin;
        this.configName = configName;
        this.file = new File(plugin.getDataFolder(), configName);

        this.reload();
    }

    protected void load() {
        Utils.log("Loading config " + configName);
        try {
            if(!file.exists()) {
                boolean result = file.getParentFile().mkdir();
                if(result) {
                    Utils.log("Created config folder");
                } else {
                    Utils.log("Failed to create config folder");
                }

                plugin.saveResource(configName, false);
            }

            yamlConf.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Utils.log("Loaded config " + configName);
    }

    abstract void reload();
}

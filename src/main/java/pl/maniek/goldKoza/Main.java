package pl.maniek.goldKoza;

import eu.okaeri.configs.*;
import eu.okaeri.configs.yaml.bukkit.*;
import eu.okaeri.configs.yaml.bukkit.serdes.*;
import java.io.*;
import java.util.*;

import lombok.*;
import org.bukkit.plugin.java.*;
import pl.maniek.goldKoza.commands.*;
import pl.maniek.goldKoza.configs.*;
import pl.maniek.goldKoza.listeners.*;

public final class Main extends JavaPlugin {

    private static PluginConfig config;
    private static MessageConfig message;
    @Getter
    private static KozaManager kozaManager;

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }

    public static PluginConfig getPluginConfig() {
        return config;
    }

    public static MessageConfig getMessageConfig() {
        return message;
    }

    public void onEnable() {
        this.loadConfig();
        kozaManager = new KozaManager();
        this.registerListeners();
        this.registerCommands();
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(kozaManager, this);
        this.getLogger().info("Listeners loaded!");
    }

    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("koza")).setExecutor(new KozaCmd());
        this.getLogger().info("Commands loaded!");
    }

    private void loadConfig() {
        try {
            config = ConfigManager.create(PluginConfig.class, it -> {
                it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
                it.withBindFile(new File(this.getDataFolder(), "Config.yml"));
                it.saveDefaults();
                it.load(true);
                this.getLogger().info("Config.yml loaded!");
            });
        } catch (Exception exception) {
            this.getLogger().warning("Error loading Config.yml" + exception);
            this.getPluginLoader().disablePlugin(this);
        }
        try {
            message = ConfigManager.create(MessageConfig.class, it -> {
                it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
                it.withBindFile(new File(this.getDataFolder(), "Message.yml"));
                it.saveDefaults();
                it.load(true);
                this.getLogger().info("Message.yml loaded!");
            });
        } catch (Exception exception) {
            this.getLogger().warning("Error loading Message.yml" + exception);
            this.getPluginLoader().disablePlugin(this);
        }
    }

    public void onDisable() {
    }
}


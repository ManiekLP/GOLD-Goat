package pl.maniek.goldKoza.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.maniek.goldKoza.Main;
import pl.maniek.goldKoza.configs.MessageConfig;
import pl.maniek.goldKoza.configs.PluginConfig;
import pl.maniek.goldKoza.listeners.KozaManager;
import pl.maniek.goldKoza.utils.ChatUtil;
import pl.maniek.goldKoza.utils.TimeUtil;

public class KozaCmd
        implements TabExecutor {
    private final PluginConfig config = Main.getPluginConfig();
    private final MessageConfig message = Main.getMessageConfig();
    private final KozaManager kozaManager = Main.getKozaManager();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("gold.koza.admin")) {
            sender.sendMessage(ChatUtil.fixColor(message.noPermission));
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatUtil.fixColor(message.usage.replace("{CMD}", cmd.getName())));
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "reload": {
                this.reloadConfig(sender);
                return true;
            }
            case "setspawn": {
                if (sender instanceof Player player) {
                    config.setKozaSpawn(player.getLocation());
                    config.save();
                    player.sendMessage(ChatUtil.fixColor(message.spawnSetSucces));
                } else {
                    sender.sendMessage(ChatUtil.fixColor(message.consoleUseError));
                }
                return true;
            }
            case "spawn": {
                if (this.config.getKozaSpawn() == null) {
                    sender.sendMessage(ChatUtil.fixColor(this.message.notSetSpawn));
                } else if (this.kozaManager.isKozaSpawned()) {
                    sender.sendMessage(ChatUtil.fixColor(message.spawnError));
                } else {
                    this.kozaManager.spawnKoza();
                    sender.sendMessage(ChatUtil.fixColor(message.spawnSucces));
                }
                return true;
            }
            case "kill": {
                if (this.kozaManager.isKozaSpawned()) {
                    this.kozaManager.killKoze();
                    sender.sendMessage(ChatUtil.fixColor(message.killSucces));
                } else {
                    sender.sendMessage(ChatUtil.fixColor(message.killNoTarget));
                }
                return true;
            }
        }
        sender.sendMessage(ChatUtil.fixColor(this.message.usage.replace("{CMD}", cmd.getName())));
        return false;
    }

    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if (sender.hasPermission("gold.koza.admin") && args.length == 1) {
            completions.add("reload");
            completions.add("setspawn");
            completions.add("spawn");
            completions.add("kill");
        }
        return completions;
    }

    private void reloadConfig(CommandSender sender) {
        try {
            long start = System.currentTimeMillis();
            this.config.load();
            this.message.load();
            sender.sendMessage(ChatUtil.fixColor(message.reloaded + TimeUtil.convertMills(System.currentTimeMillis() - start)));
        } catch (Exception e) {
            sender.sendMessage(ChatUtil.fixColor(message.reloadError + e.getMessage()));
        }
    }
}

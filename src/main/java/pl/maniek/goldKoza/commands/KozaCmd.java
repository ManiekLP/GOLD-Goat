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
            sender.sendMessage(ChatUtil.fixColor(this.message.noPermission));
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatUtil.fixColor(this.message.usage.replace("{CMD}", cmd.getName())));
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "reload": {
                this.reloadConfig(sender);
                return true;
            }
            case "setspawn": {
                if (sender instanceof Player) {
                    Player player = (Player)sender;
                    this.config.setKozaSpawn(player.getLocation());
                    this.config.save();
                    player.sendMessage(ChatUtil.fixColor(this.message.SpawnSetSucces));
                } else {
                    sender.sendMessage(ChatUtil.fixColor("&cNie mo\u017cesz u\u017cy\u0107 tej komendy z poziomu konsoli!"));
                }
                return true;
            }
            case "spawn": {
                if (this.config.getKozaSpawn() == null) {
                    sender.sendMessage(ChatUtil.fixColor(this.message.notSetSpawn));
                } else if (this.kozaManager.isKozaSpawned()) {
                    sender.sendMessage(ChatUtil.fixColor("&cKoza jest ju\u017c zrespiona!"));
                } else {
                    this.kozaManager.spawnKoza();
                    sender.sendMessage(ChatUtil.fixColor("&aPomy\u015blnie zrespi\u0142e\u015b koz\u0119!"));
                }
                return true;
            }
            case "kill": {
                if (this.kozaManager.isKozaSpawned()) {
                    this.kozaManager.killKoze();
                    sender.sendMessage(ChatUtil.fixColor("&aPomy\u015blnie zabi\u0142e\u015b wszystkie kozy!"));
                } else {
                    sender.sendMessage(ChatUtil.fixColor("&cNie ma zrespionych k\u00f3z do zabicia."));
                }
                return true;
            }
        }
        sender.sendMessage(ChatUtil.fixColor(this.message.usage.replace("{CMD}", cmd.getName())));
        return false;
    }

    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> completions = new ArrayList<String>();
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
            sender.sendMessage(ChatUtil.fixColor("&aKonfiguracja pluginu zosta\u0142a pomy\u015blnie za\u0142adowana! " + TimeUtil.convertMills(System.currentTimeMillis() - start)));
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(ChatUtil.fixColor("&cWyst\u0105pi\u0142 b\u0142\u0105d podczas wczytywania konfiguracji pluginu!"));
        }
    }
}

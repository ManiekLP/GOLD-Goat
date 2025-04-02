package pl.maniek.goldKoza.listeners;

import java.util.*;

import me.neznamy.tab.api.*;
import me.neznamy.tab.api.placeholder.*;
import org.bukkit.*;
import org.bukkit.boss.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.util.Vector;
import pl.maniek.goldKoza.*;
import pl.maniek.goldKoza.configs.*;
import pl.maniek.goldKoza.utils.*;

public class KozaManager implements Listener {
    private final PluginConfig config;
    private int actualHP;
    private Goat kozaList;
    private Map<UUID, Integer> damageTracker = new HashMap<>();
    private BossBar bossBars;
    private Integer hitCounter = 0;

    public KozaManager() {
        this.config = Main.getPluginConfig();
    }

    public void spawnKoza() {
        Location location = this.config.getKozaSpawn();
        int HP = this.config.getKozaHP();
        String kozaName = ChatUtil.fixColor(this.config.getKozaName());

        Goat koza = Objects.requireNonNull(location.getWorld()).spawn(location, Goat.class);
        koza.setCustomName(kozaName);
        koza.setCustomNameVisible(true);
        this.actualHP = HP;

        BarColor barColor = this.config.getBossBarConfig().getColor();
        BarStyle barStyle = this.config.bossBarConfig.getStyle();
        String barName = this.config.bossBarConfig.getText().replace("{HP}", String.valueOf(HP));
        this.registerCustomPlaceholders();
        BossBar bossBar = Bukkit.createBossBar(ChatUtil.fixColor(barName), barColor, barStyle);
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
        this.kozaList = koza;
        this.damageTracker = new HashMap<>();
        this.bossBars = bossBar;
        this.hitCounter = 0;
    }

    public void killKoze() {
        if (this.kozaList != null && !this.kozaList.isDead()) {
            this.kozaList.remove();
        }
        if (this.bossBars != null) {
            this.bossBars.removeAll();
        }
        this.kozaList = null;
        this.damageTracker = new HashMap<>();
        this.bossBars = null;
        this.hitCounter = 0;
    }

    @EventHandler
    public void onShootKoza(ProjectileHitEvent event) {
        Entity entity = event.getHitEntity();
        if (entity instanceof Goat && entity.getCustomName() != null && this.kozaList != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onKozaDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Goat koza) {
            if (entity.getCustomName() != null && this.kozaList != null) {
                Entity entity2 = event.getDamager();
                if (entity2 instanceof Player player) {
                    --this.actualHP;
                    event.setDamage(0.0);
                    this.damageTracker.put(player.getUniqueId(), this.damageTracker.getOrDefault(player.getUniqueId(), 0) + 1);
                    BossBar bossBar = this.bossBars;
                    if (bossBar != null) {
                        bossBar.setProgress((double)this.actualHP / (double)this.config.getKozaHP());
                        bossBar.setTitle(ChatUtil.fixColor(this.config.bossBarConfig.getText().replace("{HP}", String.valueOf(this.actualHP)).replace("{MAX_HP}", String.valueOf(this.config.getKozaHP()))));
                    }
                    this.hitCounter = this.hitCounter + 1;
                    if (this.hitCounter % this.config.odrzutCzestotliwosc == 0) {
                        this.knockBackNearbyPlayers(koza);
                    }
                    if (this.actualHP <= 0) {
                        this.rewardTopDamager(this.damageTracker);
                        koza.remove();
                        this.kozaList = null;
                        if (bossBar != null) {
                            bossBar.removeAll();
                            this.bossBars = null;
                        }
                        this.damageTracker.clear();
                        this.hitCounter = 0;
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    private void rewardTopDamager(Map<UUID, Integer> damageMap) {
        ArrayList<Map.Entry<UUID, Integer>> sortedDamagers = new ArrayList<>(damageMap.entrySet());
        sortedDamagers.sort(Map.Entry.<UUID, Integer>comparingByValue().reversed());
        int maxRewards = this.config.kozaRewards.size();
        for (int i = 0; i < maxRewards && i < sortedDamagers.size(); ++i) {
            UUID playerId = sortedDamagers.get(i).getKey();
            Player player = Bukkit.getPlayer(playerId);
            if (player == null || !this.config.kozaRewards.containsKey(i + 1)) continue;
            List<String> rewards = this.config.kozaRewards.get(i + 1);
            for (String command : rewards) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{nick}", player.getName()));
            }
        }
    }

    private void knockBackNearbyPlayers(Goat koza) {
        koza.getWorld().getPlayers().stream().filter(p -> p.getLocation().distance(koza.getLocation()) < (double)this.config.odrzutZasieg).forEach(p -> {
            Vector direction = p.getLocation().toVector().subtract(koza.getLocation().toVector()).normalize();
            Vector velocity = direction.multiply(this.config.odrzutSila);
            velocity.setY(velocity.getY() + this.config.odrzutY);
            p.setVelocity(velocity);
        });
    }

    private void registerCustomPlaceholders() {
        PlaceholderManager placeholderManager = TabAPI.getInstance().getPlaceholderManager();
        placeholderManager.registerServerPlaceholder("%GOLD-Koza_boss-spawned%", 1000, () -> this.isKozaSpawned() ? "1" : "0");
        placeholderManager.registerServerPlaceholder("%GOLD-Koza_boss-name%", 1000, () -> this.kozaList != null && this.kozaList.getCustomName() != null ? this.kozaList.getCustomName() : "-");
        placeholderManager.registerServerPlaceholder("%GOLD-Koza_boss-location%", 1000, () -> this.kozaList != null ? this.kozaList.getLocation().getBlockX() + ", " + this.kozaList.getLocation().getBlockZ() : "-");
        int i = 1;
        while (i <= 5) {
            int position = i++;
            placeholderManager.registerServerPlaceholder("%GOLD-Koza_boss-damage-top-" + position + "%", 1000, () -> {
                ArrayList<Map.Entry<UUID, Integer>> sortedDamagers = new ArrayList<>(this.damageTracker.entrySet());
                sortedDamagers.sort(Map.Entry.<UUID, Integer>comparingByValue().reversed());
                if (position <= sortedDamagers.size()) {
                    UUID playerId = sortedDamagers.get(position - 1).getKey();
                    int damage = sortedDamagers.get(position - 1).getValue();
                    Player topPlayer = Bukkit.getPlayer(playerId);
                    return topPlayer != null ? topPlayer.getName() + ": " + damage : "-";
                }
                return "-";
            });
        }
    }

    public boolean isKozaSpawned() {
        return this.kozaList != null;
    }
}
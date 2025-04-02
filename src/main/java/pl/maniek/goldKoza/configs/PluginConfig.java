package pl.maniek.goldKoza.configs;

import eu.okaeri.configs.*;
import eu.okaeri.configs.annotation.*;
import java.util.*;

import lombok.*;
import org.bukkit.*;
import org.bukkit.boss.*;
import pl.maniek.goldKoza.utils.config.*;
import pl.maniek.goldKoza.utils.config.message.*;

@Getter
@Setter
@Configuration(child = "Config.yml")
@Headers({ @Header({ "## GOLD-Goat (Main-Config) ##" }), @Header({ "# W razie błędów napisz do nas na discordzie: DC.GOLD-PLUGINS.PL" }) })
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment(value={"Nagrody za topki", "numer topki: komendy"})
    public Map<Integer, List<String>> kozaRewards;
    @Comment(value={"HP kozy"})
    public int kozaHP;
    @Comment(value={"Miejsce Spawnu"})
    public Location kozaSpawn;
    @Comment(value={"Nazwa kozy"})
    public String kozaName;
    @Comment(value={"BossBar"})
    public BossBarConfig bossBarConfig;
    @Comment(value={"Ustawienia odrzucenia!", "Podrzucenie"})
    public double odrzutY;
    @Comment(value={"Siła odrzutu"})
    public int odrzutSila;
    @Comment(value={"Zasięg pobierania graczy"})
    public int odrzutZasieg;
    @Comment(value={"Co ile uderzeń (co ile uderzeń)"})
    public int odrzutCzestotliwosc;

    public PluginConfig() {
        HashMap<Integer, List<String>> kozaReward = new HashMap<>();
        kozaReward.put(1, List.of("give {nick} diamond 3"));
        kozaReward.put(2, List.of("give {nick} diamond 2"));
        kozaReward.put(3, List.of("give {nick} diamond 1"));
        this.kozaRewards = kozaReward;
        this.kozaHP = 500;
        this.kozaSpawn = null;
        this.kozaName = "&bKOZA";
        this.bossBarConfig = new BossBarConfig(true, "&aŻycie lamy {HP}", BarColor.GREEN, BarStyle.SOLID, 0);
        this.odrzutY = 2.0;
        this.odrzutSila = 5;
        this.odrzutZasieg = 5;
        this.odrzutCzestotliwosc = 5;
    }
}

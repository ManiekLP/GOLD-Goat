package pl.maniek.goldKoza.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import pl.maniek.goldKoza.utils.config.Configuration;
import pl.maniek.goldKoza.utils.config.message.BossBarConfig;

@Getter
@Setter
@Configuration(child="Config.yml")
@Headers(value={@Header(value={"## GOLD-KOZA (Main-Config) ##"}), @Header(value={"# W razie b\u0142\u0119d\u00f3w napisz do nas na discordzie: DC.GOLD-PLUGINS.PL"})})
@Names(strategy=NameStrategy.HYPHEN_CASE, modifier=NameModifier.TO_LOWER_CASE)
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
    @Comment(value={"Si\u0142a odrzutu"})
    public int odrzutSila;
    @Comment(value={"Zasi\u0119g pobierania graczy"})
    public int odrzutZasieg;
    @Comment(value={"Co ile uderze\u0144 (co ile uderze\u0144)"})
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
        this.bossBarConfig = new BossBarConfig(true, "&a\u017bycie lamy {HP}", BarColor.GREEN, BarStyle.SOLID, 0);
        this.odrzutY = 2.0;
        this.odrzutSila = 5;
        this.odrzutZasieg = 5;
        this.odrzutCzestotliwosc = 5;
    }
}

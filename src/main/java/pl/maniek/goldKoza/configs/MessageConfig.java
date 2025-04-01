package pl.maniek.goldKoza.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Getter;
import lombok.Setter;
import pl.maniek.goldKoza.utils.config.Configuration;

@Getter
@Setter
@Configuration(child="Message.yml")
@Headers(value={@Header(value={"## GOLD-KOZA (Message-Config) ##"}), @Header(value={"# W razie b\u0142\u0119d\u00f3w napisz do nas na discordzie: DC.GOLD-PLUGINS.PL"})})
@Names(strategy=NameStrategy.HYPHEN_CASE, modifier=NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public String notSetSpawn = "&cMiejsce spawnu kozy nie jest ustawione!";
    public String SpawnSetSucces = "&aPomy\u015blnie ustawiono spawn Kozy";
    public String usage = "&fPoprawne u\u017cycie: /{CMD} <reload/setspawn/spawn/kill>";
    @Comment(value={"", "Uprawnienia do u\u017cywania komend!"})
    public String noPermission = "&4Nie posiadasz uprawnien.";
    @Comment(value={"Reload message"})
    public String reloaded = "&aKonfiguracja pluginu zostala pomyslnie zaladowana!";
    public String reloadError = "&cWystapil blad podczas wczytywania konfiguracji pluginu!";
}


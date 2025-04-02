package pl.maniek.goldKoza.configs;

import eu.okaeri.configs.*;
import eu.okaeri.configs.annotation.*;
import lombok.*;
import pl.maniek.goldKoza.utils.config.*;

@Getter
@Configuration(child = "Message.yml")
@Headers({ @Header({ "## GOLD-Goat (Message-Config) ##" }), @Header({ "# W razie błędów napisz do nas na discordzie: DC.GOLD-PLUGINS.PL" }) })
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public String notSetSpawn = "&cMiejsce spawnu kozy nie jest ustawione!";
    public String spawnSetSucces = "&aPomyślnie ustawiono spawn Kozy";
    public String usage = "&fPoprawne użycie: /{CMD} <reload/setspawn/spawn/kill>";
    @Comment(value={"", "Uprawnienia do używania komend!"})
    public String noPermission = "&4Nie posiadasz uprawnien.";
    @Comment(value={"Reload message"})
    public String reloaded = "&aKonfiguracja pluginu zostala pomyslnie zaladowana!";
    public String reloadError = "&cWystapil blad podczas wczytywania konfiguracji pluginu!";
}


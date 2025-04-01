package pl.maniek.goldKoza.utils.config.message;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

@Getter
@Setter
@NoArgsConstructor
public class BossBarConfig extends OkaeriConfig {

    @Comment("Czy bossbar ma być włączony")
    @CustomKey("enable")
    private boolean enable = true;

    @Comment("Text wyświetlany na bossbarze")
    @CustomKey("text")
    private String text = "Twój bossbar";

    @Comment("Kolor bossbara (np. BLUE, RED, GREEN)")
    @CustomKey("color")
    private BarColor color = BarColor.BLUE;

    @Comment("Styl bossbara (np. SOLID, SEGMENTED_10, SEGMENTED_20)")
    @CustomKey("style")
    private BarStyle style = BarStyle.SOLID;

    @Comment("Czas trwania bossbara w sekundach")
    @CustomKey("time")
    private int time = 3;

    public BossBarConfig(boolean enable, String text, BarColor color, BarStyle style, int time) {
        this.enable = enable;
        this.text = text;
        this.color = color;
        this.style = style;
        this.time = time;
    }
}

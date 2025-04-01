package pl.maniek.goldKoza.utils.config.material;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

@Getter
@Setter
@NoArgsConstructor
public class MaterialDropConfig extends OkaeriConfig {

    @Comment("Materiał, który gracz może zebrać")
    @CustomKey("material")
    private Material material = Material.WHEAT;

    @Comment("Ilość pieniędzy, którą można zdobyć za materiał")
    @CustomKey("currency-amount")
    private double currencyAmount = 0.0;

    @Comment("Szansa na zdobycie pieniędzy")
    @CustomKey("chance")
    private double chance = 1.0;

    @Comment("Wymagany poziom gracza do zdobycia nagrody")
    @CustomKey("required-level")
    private int requiredLevel = 0;

    @Comment("Minimalny XP za skopanie!")
    @CustomKey("minimal-xp")
    private  int minimalXP = 0;

    @Comment("Maxymalny XP za skopanie!")
    @CustomKey("maximum-xp")
    private int maximumXP = 1;

    public MaterialDropConfig(Material material, double currencyAmount, double chance, int requiredLevel, int minimalXP, int maximumXP) {
        this.material = material;
        this.currencyAmount = currencyAmount;
        this.chance = chance;
        this.requiredLevel = requiredLevel;
        this.minimalXP = minimalXP;
        this.maximumXP = maximumXP;
    }
}

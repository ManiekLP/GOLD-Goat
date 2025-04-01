package pl.maniek.goldKoza.utils.config.material;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@NoArgsConstructor
public class MaterialSellConfig extends OkaeriConfig {

    @Comment("Materiał")
    @CustomKey("material")
    private ItemStack item = new ItemStack(Material.WHEAT);

    @Comment("Ilość pieniędzy")
    @CustomKey("cost")
    private double cost = 0.0;

    @Comment("Wymagana ilość")
    @CustomKey("amount")
    private int amount = 1;

    public MaterialSellConfig(ItemStack item, double cost, int amount) {
        this.item = item;
        this.cost = cost;
        this.amount = amount;
    }
}

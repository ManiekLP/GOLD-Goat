package pl.maniek.goldKoza.utils.config.menu;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuItemConfig extends OkaeriConfig {

    @CustomKey("material")
    private Material material;
    @CustomKey("displayname")
    private String displayname;
    @CustomKey("lore")
    private List<String> lore;
    @CustomKey("slots")
    private List<Integer> slots;

    public MenuItemConfig(final Material material, final String displayname, final List<String> lore, final List<Integer> slots) {
        this.material = material;
        this.displayname = displayname;
        this.lore = lore;
        this.slots = slots;
    }
}

package pl.maniek.goldKoza.utils.config.menu;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuConfig extends OkaeriConfig {

    @Comment("Rozmiar GUI")
    @CustomKey("gui-size")
    private int guisize;
    @Comment("Nazwa gui")
    @CustomKey("gui-title")
    private String guititle;
    @Comment("Tło")
    @CustomKey("background")
    private List<MenuItemConfig> background;

    public MenuConfig(final int guisize, final String guititle, final List<MenuItemConfig> background) {
        this.guisize = guisize;
        this.guititle = guititle;
        this.background = background;
    }
}

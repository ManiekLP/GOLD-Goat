package pl.maniek.goldKoza.utils.config.message;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TitleConfig extends OkaeriConfig {

    @Comment("Czy title ma być włączony")
    @CustomKey("enable")
    private boolean enable = true;

    @CustomKey("title")
    private String title = "";

    @CustomKey("subTitle")
    private String subTitle = "";

    public TitleConfig(boolean enable, String title, String subTitle) {
        this.enable = enable;
        this.title = title;
        this.subTitle = subTitle;
    }
}

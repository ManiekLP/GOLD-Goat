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
public class TextConfig extends OkaeriConfig {

    @Comment("Czy message ma być włączony")
    @CustomKey("enable")
    private boolean enable = true;

    @CustomKey("message")
    private String message = "";

    public TextConfig(boolean enable, String message) {
        this.enable = enable;
        this.message = message;
    }
}

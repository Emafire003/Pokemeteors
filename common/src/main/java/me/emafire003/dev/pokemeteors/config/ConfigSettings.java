package me.emafire003.dev.pokemeteors.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import me.emafire003.dev.pokemeteors.PlatformSpecificStuff;
import me.emafire003.dev.pokemeteors.PokemeteorsCommon;

public class ConfigSettings {

    public static ConfigClassHandler<ConfigSettings> HANDLER = ConfigClassHandler.createBuilder(ConfigSettings.class)
            .id(PokemeteorsCommon.getIdentifier("settings"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(PlatformSpecificStuff.getConfigPath().resolve(PokemeteorsCommon.MOD_ID+"_settings.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting) // not needed, pretty print by default
                    .setJson5(true)
                    .build())
            .build();

    @AutoGen(category = "settings", group = "settings_general")
    @Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
    @SerialEntry(comment = "Should pokémon spawned with the /pokespawn command be spawned with a meteor if applicable?")
    public boolean overridePokespawnCommand = true;

}

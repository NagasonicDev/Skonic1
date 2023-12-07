package ca.nagasonic.skonic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public final class Skonic extends JavaPlugin {

    private static Skonic instance;
    private static SkriptAddon addon;
    private static Logger logger;

    public static void info(String message) {
        logger.info(message);
    }

    public static Skonic getInstance() {
        return instance;
    }
    public SkriptAddon getAddonInstance() {
        return addon;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        addon = Skript.registerAddon(this);
        logger = this.getLogger();
        try {
            addon.loadClasses("ca.nagasonic.skonic");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Skonic.info("Skonic has been enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

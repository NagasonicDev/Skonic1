package ca.nagasonic.skonic.elements.util;

import ca.nagasonic.skonic.Skonic;
import ch.njol.skript.util.Version;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Consumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class UpdateChecker implements Listener {

    private static Version UPDATE_VERSION;

    public static void checkForUpdate(String pluginVersion) {
        Util.log("Checking for update...");
        getLatestReleaseVersion(version -> {
            Version plugVer = new Version(pluginVersion);
            Version curVer = new Version(version);
            if (curVer.compareTo(plugVer) <= 0) {
                Util.logLoading("&aPlugin is up to date!");
            } else {
                Util.logLoading("&cPlugin is not up to date!");
                Util.logLoading(" - Current version: &cv%s", pluginVersion);
                Util.logLoading(" - Available update: &av%s",version);
                Util.logLoading(" - Download available at: https://github.com/NagasonicDev/Skonic/releases");
                UPDATE_VERSION = curVer;
            }
        });
    }

    private static void getLatestReleaseVersion(final Consumer<String> consumer) {
        try {
            URL url = new URL("https://api.github.com/repos/NagasonicDev/Skonic/releases/latest");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
            String tag_name = jsonObject.get("tag_name").getAsString();
            consumer.accept(tag_name);
        } catch (IOException e) {
            e.printStackTrace();
            Util.logLoading("&cChecking for update failed!");
        }
    }

    private final Skonic plugin;

    public UpdateChecker(Skonic plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("skonic.update.check")) return;

        String currentVersion = this.plugin.getDescription().getVersion();
        CompletableFuture<Version> updateVersion = getUpdateVersion(currentVersion);

        Bukkit.getScheduler().runTaskLater(this.plugin, () -> updateVersion.thenApply(version -> {
            Util.sendColMsg(player, "&7[&9Skonic&7] update available: &a" + version);
            Util.sendColMsg(player, "&7[&9Skonic&7] download at &bhttps://github.com/ShaneBeee/SkBee/releases");
            return true;
        }), 30);
    }

    private CompletableFuture<Version> getUpdateVersion(String currentVersion) {
        CompletableFuture<Version> future = new CompletableFuture<>();
        if (UPDATE_VERSION != null) {
            future.complete(UPDATE_VERSION);
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> getLatestReleaseVersion(version -> {
                Version plugVer = new Version(currentVersion);
                Version curVer = new Version(version);
                if (curVer.compareTo(plugVer) <= 0) {
                    future.cancel(true);
                } else {
                    UPDATE_VERSION = curVer;
                    future.complete(UPDATE_VERSION);
                }
            }));
        }
        return future;
    }
}

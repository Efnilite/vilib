package dev.efnilite.vilib.util;

import org.bukkit.plugin.Plugin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateChecker {

    public static void check(Plugin plugin, int resourceId) {
        Task.create(plugin)
                .async()
                .execute(() -> {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("https://api.spiget.org/v2/resources/%s/versions/latest".formatted(resourceId)))
                            .build();

                    plugin.getLogger().info("Checking for updates");

                    var tag = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                            .thenApply(HttpResponse::body)
                            .thenApply(UpdateChecker::parseLatestTag)
                            .join();

                    if (tag.isEmpty()) {
                        plugin.getLogger().info("Failed to check for updates");
                        return;
                    }

                    var version = plugin.getDescription().getVersion();
                    if (!version.equals(tag)) {
                        plugin.getLogger().info("A new version is available: %s -> %s".formatted(version, tag));
                        plugin.getLogger().info("Download at https://spigotmc.org/resources/%s".formatted(resourceId));
                    } else {
                        plugin.getLogger().info("No new version found");
                    }
                })
                .run();
    }

    private static String parseLatestTag(String responseBody) {
        Pattern pattern = Pattern.compile("\"name\": ?\"(.*?)\"");
        Matcher matcher = pattern.matcher(responseBody);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
}

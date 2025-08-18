
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Bukkey {
    private final String prefix = "§x§E§F§E§F§E§F[§x§E§F§E§F§E§9B§x§F§0§E§F§E§3u§x§F§0§E§F§D§Dk§x§F§1§E§F§D§8k§x§F§1§E§F§D§2e§x§F§1§E§F§C§Cy§x§F§2§E§F§C§6.§x§F§2§E§F§C§0j§x§F§3§E§F§B§Aa§x§F§3§E§F§B§4v§x§F§3§E§F§A§Ea§x§F§4§E§F§A§9] §x§F§5§E§F§9§D-§x§F§5§E§F§9§7>";
    // *-- Вы можете изменить prefix, остальные поля просим не трогать и не изменять! --*
    
    private final String licenseKey;
    private final String pluginId;
    private final Plugin plugin;
    private String ip;

    // *-- For fingerprints encryptions --*
    private static final char[] IP_ENCRYPT_MAP = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    private static final char[] IP_DECRYPT_MAP = new char[256];

    static {
        for (int i = 0; i < 10; i++) {
            IP_DECRYPT_MAP[IP_ENCRYPT_MAP[i]] = (char) ('0' + i);
        }
    }

    public Bukkey(Plugin plugin, String licenseKey, String pluginId, boolean isEnabled) {
        this.pluginId = pluginId;
        this.licenseKey = licenseKey;
        this.plugin = plugin;

        if (!isEnabled) {
            plugin.getLogger().info(prefix + "§e Проверка на лицензионный ключ выключена.");
            return;
        }

        this.ip = getPublicIP();
        if ("ERROR".equals(ip)) {
            plugin.getLogger().warning(prefix + "§c Не удалось получить IP, проверка невозможна.");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }

        // *-- Here we show a fingerprint --*
        plugin.getLogger().info(" ");
        plugin.getLogger().info(prefix);
        plugin.getLogger().info("§e┍ ");
        plugin.getLogger().info("§e│§r §fВаш finger-print:§e " + printFingerprint());
        plugin.getLogger().info("§e│§r §fСтатус:§e проверка лицензионного ключа...");
        plugin.getLogger().info("§e┕");
        plugin.getLogger().info(" ");

        new BukkitRunnable() {
            @Override
            public void run() {
                checkLicenseKey();
            }
        }.runTaskAsynchronously(plugin);
    }

    private String getPublicIP() {
        try {
            URL url = new URL("https://ifconfig.me/ip");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return reader.readLine().trim();
            } finally {
                connection.disconnect();
            }
        } catch (Exception e) {
            return "ERROR";
        }
    }

    private void checkLicenseKey() {
        HttpURLConnection connection = null;
        try {
            String requestBody = String.format(
                    "{\"ip\":\"%s\", \"key\":\"%s\", \"pluginId\":\"%s\"}",
                    this.ip, this.licenseKey, this.pluginId
            );

            URL url = new URL("http://127.0.0.1:2366/check/key");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int statusCode = connection.getResponseCode();
            InputStream inputStream = (statusCode >= 200 && statusCode < 300)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            StringBuilder responseBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
            }

            String response = responseBuilder.toString();

            if (response.contains("\"success\":true")) {
                plugin.getLogger().info(prefix + " §aЛицензия успешно проверена! Плагин активирован.");
            } else {
                String errorMessage = extractJsonField(response, "error");
                if (errorMessage == null || errorMessage.isEmpty()) {
                    errorMessage = "Неизвестная ошибка (код " + statusCode + ")";
                }
                plugin.getLogger().warning(prefix + "§c Ошибка лицензии: " + errorMessage);

                Bukkit.getScheduler().runTask(plugin, () ->
                        Bukkit.getPluginManager().disablePlugin(plugin)
                );
            }

        } catch (Exception e) {
            plugin.getLogger().severe(prefix + "§c Ошибка проверки лицензии: " + e.getMessage());
            Bukkit.getScheduler().runTask(plugin, () ->
                    Bukkit.getPluginManager().disablePlugin(plugin)
            );
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String extractJsonField(String json, String fieldName) {
        try {
            String search = "\"" + fieldName + "\":";
            int start = json.indexOf(search);
            if (start == -1) return null;

            start += search.length();
            while (start < json.length() && (json.charAt(start) == ' ' || json.charAt(start) == '\"')) {
                start++;
            }

            int end = json.indexOf("\"", start);
            if (end == -1) return json.substring(start).trim();

            return json.substring(start, end).trim();
        } catch (Exception e) {
            return null;
        }
    }

    private String printFingerprint() {
        StringBuilder result = new StringBuilder(ip.length());
        for (int i = 0; i < ip.length(); i++) {
            char c = ip.charAt(i);
            if (c >= '0' && c <= '9') {
                result.append(IP_ENCRYPT_MAP[c - '0']);
            } else if (c == '.') {
                result.append('-');
            } else {
                result.append(c);
            }
        }
        return result.toString() + ":" + pluginId;
    }

}


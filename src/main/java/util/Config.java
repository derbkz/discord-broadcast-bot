package util;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Config {
    public static List<DiscordBroadcast> broadcasts; // All broadcast methods as a list from the config
    public static List<String> bot_admins; // Is required for the examination of the admins
    public static String broadcast_channel = ""; // Is required to identify the channel for the broadcast
    public static String broadcast_guild = ""; // Is required for the group of all clients
    public static String bot_token = ""; // Is required to connect the DiscordBot
    public static String message_temp = ""; // Is required to temporarily store a message
    public static String message_title = ""; // Is required to set the title of a broadcast message

    public static void loadConfig() throws IOException {
        String contentConfig = readConfig();
        Gson gson = new Gson();
        ConfigStructure config = gson.fromJson(contentConfig, ConfigStructure.class);

        // The loaded settings must be loaded into the config
        broadcast_channel = config.broadcast_channel;
        broadcast_guild = config.broadcast_guild;
        bot_admins = config.bot_admins;
        broadcasts = config.broadcasts;
        bot_token = config.token;
    }

    private static String readConfig() throws IOException {
        InputStream is = new FileInputStream("bot-config.json");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();

        while (line != null) {
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        return sb.toString();
    }
}

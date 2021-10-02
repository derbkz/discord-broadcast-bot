package util;


import java.util.ArrayList;
import java.util.List;

public class ConfigStructure {
    public String token = "";
    public String broadcast_channel = "";
    public String broadcast_guild = "";
    public List<String> bot_admins = new ArrayList<String>();
    public List<DiscordBroadcast> broadcasts = new ArrayList<DiscordBroadcast>();

    public ConfigStructure(String token, String broadcast_channel, String broadcast_guild, List<String> bot_admins, List<DiscordBroadcast> broadcasts){
        this.token = token;
        this.broadcast_channel = broadcast_channel;
        this.broadcast_guild = broadcast_guild;
        this.bot_admins = bot_admins;
        this.broadcasts = broadcasts;
    }
}
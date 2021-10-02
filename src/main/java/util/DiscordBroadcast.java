package util;

import java.util.List;

public class DiscordBroadcast {
    public List<String> broadcast_target_channel; // List of target channels
    public String broadcast_emoji; // Emoji for the reaction
    public String broadcast_name; // Name of the broadcast

    public DiscordBroadcast(List<String> broadcast_target_channel, String broadcast_emoji, String broadcast_name){
        this.broadcast_target_channel = broadcast_target_channel;
        this.broadcast_emoji = broadcast_emoji;
        this.broadcast_name = broadcast_name;
    }

    public DiscordBroadcast(){ }
}
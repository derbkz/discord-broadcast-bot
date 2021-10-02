package listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import util.Config;
import util.DiscordBroadcast;

import java.awt.*;

public class BroadcastMessageReaction extends ListenerAdapter {
    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        DiscordBroadcast discord_broadcast = new DiscordBroadcast();
        MessageChannel messageChannel = event.getChannel();
        MessageReaction reaction = event.getReaction();
        Member member = event.getMember();

        // The bot's own message should be ignored here
        if (event.getJDA().getSelfUser().equals(member.getUser())) {
            return;
        }

        // The message should only be considered if it is a broadcast channel
        if (!messageChannel.getId().equals(Config.broadcast_channel)) {
            return;
        }

        // Only continue if the user is entered as an admin
        if (!Config.bot_admins.contains(member.getUser().getId())) {
            messageChannel.sendMessage(new EmbedBuilder()
                            .setColor(Color.yellow)
                            .setTitle("Not registered as admin")
                            .setDescription("You are not registered as an admin!")
                            .build())
                    .queue();
            return;
        }

        if (Config.message_temp.length() == 0) {
            messageChannel.sendMessage("No message could be found!").queue();
            messageChannel.deleteMessageById(event.getMessageId());
            return;
        }

        for (DiscordBroadcast item : Config.broadcasts) {
            // Check if the Reaction Emote is present
            if (item.broadcast_emoji.equals(event.getReactionEmote().getEmoji())) {
                discord_broadcast = item;
                // Write the message in the defined channels
                for (String channel_id : item.broadcast_target_channel) {
                    TextChannel textChannel = event.getGuild().getTextChannelById(channel_id);
                    textChannel.sendMessage(new EmbedBuilder()
                                    .setTitle(Config.message_title.length() == 0 ? "Notification" : Config.message_title)
                                    .setColor(Color.WHITE)
                                    .setDescription(Config.message_temp + " <@&"+ Config.broadcast_guild+">")
                                    .build())
                            .queue();
                }
            }
        }

        // Feedback to the user
        messageChannel
                .sendMessage(
                        new EmbedBuilder()
                                .setColor(Color.green)
                                .setTitle("Done with the execution")
                                .setDescription(getMessageDescription(discord_broadcast, event))
                                .setFooter(getMessageFooter(discord_broadcast, event))
                                .build())
                .queue();

        // Reset the buffer
        Config.message_temp = "";
        // The message for the query should be removed
        messageChannel.deleteMessageById(reaction.getMessageId()).queue();
    }

    private String getMessageFooter(DiscordBroadcast value, GuildMessageReactionAddEvent event) {
        StringBuilder builder = new StringBuilder();
        builder.append("Reaction: " + value.broadcast_name + "\r\n");
        builder.append("Number of channels: " + value.broadcast_target_channel.stream().count() + "\r\n");
        builder.append("The message was sent by " + event.getUser().getAsTag());
        return builder.toString();
    }

    private String getMessageDescription(DiscordBroadcast value, GuildMessageReactionAddEvent event) {
        StringBuilder builder = new StringBuilder();
        builder.append("You have sent this message:\r\n");
        builder.append(Config.message_temp);
        return builder.toString();
    }
}

package listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import util.Config;
import util.DiscordBroadcast;

import java.awt.*;

public class BroadcastMessage extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        MessageChannel messageChannel = event.getChannel();
        Message message = event.getMessage();
        Member member = event.getMember();

        // The bot's own message should be ignored here
        if (event.getJDA().getSelfUser().equals(member.getUser())) {
            return;
        }

        if (message.getContentDisplay().startsWith("!")) {
            return;
        }

        // The message should only be considered if it is a broadcast channel
        if (!messageChannel.getId().equals(Config.broadcast_channel)) {
            return;
        }

        // Only continue if the user is entered as an admin
        if (!Config.bot_admins.contains(message.getAuthor().getId())) {
            messageChannel
                    .sendMessage(
                            new EmbedBuilder()
                                    .setColor(Color.yellow)
                                    .setTitle("Not registered as admin")
                                    .setDescription("You are not registered as an admin!")
                                    .build())
                    .queue();
            return;
        }

        if (messageChannel.getId().equals(Config.broadcast_channel)) {
            message.delete().queue(); // The sent message should be removed

            // The footer text should be filled with the available emojis.
            StringBuilder footer = new StringBuilder();
            footer.append("Please select a reaction:\r\n");
            for (DiscordBroadcast item : Config.broadcasts) {
                footer.append("" + item.broadcast_emoji + " : " + item.broadcast_name + " \r\n");
            }

            // Send the message using the available emojis
            messageChannel
                    .sendMessage(
                            new EmbedBuilder()
                                    .setColor(Color.red)
                                    .setTitle("You want to send the following message:")
                                    .setDescription(
                                            "Title: "
                                                    + (Config.message_title.length() == 0
                                                    ? "Notification"
                                                    : Config.message_title)
                                                    + "\r\nMessage: "
                                                    + message.getContentDisplay())
                                    .setFooter(footer.toString())
                                    .build())
                    .queue(
                            msg -> {
                                for (DiscordBroadcast item : Config.broadcasts) {
                                    msg.addReaction(item.broadcast_emoji).queue();
                                }
                            });
            // Cache message
            Config.message_temp = message.getContentDisplay();
        }
    }
}

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
import util.DiscordBroadcastMessages;

import java.awt.*;

public class BroadcastMessage extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        MessageChannel messageChannel = event.getChannel();
        StringBuilder footer = new StringBuilder();
        Message message = event.getMessage();
        Member member = event.getMember();

        // The bot's own message should be ignored here
        // The message should only be considered if it is a broadcast channel
        if (event.getJDA().getSelfUser().equals(member.getUser()) |
                message.getContentDisplay().startsWith("!") |
                !messageChannel.getId().equals(Config.broadcast_channel)) {
            return;
        }

        // Only continue if the user is entered as an admin
        if (!Config.bot_admins.contains(message.getAuthor().getId())) {
            messageChannel.sendMessage(DiscordBroadcastMessages.getAuthorizationMessage(message.getAuthor().getAsTag())).queue();
            message.delete().queue();
            return;
        }

        // Check whether the config has been configured correctly
        if(Config.error_message.length() != 0){
            System.out.println(Config.error_message);
            return;
        }

        if (messageChannel.getId().equals(Config.broadcast_channel)) {
            message.delete().queue(); // The sent message should be removed
            // The footer text should be filled with the available emojis.
            footer.append("Please select a reaction:\r\n");
            for (DiscordBroadcast item : Config.broadcasts) {
                footer.append("" + item.broadcast_emoji + " : " + item.broadcast_name + " \r\n");
            }

            if(Config.message_temp.length() != 0){
                footer.append("\r\n❌ An older broadcast message has now been overwritten.\r\n" +
                              "🙇‍♂️Remember that you can only temporarily cache one message.");
            }

            // Send the message using the available emojis
            messageChannel
                    .sendMessage(
                            new EmbedBuilder()
                                    .setColor(Color.red)
                                    .setTitle("📝 You want to send the following message:")
                                    .setDescription("Title: " + (Config.message_title) + "\r\n" +
                                                    "Message: " + message.getContentDisplay() + "\r\n" +
                                                    "Image: " + (Config.file_path.length() == 0 ? "Not specified" : Config.file_path))
                                    .setFooter(footer.toString())
                                    .build())
                    .queue(
                            msg -> {
                                for (DiscordBroadcast item : Config.broadcasts) {
                                    msg.addReaction(item.broadcast_emoji).queue();
                                }
                                // Add the sent message to the list of active messages
                                Config.active_message = msg.getId();
                            });
            // Cache message
            Config.message_temp = message.getContentDisplay();
        }
    }
}

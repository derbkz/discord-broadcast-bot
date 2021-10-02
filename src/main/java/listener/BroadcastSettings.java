package listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import util.Config;

import java.awt.*;

public class BroadcastSettings extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        MessageChannel messageChannel = event.getChannel();
        Message message = event.getMessage();

        // The bot's own message should be ignored here
        if (event.getJDA().getSelfUser().equals(member.getUser())) {
            return;
        }

        if (!message.getContentDisplay().startsWith("!")) {
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

        if (message.getContentDisplay().startsWith("!title")) {
            Config.message_title = message.getContentDisplay().substring(6);
            messageChannel
                    .sendMessage(
                            new EmbedBuilder()
                                    .setColor(Color.yellow)
                                    .setTitle("Configuration changed")
                                    .setDescription("You have changed the title to " + Config.message_title)
                                    .build())
                    .queue();
        } else {
            messageChannel
                    .sendMessage(
                            new EmbedBuilder()
                                    .setColor(Color.yellow)
                                    .setTitle("Configuration failed")
                                    .setDescription("The command could not be found")
                                    .build())
                    .queue();
        }
    }
}

package listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import util.Config;
import util.DiscordBroadcastMessages;

import java.awt.*;
import java.util.Locale;

public class BroadcastSettings extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        MessageChannel messageChannel = event.getChannel();
        Message message = event.getMessage();

        // The bot's own message should be ignored here
        // The message should only be considered if it is a broadcast channel
        if (event.getJDA().getSelfUser().equals(member.getUser()) |
                !message.getContentDisplay().startsWith("!") |
                !messageChannel.getId().equals(Config.broadcast_channel)) {
            return;
        }

        // Only continue if the user is entered as an admin
        if (!Config.bot_admins.contains(message.getAuthor().getId())) {
            messageChannel.sendMessage(DiscordBroadcastMessages.getAuthorizationMessage(message.getAuthor().getName())).queue();
            message.delete().queue();
            return;
        }

        if(message.getContentDisplay().toLowerCase().startsWith("!title")){
            Config.message_title = message.getContentDisplay().substring(6);
            messageChannel.sendMessage(DiscordBroadcastMessages.getConfigurationChangedMessage(message.getAuthor().getAsTag())).queue();
            message.delete().queue();
            return;
        }

        switch (message.getContentDisplay()){
            case "!title":{
                Config.message_title = message.getContentDisplay().substring(6);
                break;
            }
            case "!help":{
                messageChannel.sendMessage(DiscordBroadcastMessages.getHelpMessage()).queue();
                break;
            }
            default:{
                messageChannel.sendMessage(DiscordBroadcastMessages.getCommandNotFound()).queue();
                break;
            }
        }
        // Delete the sent message from the user
        message.delete().queue();
    }
}

package util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class DiscordBroadcastMessages {

    public static MessageEmbed getAuthorizationMessage(String clientName){
        return new EmbedBuilder()
                .setColor(Color.yellow)
                .setTitle("👮‍♂️ ️Not registered as admin")
                .setDescription("You are not registered as an admin!")
                .setFooter("Tried execution of " + clientName + ".")
                .build();
    }

    public static MessageEmbed getConfigurationChangedMessage(String clientName){
        return new EmbedBuilder()
                .setColor(Color.yellow)
                .setTitle("⚙ Configuration changed")
                .setDescription("You have changed the title to **" + Config.message_title + "**.")
                .setFooter("Executed by " + clientName + ".")
                .build();
    }

    public static MessageEmbed getCommandNotFound(){
        return new EmbedBuilder()
                .setColor(Color.yellow)
                .setTitle("🦉 Command not found!")
                .setDescription("The command you entered could not be found.\r\nHave all commands listed with **!help**")
                .build();
    }

    public static MessageEmbed getCannotSentMessage(){
        return new EmbedBuilder()
                .setColor(Color.yellow)
                .setTitle("😓 Cannot send message ")
                .setDescription("The following points could be the reason: \r\n" +
                        "- The message was generated before or after restarting the bot.\r\n" +
                        "- You are not responding to a valid message.")
                .build();
    }

    public static MessageEmbed getNoMessageFoundMessage(){
        return new EmbedBuilder()
                .setColor(Color.yellow)
                .setTitle("🤔 No message found")
                .setDescription("You must first define a message in order to send it.")
                .build();
    }

    public static MessageEmbed getHelpMessage(){
        return new EmbedBuilder()
                .setColor(Color.blue)
                .setTitle("📌 Assistance")
                .setDescription("The following commands are available to you:\r\n" +
                                "!title [name] -> Give your message a title of your own\r\n" +
                                "!help -> Get an overview of all commands")
                .build();
    }
}

package core;

import listener.BroadcastMessage;
import listener.BroadcastMessageReaction;
import listener.BroadcastSettings;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import util.Config;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main {
    public static JDA jda;

    public static void main(String[] Args) throws LoginException, IOException {
        Config.loadConfig();
        JDABuilder builder =
                (JDABuilder)
                        JDABuilder.createDefault(Config.bot_token)
                                .addEventListeners(new BroadcastMessage())
                                .addEventListeners(new BroadcastMessageReaction())
                                .addEventListeners(new BroadcastSettings());

        // Initialize the JDA
        jda = builder.build();
    }
}

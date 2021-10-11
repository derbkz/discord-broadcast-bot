# broadcast-discord-bot
Define as many channels as you want on a Discord server in the config to send a message to all desired channels. So you can easily prepare a config once and then always output a message as a broadcast

## Configuration of the bot 
The token for the bot can be found on the following website: https://discord.com/developers/applications. 

You can define bot admins who are only allowed to execute the commands.

Broadcast guild will be the group that is tagged in the broadcast.

Broadcast channel is the group where you can define a broadcast.

```json
{
  "token": "",
  "bot_admins": ["278785550935392257", "470715613669621770"],
  "broadcast_channel": "893261098076229684",
  "broadcast_guild": "893273507054645280",
  "broadcasts": [
    {
      "broadcast_name": "All channels",
      "broadcast_emoji": "💎",
      "broadcast_target_channel": ["893264144629919744", "893264169271443467", "893264274166788187"]
    },
    {
      "broadcast_name": "Only two",
      "broadcast_emoji": "😍",
      "broadcast_target_channel": ["893264144629919744", "893264169271443467"]
    }
  ]
}
```

## Changelog
Version 1.0 (20211001)
+ The bot was created with the basic functions

Version 1.1 (20211004)
+ The formatting of the messages has been revised
+ If you overwrite a cached message, a hint appears
+ Only one active, cached message can now be sent
+ Detailed error messages have been added

Version 1.2 (Not published yet)
+ Multiple target channels can be defined
+ Name changed in the config (token -> bot_token)

package com.dinnerbone.bukkit.chat;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CommandHandler {
    protected final ChatBukkit plugin;

    public CommandHandler(ChatBukkit plugin) {
        this.plugin = plugin;
    }

    public abstract boolean perform(CommandSender sender, String[] args);
    
    protected static boolean anonymousCheck(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cannot execute that command, I don't know who you are!");
            return true;
        } else {
            return false;
        }
    }

    protected static Player getPlayer(CommandSender sender, String[] args, int index) {
        if (args.length >= index) {
            List<Player> players = sender.getServer().matchPlayer(args[index]);

            if (players.isEmpty()) {
                sender.sendMessage("I don't know who '" + args[index] + "' is!");
                return null;
            } else {
                return players.get(0);
            }
        } else {
            if (anonymousCheck(sender)) {
                return null;
            } else {
                return (Player)sender;
            }
        }
    }
}

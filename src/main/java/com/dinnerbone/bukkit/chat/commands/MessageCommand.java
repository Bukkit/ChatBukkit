package com.dinnerbone.bukkit.chat.commands;

import com.dinnerbone.bukkit.chat.ChatBukkit;
import com.dinnerbone.bukkit.chat.CommandHandler;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand extends CommandHandler {
    private Map<String, CommandSender> lastMessages = new HashMap<String, CommandSender>();

    public MessageCommand(ChatBukkit plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            return false;
        }

        if (!sender.hasPermission("chatbukkit.msg")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to send private messages");
            return true;
        }

        Player target = getPlayer(sender, args, 0);

        if (target != null) {
            String message = recompileMessage(args, 1, args.length - 1);
            String name = "Anonymous";

            // TODO: This should use an event, but we need some internal changes to support that fully.

            if (sender instanceof Player) {
                name = ((Player) sender).getDisplayName();
            }

            target.sendMessage(String.format("[%s]->[you]: %s", name, message));
            sender.sendMessage(String.format("[you]->[%s]: %s", target.getDisplayName(), message));

            lastMessages.put(target.getName(), sender);
        }

        return true;
    }

    public CommandSender getLastSender(CommandSender target) {
        CommandSender lastSender = null;

        if (target != null) {
            String senderName = target.getName();
            lastSender = lastMessages.get(senderName);

            if (lastSender instanceof Player) {
                if (!((Player) lastSender).isValid()) {
                    lastSender = plugin.getServer().getPlayerExact(lastSender.getName());
                    lastMessages.put(target.getName(), lastSender);
                }
            }
        }

        return lastSender;
    }
}
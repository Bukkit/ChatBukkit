package com.dinnerbone.bukkit.chat.commands;

import com.dinnerbone.bukkit.chat.ChatBukkit;
import com.dinnerbone.bukkit.chat.CommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class ReplyCommand extends CommandHandler {
    public ReplyCommand(ChatBukkit plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        if (!sender.hasPermission("chatbukkit.msg")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to send private messages");
            return true;
        }

        CommandSender target = getTarget(sender);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "There is nobody to reply to!");
        } else {
            String message = recompileMessage(args, 0, args.length - 1);
            String senderName = sender.getName();
            String targetName = target.getName();

            // TODO: This should use an event, but we need some internal changes to support that fully.

            if (target instanceof Player) {
                targetName = ((Player) target).getDisplayName();
            }

            if (sender instanceof Player) {
                senderName = ((Player) sender).getDisplayName();
            }

            target.sendMessage(String.format("[%s]->[you]: %s", senderName, message));
            sender.sendMessage(String.format("[you]->[%s]: %s", targetName, message));
        }

        return true;
    }

    private CommandSender getTarget(CommandSender sender) {
        PluginCommand command = plugin.getCommand("msg");

        if (command != null && command.getExecutor() instanceof MessageCommand) {
            return ((MessageCommand) command.getExecutor()).getLastSender(sender);
        } else {
            return null;
        }
    }
}

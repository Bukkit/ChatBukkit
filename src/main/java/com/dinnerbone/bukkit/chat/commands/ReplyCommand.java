
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
        if ((anonymousCheck(sender)) || (args.length < 1)) {
            return false;
        }

        if (!sender.hasPermission("chatbukkit.msg")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to send private messages");
            return true;
        }

        Player player = (Player)sender;
        CommandSender target = getTarget(player);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "There is nobody to reply to!");
        } else {
            String message = recompileMessage(args, 0, args.length - 1);
            String name = "Anonymous";

            // TODO: This should use an event, but we need some internal changes to support that fully.

            if (target instanceof Player) {
                name = ((Player)target).getDisplayName();
            }
            
            target.sendMessage(String.format("[%s]->[you]: %s", player.getDisplayName(), message));
            sender.sendMessage(String.format("[you]->[%s]: %s", name, message));
        }

        return true;
    }

    private CommandSender getTarget(Player player) {
        PluginCommand command = plugin.getCommand("msg");

        if ((command != null) && (command.getExecutor() instanceof MessageCommand)) {
            return ((MessageCommand)command.getExecutor()).getLastSender(player);
        } else {
            return null;
        }
    }
}

package com.dinnerbone.bukkit.chat.commands;

import com.dinnerbone.bukkit.chat.ChatBukkit;
import com.dinnerbone.bukkit.chat.CommandHandler;
import com.dinnerbone.bukkit.chat.events.WhoisRequestEvent;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoisCommand extends CommandHandler {
    public WhoisCommand(ChatBukkit plugin) {
        super(plugin);
    }

    @Override
    public boolean perform(CommandSender sender, String[] args) {
        if (args.length > 1) {
            return false;
        }

        Player player = getPlayer(sender, args, 0);

        if (player != null) {
            WhoisRequestEvent report = new WhoisRequestEvent(sender, player);

            report.setField("Display Name", player.getDisplayName());
            report.setField("World", player.getWorld().getName());

            if (!ChatColor.stripColor(player.getDisplayName()).equalsIgnoreCase(player.getName())) {
                report.setField("Username", player.getName());
            }

            player.getServer().getPluginManager().callEvent(report);

            sender.sendMessage("------ WHOIS report ------");
            Set<String> keys = report.getFields().keySet();
            for (String key : keys) {
                sender.sendMessage(key + ": " + report.getField(key));
            }
        } else {
            return true;
        }

        return false;
    }
}

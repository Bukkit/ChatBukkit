package com.dinnerbone.bukkit.chat.commands;

import com.dinnerbone.bukkit.chat.ChatBukkit;
import com.dinnerbone.bukkit.chat.CommandHandler;
import com.dinnerbone.bukkit.chat.events.WhoisRequestEvent;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoCommand extends CommandHandler {
    public WhoCommand(ChatBukkit plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            PerformPlayerList(sender, args);
            return true;
        } else if (args.length == 1) {
            PerformWhois(sender, args);
            return true;
        }

        return false;
    }

    private void PerformWhois(CommandSender sender, String[] args) {
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
        }
    }

    private void PerformPlayerList(CommandSender sender, String[] args) {
        String result = "";
        Player[] players = plugin.getServer().getOnlinePlayers();
        int count = 0;

        for (Player player : players) {
            String name = player.getDisplayName();

            if (name.length() > 0) {
                if (result.length() > 0) result += ", ";
                result += name;
                count++;
            }
        }

        if (count == 0) {
            sender.sendMessage("There's currently nobody playing on this server!");
        } else if (count == 1) {
            sender.sendMessage("There's only one player online: " + result);
        } else {
            sender.sendMessage("Online players: " + result);
        }
    }
}

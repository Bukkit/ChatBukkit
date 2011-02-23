
package com.dinnerbone.bukkit.chat;

import com.dinnerbone.bukkit.chat.commands.WhoisCommand;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Basic chat functionality
 *
 * @author Dinnerbone
 */
public class ChatBukkit extends JavaPlugin {
    private Map<String, CommandHandler> commands = new HashMap<String, CommandHandler>();

    public void onDisable() {}

    public void onEnable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getFullName() + " is enabled!" );

        commands.put("whois", new WhoisCommand(this));
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        CommandHandler handler = commands.get(command.getName().toLowerCase());
        
        if (handler != null) {
            return handler.perform(sender, args);
        } else {
            return false;
        }
    }
}
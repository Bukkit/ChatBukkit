
package com.dinnerbone.bukkit.chat;

import com.dinnerbone.bukkit.chat.commands.MessageCommand;
import com.dinnerbone.bukkit.chat.commands.ReplyCommand;
import com.dinnerbone.bukkit.chat.commands.WhoCommand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Basic chat functionality
 *
 * @author Dinnerbone
 */
public class ChatBukkit extends JavaPlugin {
    public void onDisable() {}

    public void onEnable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getFullName() + " is enabled!" );

        getCommand("who").setExecutor(new WhoCommand(this));
        getCommand("msg").setExecutor(new MessageCommand(this));
        getCommand("reply").setExecutor(new ReplyCommand(this));
    }
}
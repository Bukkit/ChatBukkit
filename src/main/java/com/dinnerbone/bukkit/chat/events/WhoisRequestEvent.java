package com.dinnerbone.bukkit.chat.events;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * Thrown when someone requests a WHOIS on a player
 */
public class WhoisRequestEvent extends Event {
    private final CommandSender sender;
    private final Player player;
    private final Map<String, String> result = new HashMap<String, String>();

    public WhoisRequestEvent(CommandSender sender, Player player) {
        super("WHOIS_REQUEST");
        this.sender = sender;
        this.player = player;
    }

    /**
     * Gets the target player to display details about
     * 
     * @return Player target of this event
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the {@link CommandSender} who requested the WHOIS report
     *
     * @return The origin of this request
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * Sets the specified field of this report to the given value.
     * Use null if you wish to remove that field.
     *
     * @param field Name of the field to set
     * @param value New value of the field (or null to remove)
     */
    public void setField(String field, String value) {
        if (value == null) {
            result.remove(field);
        } else {
            result.put(field, value);
        }
    }

    /**
     * Gets a field which is already collected by this report.
     *
     * @param field Name of the field to retrieve
     * @return Value contained in that field, or null if none exist
     */
    public String getField(String field) {
        return result.get(field);
    }

    /**
     * Returns all fields collected by this report so far
     *
     * @return Map<Name, Value> of all collected fields
     */
    public Map<String, String> getFields() {
        return result;
    }
}
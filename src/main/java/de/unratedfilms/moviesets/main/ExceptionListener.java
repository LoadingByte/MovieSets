
package de.unratedfilms.moviesets.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.quartercode.quarterbukkit.api.exception.NoCommandFoundException;
import com.quartercode.quarterbukkit.api.exception.NoCommandPermissionException;

public class ExceptionListener implements Listener {

    public ExceptionListener(MovieSetsPlugin plugin) {

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void noCommandPermissionException(NoCommandPermissionException exception) {

        exception.getCauser().sendMessage(ChatColor.DARK_RED + "You don't have enough permissions to use the command '" + exception.getCommand().getLabel() + "'!");
    }

    @EventHandler
    public void noCommandFoundException(NoCommandFoundException exception) {

        exception.getCommand().getSender().sendMessage(ChatColor.DARK_RED + "The command '" + exception.getCommand().getLabel() + "' doesn't exist!");
    }

}

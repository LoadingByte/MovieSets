
package de.unratedfilms.moviesets.command;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandHandler;
import com.quartercode.quarterbukkit.api.command.CommandInfo;
import de.unratedfilms.moviesets.logic.MovieSetStorage;

public class ClearCommand implements CommandHandler {

    @Override
    public CommandInfo getInfo() {

        return new CommandInfo(true, null, "Removes the names of all named sets, leaving an empty set list.", "moviesets.command.clear", "clear");
    }

    @Override
    public void execute(Command command) {

        CommandSender sender = command.getSender();
        if (! (sender instanceof Entity)) {
            sender.sendMessage(ChatColor.DARK_RED + "This command must be executed by an entity.");
            return;
        }

        World world = ((Entity) sender).getWorld();
        int setCount = MovieSetStorage.getNamedMovieSets(world).size();

        MovieSetStorage.clearNamedMovieSets(world);
        sender.sendMessage(ChatColor.DARK_GREEN + "Successfully removed all " + setCount + " sets from name list.");
    }

}

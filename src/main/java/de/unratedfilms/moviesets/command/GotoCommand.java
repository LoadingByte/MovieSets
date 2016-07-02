
package de.unratedfilms.moviesets.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandHandler;
import com.quartercode.quarterbukkit.api.command.CommandInfo;
import de.unratedfilms.moviesets.logic.MovieSet;
import de.unratedfilms.moviesets.logic.MovieSetStorage;
import de.unratedfilms.moviesets.util.MaybeError;

public class GotoCommand implements CommandHandler {

    @Override
    public CommandInfo getInfo() {

        return new CommandInfo(true, "<Set no. | Set name stub>", "Teleports you to the center of the set with the given number or name stub.", "moviesets.command.goto", "goto");
    }

    @Override
    public void execute(Command command) {

        CommandSender sender = command.getSender();
        if (! (sender instanceof Entity)) {
            sender.sendMessage(ChatColor.DARK_RED + "This command must be executed by an entity.");
            return;
        }

        String[] arguments = command.getArguments();
        if (arguments.length != 1) {
            sender.sendMessage(ChatColor.DARK_RED + "This command requires exactly 1 argument.");
            return;
        }

        Location teleportLocation = getSetTeleportLocation(sender, arguments[0]);
        if (teleportLocation != null) {
            ((Entity) sender).teleport(teleportLocation.getWorld().getHighestBlockAt(teleportLocation).getLocation().add(0.5, 0, 0.5));
        }
    }

    private Location getSetTeleportLocation(CommandSender sender, String argument) {

        MaybeError<MovieSet> identificationResult = MovieSetStorage.identifyMovieSet( ((Entity) sender).getWorld(), argument);

        if (identificationResult instanceof MaybeError.Error) {
            sender.sendMessage(ChatColor.DARK_RED + ((MaybeError.Error<MovieSet>) identificationResult).getMessage());
            return null;
        } else {
            MovieSet set = ((MaybeError.Success<MovieSet>) identificationResult).getValue();
            String messageSetName = set.getName() == null
                    ? ChatColor.DARK_AQUA + "unnamed" + ChatColor.DARK_GREEN
                    : "'" + ChatColor.DARK_AQUA + set.getName() + ChatColor.DARK_GREEN + "'";
            sender.sendMessage(ChatColor.DARK_GREEN + "Successfully teleported you to set " + ChatColor.GOLD + set.getIndex() + ChatColor.DARK_GREEN + " (" + messageSetName + ").");
            return set.getCenterLocation();
        }
    }

}

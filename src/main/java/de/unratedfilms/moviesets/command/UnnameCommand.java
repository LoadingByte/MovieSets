
package de.unratedfilms.moviesets.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandHandler;
import com.quartercode.quarterbukkit.api.command.CommandInfo;
import de.unratedfilms.moviesets.logic.MovieSet;
import de.unratedfilms.moviesets.logic.MovieSetStorage;
import de.unratedfilms.moviesets.util.MaybeError;

public class UnnameCommand implements CommandHandler {

    @Override
    public CommandInfo getInfo() {

        return new CommandInfo(true, "<Set no. | Set name stub>", "Removes the name of the set with the given number or name stub.", "moviesets.command.unname", "unname");
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

        doUnnaming(sender, arguments[0]);
    }

    private void doUnnaming(CommandSender sender, String argument) {

        MaybeError<MovieSet> identificationResult = MovieSetStorage.identifyMovieSet( ((Entity) sender).getWorld(), argument);

        if (identificationResult instanceof MaybeError.Error) {
            sender.sendMessage(ChatColor.DARK_RED + ((MaybeError.Error<MovieSet>) identificationResult).getMessage());
        } else {
            MovieSet set = ((MaybeError.Success<MovieSet>) identificationResult).getValue();
            MovieSetStorage.removeNamedMovieSet(set);
            sender.sendMessage(ChatColor.DARK_GREEN + "Set " + ChatColor.GOLD + set.getIndex() + ChatColor.DARK_GREEN + " with former name '"
                    + ChatColor.DARK_AQUA + set.getName() + ChatColor.DARK_GREEN + "' successfully removed from name list.");

        }
    }

}

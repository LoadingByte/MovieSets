
package de.unratedfilms.moviesets.command;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandHandler;
import com.quartercode.quarterbukkit.api.command.CommandInfo;
import de.unratedfilms.moviesets.logic.MovieSet;
import de.unratedfilms.moviesets.logic.MovieSetStorage;

public class NameCommand implements CommandHandler {

    @Override
    public CommandInfo getInfo() {

        return new CommandInfo(true, "<Set no.> <Set name>", "Declares the name of the set with the given number.", "moviesets.command.name", "name");
    }

    @Override
    public void execute(Command command) {

        CommandSender sender = command.getSender();
        if (! (sender instanceof Entity)) {
            sender.sendMessage(ChatColor.DARK_RED + "This command must be executed by an entity.");
            return;
        }

        String[] arguments = command.getArguments();
        if (arguments.length != 2) {
            sender.sendMessage(ChatColor.DARK_RED + "This command requires exactly 2 arguments.");
            return;
        }

        doNaming(sender, arguments[0], arguments[1]);
    }

    private void doNaming(CommandSender sender, String numberArgument, String nameArgument) {

        World world = ((Entity) sender).getWorld();

        int setIndex;
        try {
            setIndex = Integer.parseInt(numberArgument);

            if (setIndex <= 0) {
                sender.sendMessage(ChatColor.DARK_RED + "Set numbers must be >= 1, the provided number " + numberArgument + " is therefore invalid.");
                return;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.DARK_RED + numberArgument + " is not a valid set number.");
            return;
        }

        if (StringUtils.isBlank(nameArgument)) {
            sender.sendMessage(ChatColor.DARK_RED + "Can't use a blank string as set name.");
            return;
        } else if (StringUtils.isNumeric(nameArgument)) {
            sender.sendMessage(ChatColor.DARK_RED + "Can't use a string which only contains numbers as set name, the provided '" + nameArgument + "' is therefore invalid.");
            return;
        } else if (MovieSetStorage.getNamedMovieSetByName(world, nameArgument) != null) {
            sender.sendMessage(ChatColor.DARK_RED + "There already exists a set with the name '" + nameArgument + "'.");
            return;
        }

        MovieSetStorage.addNamedMovieSet(new MovieSet(setIndex, world, nameArgument));
        sender.sendMessage(ChatColor.DARK_GREEN + "Set " + ChatColor.GOLD + setIndex + ChatColor.DARK_GREEN + " successfully named '"
                + ChatColor.DARK_AQUA + nameArgument + ChatColor.DARK_GREEN + "'.");
    }

}

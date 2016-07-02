
package de.unratedfilms.moviesets.command;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandHandler;
import com.quartercode.quarterbukkit.api.command.CommandInfo;
import de.unratedfilms.moviesets.logic.MovieSet;
import de.unratedfilms.moviesets.logic.MovieSetStorage;

public class ListCommand implements CommandHandler {

    @Override
    public CommandInfo getInfo() {

        return new CommandInfo(true, null, "Lists all named sets alongside their numbers for quick teleportation.", "moviesets.command.list", "list");
    }

    @Override
    public void execute(Command command) {

        CommandSender sender = command.getSender();
        if (! (sender instanceof Entity)) {
            sender.sendMessage(ChatColor.DARK_RED + "This command must be executed by an entity.");
            return;
        }

        sender.sendMessage(ChatColor.GREEN + "==========[ MovieSets List ]==========");

        List<MovieSet> sets = MovieSetStorage.getNamedMovieSets( ((Entity) sender).getWorld());
        if (sets.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "This world doesn't have any named movie sets yet.");
        } else {
            // Retrieve the highest named movie set index; it will be used for formatting purposes later on
            int highestIndex = 0;
            for (MovieSet set : sets) {
                if (set.getIndex() > highestIndex) {
                    highestIndex = set.getIndex();
                }
            }

            Collections.sort(sets, new AlphabeticalMovieSetComparator());
            for (MovieSet set : sets) {
                String leading = StringUtils.repeat('-', String.valueOf(highestIndex).length() - String.valueOf(set.getIndex()).length()) + "--";
                sender.sendMessage(ChatColor.GRAY + leading + " [" + ChatColor.GOLD + set.getIndex() + ChatColor.GRAY + "] " + ChatColor.DARK_AQUA + set.getName());
            }
        }
    }

    private static class AlphabeticalMovieSetComparator implements Comparator<MovieSet> {

        @Override
        public int compare(MovieSet o1, MovieSet o2) {

            return o1.getName().compareTo(o2.getName());
        }

    }

}

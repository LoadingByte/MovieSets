
package de.unratedfilms.moviesets.command;

import static org.spongepowered.api.text.format.TextColors.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import de.unratedfilms.moviesets.Consts;
import de.unratedfilms.moviesets.logic.MovieSet;
import de.unratedfilms.moviesets.logic.MovieSetStorage;

public class ListCommand implements CommandExecutor {

    public static final CommandSpec SPEC = CommandSpec.builder()
            .description(Text.of("Lists all named sets alongside their numbers for quick teleportation"))
            .permission(Consts.PLUGIN_ID + ".command.list")
            .executor(new ListCommand())
            .build();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (! (src instanceof Player)) {
            throw new CommandException(Text.of("This command must be executed by a player."));
        }

        src.sendMessage(Text.of(GREEN, "==========[ " + Consts.PLUGIN_NAME + " List ]=========="));

        List<MovieSet> sets = MovieSetStorage.getNamedMovieSets( ((Player) src).getWorld());
        if (sets.isEmpty()) {
            throw new CommandException(Text.of("This world doesn't have any named movie sets yet."));
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
                String leading = StringUtils.repeat("-", String.valueOf(highestIndex).length() - String.valueOf(set.getIndex()).length()) + "--";
                src.sendMessage(Text.of(GRAY, leading, " [", GOLD, set.getIndex(), GRAY, "] ", DARK_AQUA, set.getName()));
            }

            return CommandResult.success();
        }
    }

    private static class AlphabeticalMovieSetComparator implements Comparator<MovieSet> {

        @Override
        public int compare(MovieSet o1, MovieSet o2) {

            return o1.getName().compareTo(o2.getName());
        }

    }

}

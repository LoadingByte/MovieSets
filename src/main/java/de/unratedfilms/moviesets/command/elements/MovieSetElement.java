
package de.unratedfilms.moviesets.command.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.PatternMatchingCommandElement;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import de.unratedfilms.moviesets.logic.MovieSet;
import de.unratedfilms.moviesets.logic.MovieSetStorage;
import de.unratedfilms.moviesets.util.MaybeError;

public class MovieSetElement extends PatternMatchingCommandElement {

    public MovieSetElement(Text key) {

        super(key);
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {

        if (! (src instanceof Player)) {
            throw args.createError(Text.of("The movie set command element must be used by a player"));
        }

        String next = args.next();
        MaybeError<MovieSet> identificationResult = MovieSetStorage.identifyMovieSet( ((Player) src).getWorld(), next);

        if (identificationResult instanceof MaybeError.Success) {
            return ((MaybeError.Success<MovieSet>) identificationResult).getValue();
        } else {
            throw args.createError(Text.of( ((MaybeError.Error<MovieSet>) identificationResult).getMessage()));
        }
    }

    @Override
    protected Iterable<String> getChoices(CommandSource src) {

        if (! (src instanceof Player)) {
            return Collections.emptyList();
        }

        List<MovieSet> sets = MovieSetStorage.getNamedMovieSets( ((Player) src).getWorld());

        List<String> choices = new ArrayList<>();
        for (MovieSet set : sets) {
            choices.add(String.valueOf(set.getIndex()));
        }
        for (MovieSet set : sets) {
            choices.add(set.getName());
        }
        return choices;
    }

    @Override
    protected Object getValue(String choice) throws IllegalArgumentException {

        // Never used, since we defined our own parseValue() method with more descriptive error messages
        return null;
    }

}

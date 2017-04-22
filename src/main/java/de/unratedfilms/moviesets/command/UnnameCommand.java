
package de.unratedfilms.moviesets.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import de.unratedfilms.moviesets.Consts;
import de.unratedfilms.moviesets.command.elements.MovieSetElement;
import de.unratedfilms.moviesets.logic.MovieSet;
import de.unratedfilms.moviesets.logic.MovieSetStorage;

public class UnnameCommand implements CommandExecutor {

    public static final CommandSpec SPEC = CommandSpec.builder()
            .description(Text.of("Removes the name of the set with the given number or name stub"))
            .permission(Consts.PLUGIN_ID + ".command.unname")
            .arguments(
                    GenericArguments.onlyOne(new MovieSetElement(Text.of("set no. | set name stub"))))
            .executor(new ClearCommand())
            .build();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (! (src instanceof Player)) {
            throw new CommandException(Text.of("This command must be executed by a player"));
        }

        if (!args.hasAny("set no. | set name stub")) {
            throw new CommandException(Text.of("This command requires you to provide a set as an argument"), true);
        }

        MovieSet set = args.<MovieSet> getOne("set no. | set name stub").get();

        MovieSetStorage.removeNamedMovieSet(set);

        src.sendMessage(Text.of(
                TextColors.DARK_GREEN, "Set ",
                TextColors.GOLD, set.getIndex(),
                TextColors.DARK_GREEN, " with former name '",
                TextColors.DARK_AQUA, set.getName(),
                TextColors.DARK_GREEN, "' successfully removed from name list"));
        return CommandResult.success();
    }

}

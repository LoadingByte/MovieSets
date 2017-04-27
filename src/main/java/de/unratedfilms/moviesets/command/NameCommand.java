
package de.unratedfilms.moviesets.command;

import static org.spongepowered.api.text.format.TextColors.*;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import de.unratedfilms.moviesets.Consts;
import de.unratedfilms.moviesets.command.elements.MovieSetElement;
import de.unratedfilms.moviesets.logic.MovieSet;
import de.unratedfilms.moviesets.logic.MovieSetStorage;

public class NameCommand implements CommandExecutor {

    public static final CommandSpec SPEC = CommandSpec.builder()
            .description(Text.of("Declares the new name of the set with the given number or name stub"))
            .permission(Consts.PLUGIN_ID + ".command.name")
            .arguments(
                    GenericArguments.onlyOne(new MovieSetElement(Text.of("set no. | set name stub"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("new set name"))))
            .executor(new ClearCommand())
            .build();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (! (src instanceof Player)) {
            throw new CommandException(Text.of("This command must be executed by a player."));
        }

        if (!args.hasAny("set no. | set name stub")) {
            throw new CommandException(Text.of("This command requires you to provide a set as an argument."), true);
        }
        if (!args.hasAny("new set name")) {
            throw new CommandException(Text.of("This command requires you to provide a new set name as an argument."), true);
        }

        MovieSet set = args.<MovieSet> getOne("set no. | set name stub").get();
        String newName = args.<String> getOne("new set name").get();

        if (StringUtils.isBlank(newName)) {
            throw new CommandException(Text.of("Can't use a blank string as the new set name."));
        }
        if (StringUtils.isNumeric(newName)) {
            throw new CommandException(Text.of("Can't use a string which only contains numbers as the new set name, the provided '" + newName + "' is therefore invalid."));
        }
        if (MovieSetStorage.getNamedMovieSetByName(set.getWorld(), newName) != null) {
            throw new CommandException(Text.of("There already exists a set with the name '" + newName + "'."));
        }

        MovieSetStorage.addNamedMovieSet(new MovieSet(set.getIndex(), set.getWorld(), newName));

        src.sendMessage(Text.of(
                DARK_GREEN, "Set ",
                GOLD, set.getIndex(),
                DARK_GREEN, " successfully named '",
                DARK_AQUA, newName,
                DARK_GREEN, "'"));
        return CommandResult.success();
    }

}

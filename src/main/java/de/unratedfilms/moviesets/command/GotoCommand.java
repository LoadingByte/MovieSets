
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
import de.unratedfilms.moviesets.command.elements.MovieSetElement;
import de.unratedfilms.moviesets.logic.MovieSet;

public class GotoCommand implements CommandExecutor {

    public static final CommandSpec SPEC = CommandSpec.builder()
            .description(Text.of("Teleports you to the center of the set with the given number or name stub"))
            .permission("moviesets.command.goto")
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

        Player player = (Player) src;
        MovieSet set = args.<MovieSet> getOne("set no. | set name stub").get();
        Text messageSetName = set.getName() == null
                ? Text.of(TextColors.DARK_AQUA, "unnamed", TextColors.DARK_GREEN)
                : Text.of("'", TextColors.DARK_AQUA, set.getName(), TextColors.DARK_GREEN, "'");

        if (!player.setLocationSafely(set.getCenterLocation())) {
            if (!player.setLocation(set.getCenterLocation())) {
                throw new CommandException(Text.of("Cannot teleport you to set ", set.getIndex(), " (", messageSetName, ")",
                        " because the teleport has been cancelled internally and thereby rejected."));
            }
        }

        src.sendMessage(Text.of(
                TextColors.DARK_GREEN, "Successfully teleported you to set ",
                TextColors.GOLD, set.getIndex(),
                TextColors.DARK_GREEN, " (", messageSetName, ")"));
        return CommandResult.success();
    }

}

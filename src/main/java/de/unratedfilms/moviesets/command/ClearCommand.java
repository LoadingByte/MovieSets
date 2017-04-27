
package de.unratedfilms.moviesets.command;

import static org.spongepowered.api.text.format.TextColors.DARK_GREEN;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;
import de.unratedfilms.moviesets.Consts;
import de.unratedfilms.moviesets.logic.MovieSetStorage;

public class ClearCommand implements CommandExecutor {

    public static final CommandSpec SPEC = CommandSpec.builder()
            .description(Text.of("Removes the names of all named sets, leaving an empty set list"))
            .permission(Consts.PLUGIN_ID + ".command.clear")
            .executor(new ClearCommand())
            .build();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (! (src instanceof Player)) {
            throw new CommandException(Text.of("This command must be executed by a player."));
        }

        World world = ((Player) src).getWorld();
        int setCount = MovieSetStorage.getNamedMovieSets(world).size();

        MovieSetStorage.clearNamedMovieSets(world);
        src.sendMessage(Text.of(DARK_GREEN, "Successfully removed all ", setCount, " sets from name list."));

        return CommandResult.success();
    }

}

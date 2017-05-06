
package de.unratedfilms.moviesets.command;

import static org.spongepowered.api.text.format.TextColors.*;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import de.unratedfilms.moviesets.Consts;
import de.unratedfilms.moviesets.command.elements.MovieSetElement;
import de.unratedfilms.moviesets.logic.MovieSet;
import de.unratedfilms.moviesets.util.Pos2D;

public class GotoCommand implements CommandExecutor {

    public static final CommandSpec SPEC = CommandSpec.builder()
            .description(Text.of("Teleports you to the center of the set with the given number or name stub"))
            .permission(Consts.PLUGIN_ID + ".command.goto")
            .arguments(
                    GenericArguments.onlyOne(new MovieSetElement(Text.of("set no. | set name stub"))))
            .executor(new GotoCommand())
            .build();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (! (src instanceof Player)) {
            throw new CommandException(Text.of("This command must be executed by a player."));
        }

        Player player = (Player) src;
        MovieSet set = args.<MovieSet> getOne("set no. | set name stub").get();
        Text messageSetName = set.getName() == null
                ? Text.of(DARK_AQUA, "unnamed", DARK_GREEN)
                : Text.of("'", DARK_AQUA, set.getName(), DARK_GREEN, "'");

        Location<World> gotoLocation = getHighestBlockLocAt(set.getWorld(), set.getCenterLocation()).add(0, 2 /* add 2 just to be safe */, 0);
        if (!player.setLocationSafely(gotoLocation)) {
            if (!player.setLocation(gotoLocation)) {
                throw new CommandException(Text.of("Cannot teleport you to set ", set.getIndex(), " (", messageSetName, ")",
                        " because the teleport has been cancelled internally and thereby rejected."));
            }
        }

        src.sendMessage(Text.of(
                DARK_GREEN, "Successfully teleported you to set ",
                GOLD, set.getIndex(),
                DARK_GREEN, " (", messageSetName, ")."));
        return CommandResult.success();
    }

    private Location<World> getHighestBlockLocAt(World world, Pos2D column) {

        for (int y = world.getBlockMax().getY(); y >= 0; y--) {
            if (world.getBlock(column.getX(), y, column.getZ()).getType() != BlockTypes.AIR) {
                return new Location<>(world, column.getX(), y, column.getZ());
            }
        }

        // There's no block at all, but we don't want to crash
        return new Location<>(world, column.getX(), 0, column.getZ());
    }

}

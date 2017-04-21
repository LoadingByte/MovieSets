
package de.unratedfilms.moviesets.command;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class InfoCommand implements CommandExecutor {

    public static final CommandSpec SPEC = CommandSpec.builder()
            .description(Text.of("Shows an info page"))
            .permission("moviesets.command.info")
            .executor(new InfoCommand())
            .build();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        PluginContainer plg = Sponge.getPluginManager().getPlugin("moviesets").get();

        src.sendMessage(Text.of(TextColors.GREEN, "==========[ MovieSets Info ]=========="));
        src.sendMessage(Text.of(TextColors.AQUA, "This is MovieSets version ", TextColors.GOLD, plg.getVersion().orElse("unknown"), TextColors.AQUA, ""));
        src.sendMessage(Text.of(TextColors.AQUA, plg.getDescription().orElse("no description")));
        src.sendMessage(Text.of(TextColors.AQUA, "Credits: ", TextColors.GOLD,
                Text.joinWith(Text.of(TextColors.AQUA, ", ", TextColors.GOLD), plg.getAuthors().stream().map(Text::of).iterator())));

        return CommandResult.success();
    }

}

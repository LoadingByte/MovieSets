
package de.unratedfilms.moviesets.command;

import static org.spongepowered.api.text.format.TextColors.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import de.unratedfilms.moviesets.Consts;

public class InfoCommand implements CommandExecutor {

    public static final CommandSpec SPEC = CommandSpec.builder()
            .description(Text.of("Shows an info page"))
            .permission(Consts.PLUGIN_ID + ".command.info")
            .executor(new InfoCommand())
            .build();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        PluginContainer plg = Sponge.getPluginManager().getPlugin(Consts.PLUGIN_ID).get();

        src.sendMessage(Text.of(GREEN, "==========[ " + Consts.PLUGIN_NAME + " Info ]=========="));
        src.sendMessage(Text.of(AQUA, "This is " + Consts.PLUGIN_NAME + " version ", GOLD, plg.getVersion().orElse("unknown"), AQUA, ""));
        src.sendMessage(Text.of(AQUA, plg.getDescription().orElse("no description")));
        src.sendMessage(Text.of(AQUA, "Credits: ", GOLD,
                Text.joinWith(Text.of(AQUA, ", ", GOLD), plg.getAuthors().stream().map(Text::of).iterator())));

        return CommandResult.success();
    }

}

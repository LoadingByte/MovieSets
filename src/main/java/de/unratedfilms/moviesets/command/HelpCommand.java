
package de.unratedfilms.moviesets.command;

import static org.spongepowered.api.text.format.TextColors.*;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandMapping;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.dispatcher.Dispatcher;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import de.unratedfilms.moviesets.Consts;

/*
 * This command is quite the hack since Sponge doesn't want you to access subcommand metadata for some unknown reason.
 * Hopefully that's gonna improve in the future.
 */
public class HelpCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        src.sendMessage(Text.of(GREEN, "==========[ " + Consts.PLUGIN_NAME + " Help ]=========="));

        CommandMapping cmd = Sponge.getCommandManager().get(Consts.PLUGIN_ID, src).get();

        Iterator<Text> aliases = cmd.getAllAliases().stream().sorted((s1, s2) -> Integer.compare(s1.length(), s2.length())).map(str -> Text.of("/", str)).iterator();
        src.sendMessage(Text.of("Aliases: ", DARK_GREEN,
                Text.joinWith(Text.of(WHITE, ", ", DARK_GREEN), aliases)));

        // A really hacky way to get the subcommands; I would be quite astonished if SpongeAPI doesn't offer a better one.
        Set<? extends CommandMapping> subcmds;
        try {
            Dispatcher dp = (Dispatcher) FieldUtils.readDeclaredField( ((CommandSpec) cmd.getCallable()).getExecutor(), "dispatcher" /* in ChildCommandElementExecutor */, true);
            subcmds = dp.getCommands();
        } catch (IllegalAccessException e) {
            // This should never happen!
            throw new IllegalStateException(e);
        }

        for (CommandMapping subcmd : subcmds) {
            CommandCallable subcmdc = subcmd.getCallable();

            if (subcmdc.testPermission(src)) {
                for (String alias : subcmd.getAllAliases()) {
                    Text line = Text.of(GOLD, "/", cmd.getPrimaryAlias(), " ", alias);
                    if (subcmdc.getUsage(src) != null) {
                        line = Text.of(line, " ", subcmdc.getUsage(src));
                    }
                    src.sendMessage(line);
                }

                src.sendMessage(Text.of(DARK_RED, " > ", GRAY, subcmdc.getShortDescription(src).get()));
            }
        }

        return CommandResult.success();
    }

}

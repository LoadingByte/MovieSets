
package de.unratedfilms.moviesets.main;

import com.quartercode.quarterbukkit.api.command.CommandExecutor;
import de.unratedfilms.moviesets.command.ClearCommand;
import de.unratedfilms.moviesets.command.GotoCommand;
import de.unratedfilms.moviesets.command.HelpCommand;
import de.unratedfilms.moviesets.command.InfoCommand;
import de.unratedfilms.moviesets.command.ListCommand;
import de.unratedfilms.moviesets.command.NameCommand;
import de.unratedfilms.moviesets.command.UnnameCommand;

class MovieSetsPluginExecutor {

    public static void onEnable(MovieSetsPlugin plugin) {

        new ExceptionListener(plugin);

        CommandExecutor commandExecutor = new CommandExecutor(plugin, "moviesets");
        commandExecutor.addCommandHandler(new HelpCommand());
        commandExecutor.addCommandHandler(new InfoCommand());
        commandExecutor.addCommandHandler(new ListCommand());
        commandExecutor.addCommandHandler(new GotoCommand());
        commandExecutor.addCommandHandler(new NameCommand());
        commandExecutor.addCommandHandler(new UnnameCommand());
        commandExecutor.addCommandHandler(new ClearCommand());

        plugin.getLogger().info("Plugin successfully enabled.");
    }

    public static void onDisable(MovieSetsPlugin plugin) {

        plugin.getLogger().info("Plugin successfully disabled.");
    }

}

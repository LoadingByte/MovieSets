
package de.unratedfilms.moviesets.main;

import java.util.logging.Logger;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import com.quartercode.quarterbukkit.api.command.CommandExecutor;
import de.unratedfilms.moviesets.command.ClearCommand;
import de.unratedfilms.moviesets.command.GotoCommand;
import de.unratedfilms.moviesets.command.HelpCommand;
import de.unratedfilms.moviesets.command.InfoCommand;
import de.unratedfilms.moviesets.command.ListCommand;
import de.unratedfilms.moviesets.command.NameCommand;
import de.unratedfilms.moviesets.command.UnnameCommand;
import de.unratedfilms.moviesets.gen.MovieSetsWorldGenerator;

public class MovieSetsPlugin extends JavaPlugin {

    private final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {

        new ExceptionListener(this);

        CommandExecutor commandExecutor = new CommandExecutor(this);
        getCommand("sets").setExecutor(commandExecutor);
        getCommand("sets").setTabCompleter(commandExecutor);

        commandExecutor.addCommandHandler(new HelpCommand());
        commandExecutor.addCommandHandler(new InfoCommand());
        commandExecutor.addCommandHandler(new ListCommand());
        commandExecutor.addCommandHandler(new GotoCommand());
        commandExecutor.addCommandHandler(new NameCommand());
        commandExecutor.addCommandHandler(new UnnameCommand());
        commandExecutor.addCommandHandler(new ClearCommand());

        log.info("[MovieSet] Plugin successfully enabled.");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {

        return new MovieSetsWorldGenerator();
    }

}

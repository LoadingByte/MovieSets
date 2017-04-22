
package de.unratedfilms.moviesets.main;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.gen.WorldGeneratorModifier;
import de.unratedfilms.moviesets.Consts;
import de.unratedfilms.moviesets.command.ClearCommand;
import de.unratedfilms.moviesets.command.GotoCommand;
import de.unratedfilms.moviesets.command.HelpCommand;
import de.unratedfilms.moviesets.command.InfoCommand;
import de.unratedfilms.moviesets.command.ListCommand;
import de.unratedfilms.moviesets.command.NameCommand;
import de.unratedfilms.moviesets.command.UnnameCommand;
import de.unratedfilms.moviesets.gen.MovieSetsWorldGeneratorModifier;

@Plugin (id = Consts.PLUGIN_ID)
public class MovieSetsPlugin {

    @Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) {

        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .description(Text.of("The main and only command for controlling the " + Consts.PLUGIN_NAME + " plugin"))
                .permission(Consts.PLUGIN_ID + ".command.help")
                .executor(new HelpCommand())
                .child(InfoCommand.SPEC, "info")
                .child(ListCommand.SPEC, "list", "ls", "l")
                .child(GotoCommand.SPEC, "goto", "tp")
                .child(NameCommand.SPEC, "name", "create")
                .child(UnnameCommand.SPEC, "unname", "remove", "delete")
                .child(ClearCommand.SPEC, "clear")
                .build(), Consts.PLUGIN_ID, "msets", "sets");

        Sponge.getRegistry().register(WorldGeneratorModifier.class, new MovieSetsWorldGeneratorModifier());
    }

}

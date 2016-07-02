
package de.unratedfilms.moviesets.main;

import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import com.quartercode.quarterbukkit.QuarterBukkitIntegration;
import de.unratedfilms.moviesets.gen.MovieSetsWorldGenerator;

public class MovieSetsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        // QuarterBukkit
        if (!QuarterBukkitIntegration.integrate(this)) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        MovieSetsPluginExecutor.onEnable(this);
    }

    @Override
    public void onDisable() {

        MovieSetsPluginExecutor.onDisable(this);
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {

        return new MovieSetsWorldGenerator();
    }

}

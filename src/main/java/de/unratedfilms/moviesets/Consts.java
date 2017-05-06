
package de.unratedfilms.moviesets;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;

public class Consts {

    public static final String PLUGIN_ID    = "moviesets";
    public static final String PLUGIN_NAME  = "MovieSets";

    public static final Logger LOGGER       = Sponge.getPluginManager().getPlugin(PLUGIN_ID).get().getLogger();

    /**
     * The x/z width and height of each big movie set square.
     * This number is odd so that each movie set has a single center block.
     */
    public static final int    SET_DIAMETER = 2001;

    /**
     * The y-height of each big movie set square, excluding the bedrock at the bottom.
     */
    public static final int    SET_HEIGHT   = 50;

    /**
     * The width of the empty gaps between the big movie set squares.
     */
    public static final int    SET_GAP      = 100;

    private Consts() {}

}

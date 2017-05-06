
package de.unratedfilms.moviesets.logic;

import de.unratedfilms.moviesets.Consts;
import de.unratedfilms.moviesets.util.Pos2D;

public class MovieSetUtils {

    /**
     * Checks whether the given block location is part of a movie set.
     *
     * @param pos The block location to check.
     * @return Whether or not the given block is part of the movie set.
     */
    public static boolean isInsideMovieSet(Pos2D pos) {

        int movieSetCenterDiff = Consts.SET_DIAMETER + Consts.SET_GAP;
        int zeroMovieSetRadius = (Consts.SET_DIAMETER - 1) / 2;
        int zeroRegionRadius = zeroMovieSetRadius + Consts.SET_GAP;

        int mx = Math.abs(pos.getX());
        int mz = Math.abs(pos.getZ());

        // Makes sure that the center "movie set region" (index 0, grid coordinates 0;0) is not a movie set since that's the spawn.
        if (mx <= zeroRegionRadius && mz <= zeroRegionRadius) {
            return false;
        }

        while (mx > zeroRegionRadius) {
            mx -= movieSetCenterDiff;
        }
        while (mz > zeroRegionRadius) {
            mz -= movieSetCenterDiff;
        }

        return mx <= zeroMovieSetRadius && mz <= zeroMovieSetRadius;
    }

    private MovieSetUtils() {}

}

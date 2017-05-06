
package com.quartercode.moviesets.test.logic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import de.unratedfilms.moviesets.Consts;
import de.unratedfilms.moviesets.logic.MovieSet;

@RunWith (Parameterized.class)
public class MovieSetGetCenterLocationTest {

    @Parameters
    public static Collection<Object[]> getData() {

        int movieSetCenterDiff = Consts.SET_DIAMETER + Consts.SET_GAP;

        List<Object[]> data = new ArrayList<>();
        data.add(new Object[] { 0, 0, 0 });
        data.add(new Object[] { 1, movieSetCenterDiff, 0 });
        data.add(new Object[] { 2, movieSetCenterDiff, -movieSetCenterDiff });
        data.add(new Object[] { 3, 0, -movieSetCenterDiff });
        data.add(new Object[] { 4, -movieSetCenterDiff, -movieSetCenterDiff });
        data.add(new Object[] { 5, -movieSetCenterDiff, 0 });
        data.add(new Object[] { 6, -movieSetCenterDiff, movieSetCenterDiff });
        data.add(new Object[] { 7, 0, movieSetCenterDiff });
        data.add(new Object[] { 8, movieSetCenterDiff, movieSetCenterDiff });
        data.add(new Object[] { 9, 2 * movieSetCenterDiff, movieSetCenterDiff });
        data.add(new Object[] { 10, 2 * movieSetCenterDiff, 0 });
        data.add(new Object[] { 11, 2 * movieSetCenterDiff, -movieSetCenterDiff });
        return data;
    }

    private final int index;
    private final int expectedX;
    private final int expectedZ;

    public MovieSetGetCenterLocationTest(int index, int expectedX, int expectedZ) {

        this.index = index;
        this.expectedX = expectedX;
        this.expectedZ = expectedZ;
    }

    @Test
    public void testGetCenterLocation() {

        Location<World> actualLoc = new MovieSet(index, mock(World.class), "").getCenterLocation();
        assertEquals("Center location x coordinate", expectedX, actualLoc.getBlockX());
        assertEquals("Center location y coordinate", 0, actualLoc.getBlockY());
        assertEquals("Center location z coordinate", expectedZ, actualLoc.getBlockZ());
    }

}

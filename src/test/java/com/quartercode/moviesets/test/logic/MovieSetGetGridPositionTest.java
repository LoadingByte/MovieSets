
package com.quartercode.moviesets.test.logic;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import de.unratedfilms.moviesets.logic.MovieSet;
import de.unratedfilms.moviesets.util.Pos2D;

@RunWith (Parameterized.class)
public class MovieSetGetGridPositionTest {

    @Parameters
    public static Collection<Object[]> getData() {

        List<Object[]> data = new ArrayList<>();
        data.add(new Object[] { 0, 0, 0 });
        data.add(new Object[] { 1, 1, 0 });
        data.add(new Object[] { 2, 1, -1 });
        data.add(new Object[] { 3, 0, -1 });
        data.add(new Object[] { 4, -1, -1 });
        data.add(new Object[] { 5, -1, 0 });
        data.add(new Object[] { 6, -1, 1 });
        data.add(new Object[] { 7, 0, 1 });
        data.add(new Object[] { 8, 1, 1 });
        data.add(new Object[] { 9, 2, 1 });
        data.add(new Object[] { 10, 2, 0 });
        data.add(new Object[] { 11, 2, -1 });
        return data;
    }

    private final int index;
    private final int expectedGridX;
    private final int expectedGridZ;

    public MovieSetGetGridPositionTest(int index, int expectedGridX, int expectedGridZ) {

        this.index = index;
        this.expectedGridX = expectedGridX;
        this.expectedGridZ = expectedGridZ;
    }

    @Test
    public void testGetGridPosition() {

        Pos2D actualGridPos = new MovieSet(index, null, null).getGridPosition();
        assertEquals("Grid x coordinate", expectedGridX, actualGridPos.getX());
        assertEquals("Grid z coordinate", expectedGridZ, actualGridPos.getZ());
    }

}

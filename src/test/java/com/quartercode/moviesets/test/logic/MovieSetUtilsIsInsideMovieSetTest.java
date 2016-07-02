
package com.quartercode.moviesets.test.logic;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import de.unratedfilms.moviesets.logic.Consts;
import de.unratedfilms.moviesets.logic.MovieSetUtils;
import de.unratedfilms.moviesets.util.Pos2D;

@RunWith (Parameterized.class)
public class MovieSetUtilsIsInsideMovieSetTest {

    @Parameters
    public static Collection<Object[]> getData() {

        List<Object[]> data = new ArrayList<>();

        /*
         * center x test
         */

        data.add(new Object[] { 0, 0, false });

        data.add(new Object[] { 0, 0, false });
        data.add(new Object[] { 1, 0, false });
        data.add(new Object[] { -1, 0, false });
        data.add(new Object[] { (Consts.SET_DIAMETER - 1) / 2, 0, false });
        data.add(new Object[] { - (Consts.SET_DIAMETER - 1) / 2, 0, false });

        data.add(new Object[] { (Consts.SET_DIAMETER - 1) / 2 + 1, 0, false });
        data.add(new Object[] { - (Consts.SET_DIAMETER - 1) / 2 - 1, 0, false });
        data.add(new Object[] { (Consts.SET_DIAMETER - 1) / 2 + Consts.SET_GAP, 0, false });
        data.add(new Object[] { - (Consts.SET_DIAMETER - 1) / 2 - Consts.SET_GAP, 0, false });

        /*
         *
         */

        // We need an offset here sine the center region is not a movie set.
        int otherCoordOffset = Consts.SET_DIAMETER + Consts.SET_GAP;

        // x tests

        data.add(new Object[] { 0, otherCoordOffset, true });
        data.add(new Object[] { 1, otherCoordOffset, true });
        data.add(new Object[] { -1, otherCoordOffset, true });
        data.add(new Object[] { (Consts.SET_DIAMETER - 1) / 2, otherCoordOffset, true });
        data.add(new Object[] { - (Consts.SET_DIAMETER - 1) / 2, otherCoordOffset, true });

        data.add(new Object[] { (Consts.SET_DIAMETER - 1) / 2 + 1, otherCoordOffset, false });
        data.add(new Object[] { - (Consts.SET_DIAMETER - 1) / 2 - 1, otherCoordOffset, false });
        data.add(new Object[] { (Consts.SET_DIAMETER - 1) / 2 + Consts.SET_GAP, otherCoordOffset, false });
        data.add(new Object[] { - (Consts.SET_DIAMETER - 1) / 2 - Consts.SET_GAP, otherCoordOffset, false });

        data.add(new Object[] { Consts.SET_DIAMETER + Consts.SET_GAP, otherCoordOffset, true });
        data.add(new Object[] { -Consts.SET_DIAMETER - Consts.SET_GAP, otherCoordOffset, true });

        data.add(new Object[] { Consts.SET_DIAMETER + Consts.SET_GAP + (Consts.SET_DIAMETER - 1) / 2 + Consts.SET_GAP, otherCoordOffset, false });
        data.add(new Object[] { -Consts.SET_DIAMETER - Consts.SET_GAP - (Consts.SET_DIAMETER - 1) / 2 - Consts.SET_GAP, otherCoordOffset, false });

        // z tests

        data.add(new Object[] { otherCoordOffset, 0, true });
        data.add(new Object[] { otherCoordOffset, 1, true });
        data.add(new Object[] { otherCoordOffset, -1, true });
        data.add(new Object[] { otherCoordOffset, (Consts.SET_DIAMETER - 1) / 2, true });
        data.add(new Object[] { otherCoordOffset, - (Consts.SET_DIAMETER - 1) / 2, true });

        data.add(new Object[] { otherCoordOffset, (Consts.SET_DIAMETER - 1) / 2 + 1, false });
        data.add(new Object[] { otherCoordOffset, - (Consts.SET_DIAMETER - 1) / 2 - 1, false });
        data.add(new Object[] { otherCoordOffset, (Consts.SET_DIAMETER - 1) / 2 + Consts.SET_GAP, false });
        data.add(new Object[] { otherCoordOffset, - (Consts.SET_DIAMETER - 1) / 2 - Consts.SET_GAP, false });

        data.add(new Object[] { otherCoordOffset, Consts.SET_DIAMETER + Consts.SET_GAP, true });
        data.add(new Object[] { otherCoordOffset, -Consts.SET_DIAMETER - Consts.SET_GAP, true });

        data.add(new Object[] { otherCoordOffset, Consts.SET_DIAMETER + Consts.SET_GAP + (Consts.SET_DIAMETER - 1) / 2 + Consts.SET_GAP, false });
        data.add(new Object[] { otherCoordOffset, -Consts.SET_DIAMETER - Consts.SET_GAP - (Consts.SET_DIAMETER - 1) / 2 - Consts.SET_GAP, false });

        return data;
    }

    private final int     x;
    private final int     z;
    private final boolean expectedResult;

    public MovieSetUtilsIsInsideMovieSetTest(int x, int z, boolean expectedResult) {

        this.x = x;
        this.z = z;
        this.expectedResult = expectedResult;
    }

    @Test
    public void testGetGridPosition() {

        assertEquals("Check result", expectedResult, MovieSetUtils.isInsideMovieSet(new Pos2D(x, z)));
    }

}

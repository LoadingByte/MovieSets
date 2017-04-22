
package de.unratedfilms.moviesets.logic;

import static de.unratedfilms.moviesets.Consts.LOGGER;
import org.apache.commons.lang3.Validate;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import de.unratedfilms.moviesets.Consts;
import de.unratedfilms.moviesets.util.Pos2D;

public class MovieSet {

    private int    index;
    private World  world;
    private String name;

    public MovieSet(int index, World world, String name) {

        setIndex(index);
        this.world = world;
        this.name = name;
    }

    public int getIndex() {

        return index;
    }

    public void setIndex(int index) {

        Validate.isTrue(index >= 0);
        this.index = index;
    }

    public World getWorld() {

        return world;
    }

    public void setWorld(World world) {

        this.world = world;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    // Uses ideas from:
    // https://math.stackexchange.com/questions/163080/on-a-two-dimensional-grid-is-there-a-formula-i-can-use-to-spiral-coordinates-in
    public Pos2D getGridPosition() {

        int k;

        int m = (int) Math.floor(Math.sqrt(index));
        if (m % 2 != 0) { // m is odd
            k = (m - 1) / 2;
        } else { // m is even
            if (index >= m * (m + 1)) {
                k = m / 2;
            } else {
                k = m / 2 - 1;
            }
        }

        if (2 * k * (2 * k + 1) <= index && index <= square(2 * k + 1)) {
            return new Pos2D(index - 4 * square(k) - 3 * k, k);
        } else if (square(2 * k + 1) < index && index <= 2 * (k + 1) * (2 * k + 1)) {
            return new Pos2D(k + 1, 4 * square(k) + 5 * k + 1 - index);
        } else if (2 * (k + 1) * (2 * k + 1) < index && index <= 4 * square(k + 1)) {
            return new Pos2D(4 * square(k) + 7 * k + 3 - index, -k - 1);
        } else if (4 * square(k + 1) < index && index <= 2 * (k + 1) * (2 * k + 3)) {
            return new Pos2D(-k - 1, index - 4 * square(k) - 9 * k - 5);
        } else {
            LOGGER.error("Programming error: Cannot calculate grid position of movie set index " + index + " because it doesn't seem to fit into the programmed categories");
            return null;
        }
    }

    private int square(int n) {

        return n * n;
    }

    public Location<World> getCenterLocation() {

        Pos2D gridPosition = getGridPosition();
        int x = gridPosition.getX() * (Consts.SET_DIAMETER + Consts.SET_GAP);
        int z = gridPosition.getZ() * (Consts.SET_DIAMETER + Consts.SET_GAP);
        return new Location<>(world, x, 0, z);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + index;
        result = prime * result + (name == null ? 0 : name.hashCode());
        result = prime * result + (world == null ? 0 : world.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MovieSet other = (MovieSet) obj;
        if (index != other.index) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (world == null) {
            if (other.world != null) {
                return false;
            }
        } else if (!world.equals(other.world)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return name;
    }

}

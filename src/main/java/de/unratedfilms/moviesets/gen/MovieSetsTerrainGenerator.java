
package de.unratedfilms.moviesets.gen;

import static de.unratedfilms.moviesets.logic.Consts.SET_HEIGHT;
import static java.lang.Math.max;
import static java.lang.Math.min;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.ImmutableBiomeVolume;
import org.spongepowered.api.world.extent.MutableBlockVolume;
import org.spongepowered.api.world.gen.GenerationPopulator;
import com.flowpowered.math.vector.Vector3i;
import de.unratedfilms.moviesets.logic.MovieSetUtils;
import de.unratedfilms.moviesets.util.Pos2D;

public class MovieSetsTerrainGenerator implements GenerationPopulator {

    private final Cause generatorCause = Cause.source(this).build();

    @Override
    public void populate(World world, MutableBlockVolume buffer, ImmutableBiomeVolume biomes) {

        Vector3i min = buffer.getBlockMin();
        Vector3i max = buffer.getBlockMax();

        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int z = min.getZ(); z <= max.getZ(); z++) {
                // Set the ground level bedrock block
                if (min.getY() == 0) {
                    buffer.setBlockType(x, 0, z, BlockTypes.BEDROCK, generatorCause);
                }

                // Add a platform if the current xz-location is part of a movie set
                if (MovieSetUtils.isInsideMovieSet(new Pos2D(x, z))) {
                    for (int y = max(1, min.getY()); y < min(max.getY(), SET_HEIGHT); y++) {
                        buffer.setBlockType(x, y, z, BlockTypes.DIRT, generatorCause);
                    }

                    if (min.getY() < SET_HEIGHT && SET_HEIGHT < max.getY()) {
                        buffer.setBlockType(x, SET_HEIGHT, z, BlockTypes.GRASS, generatorCause);
                    }
                }
            }
        }
    }

}

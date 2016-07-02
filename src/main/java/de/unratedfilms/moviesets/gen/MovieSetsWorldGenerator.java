
package de.unratedfilms.moviesets.gen;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import de.unratedfilms.moviesets.logic.Consts;
import de.unratedfilms.moviesets.logic.MovieSetUtils;
import de.unratedfilms.moviesets.util.Pos2D;

public class MovieSetsWorldGenerator extends ChunkGenerator {

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {

        return new Location(world, 0, 1, 0);
    }

    @Override
    public byte[][] generateBlockSections(World world, Random random, int cx, int cz, BiomeGrid biomes) {

        byte[][] result = new byte[world.getMaxHeight() / 16][];

        for (int lx = 0; lx < 16; lx++) {
            int gx = cx * 16 + lx;

            for (int lz = 0; lz < 16; lz++) {
                int gz = cz * 16 + lz;

                // The biome should always be "plains".
                biomes.setBiome(lx, lz, Biome.PLAINS);

                // Set the ground level bedrock block.
                setBlock(result, lx, 0, lz, (byte) 7 /* bedrock */);

                // Add a platform if the current xz-location is part of a movie set.
                if (MovieSetUtils.isInsideMovieSet(new Pos2D(gx, gz))) {
                    for (int y = 1; y < Consts.SET_HEIGHT; y++) {
                        setBlock(result, lx, y, lz, (byte) 3 /* dirt */);
                    }

                    setBlock(result, lx, Consts.SET_HEIGHT, lz, (byte) 2 /* grass */);
                }
            }
        }

        return result;
    }

    private void setBlock(byte[][] result, int x, int y, int z, byte blockId) {

        if (result[y >> 4] == null) {
            result[y >> 4] = new byte[4096];
        }
        result[y >> 4][ (y & 0xF) << 8 | z << 4 | x] = blockId;
    }

}

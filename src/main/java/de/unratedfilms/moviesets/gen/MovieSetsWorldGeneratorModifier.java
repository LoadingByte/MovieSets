
package de.unratedfilms.moviesets.gen;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.world.biome.BiomeGenerationSettings;
import org.spongepowered.api.world.biome.BiomeType;
import org.spongepowered.api.world.biome.BiomeTypes;
import org.spongepowered.api.world.gen.WorldGenerator;
import org.spongepowered.api.world.gen.WorldGeneratorModifier;
import org.spongepowered.api.world.storage.WorldProperties;
import de.unratedfilms.moviesets.Consts;

public class MovieSetsWorldGeneratorModifier implements WorldGeneratorModifier {

    @Override
    public void modifyWorldGenerator(WorldProperties world, DataContainer settings, WorldGenerator worldGenerator) {

        worldGenerator.getGenerationPopulators().clear();
        worldGenerator.getPopulators().clear();
        for (BiomeType biome : Sponge.getRegistry().getAllOf(BiomeType.class)) {
            BiomeGenerationSettings biomeSettings = worldGenerator.getBiomeSettings(biome);
            biomeSettings.getGenerationPopulators().clear();
            biomeSettings.getPopulators().clear();
            biomeSettings.getGroundCoverLayers().clear();
        }

        worldGenerator.setBaseGenerationPopulator(new MovieSetsTerrainGenerator());
        worldGenerator.setBiomeGenerator(buffer -> buffer.getBiomeWorker().fill((x, y, z) -> BiomeTypes.PLAINS));
    }

    @Override
    public String getId() {

        return Consts.PLUGIN_ID + ":" + Consts.PLUGIN_ID;
    }

    @Override
    public String getName() {

        return Consts.PLUGIN_NAME + " Modifier";
    }

}

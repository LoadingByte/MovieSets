
package de.unratedfilms.moviesets.logic;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.bukkit.World;
import de.unratedfilms.moviesets.util.MaybeError;

public class MovieSetStorage {

    private static final String STORAGE_FILE_NAME = "moviesets.properties";

    private static final Logger log               = Logger.getLogger("Minecraft");

    public static List<MovieSet> getNamedMovieSets(World world) {

        Properties storage = loadProperties(world);

        List<MovieSet> movieSets = new ArrayList<>();

        for (Entry<Object, Object> entry : storage.entrySet()) {
            String setName = String.valueOf(entry.getValue());

            try {
                int setIndex = Integer.parseInt(String.valueOf(entry.getKey()));

                // When the index parsing has been successful
                movieSets.add(new MovieSet(setIndex, world, setName));
            } catch (NumberFormatException e) {
                log.warning("Can't parse movie set index '" + entry.getKey() + "' in movie set storage file for world '" + world.getName() + "';"
                        + "ignoring that entry for now, it will be deleted later on.");
            }
        }

        return movieSets;
    }

    public static MovieSet getNamedMovieSetByIndex(World world, int index) {

        for (MovieSet movieSet : getNamedMovieSets(world)) {
            if (movieSet.getIndex() == index) {
                return movieSet;
            }
        }

        return null;
    }

    public static MovieSet getNamedMovieSetByName(World world, String name) {

        for (MovieSet movieSet : getNamedMovieSets(world)) {
            if (movieSet.getName().equalsIgnoreCase(name)) {
                return movieSet;
            }
        }

        return null;
    }

    public static List<MovieSet> getNamedMovieSetsByNameStub(World world, String nameStub) {

        List<MovieSet> matchingSets = new ArrayList<>();

        for (MovieSet movieSet : getNamedMovieSets(world)) {
            if (movieSet.getName().toLowerCase().startsWith(nameStub.toLowerCase())) {
                matchingSets.add(movieSet);
            }
        }

        return matchingSets;
    }

    /**
     * This method takes a string of information and tries to identify the one and only movie set which fits it.
     * Note that the name of the returned movie set might be {@code null} if the identified movie set has not yet been named.
     * Also note that multiple possible movie set results (e.g. because of an ambiguous name stub) lead to an error.
     *
     * @param world The world in which the targeted movie set exists.
     * @param information Either contains a set index number or a set name stub.
     * @return A {@link MaybeError} object which is either a {@code Success} with the identified movie set or an {@code Error} with a helpful message.
     */
    public static MaybeError<MovieSet> identifyMovieSet(World world, String information) {

        try {
            // Information string is a number
            int setIndex = Integer.parseInt(information);

            MovieSet movieSet = getNamedMovieSetByIndex(world, setIndex);

            // Case: Given index is invalid
            if (setIndex <= 0) {
                return new MaybeError.Error<>("Set numbers must be >= 1, the provided number " + information + " is therefore invalid.");
            }
            // Case: Movie set with given index is named
            else if (movieSet != null) {
                return new MaybeError.Success<>(movieSet);
            }
            // Case: Index is valid, but movie set is not named -> anonymous movie set
            else {
                return new MaybeError.Success<>(new MovieSet(setIndex, world, null));
            }
        } catch (NumberFormatException e) {
            // Information string is not a number, but a name stub
            List<MovieSet> matchingSets = getNamedMovieSetsByNameStub(world, information);

            if (matchingSets.size() == 0) {
                return new MaybeError.Error<>("There's no set with a name that starts with '" + information + "'.");
            } else if (matchingSets.size() >= 2) {
                return new MaybeError.Error<>("There are " + matchingSets.size() + " sets with names that start with '" + information + "' ('"
                        + StringUtils.join(matchingSets, "', '") + "'); please use a longer input.");
            } else {
                return new MaybeError.Success<>(matchingSets.get(0));
            }
        }
    }

    public static void addNamedMovieSet(MovieSet movieSet) {

        Properties storage = loadProperties(movieSet.getWorld());
        storage.put(String.valueOf(movieSet.getIndex()), movieSet.getName());
        storeProperties(movieSet.getWorld(), storage);
    }

    public static void removeNamedMovieSet(MovieSet movieSet) {

        removeNamedMovieSetByIndex(movieSet.getWorld(), movieSet.getIndex());
    }

    public static void removeNamedMovieSetByIndex(World world, int index) {

        Properties storage = loadProperties(world);
        storage.remove(String.valueOf(index));
        storeProperties(world, storage);
    }

    public static void clearNamedMovieSets(World world) {

        // Just store an empty properties file
        storeProperties(world, new Properties());
    }

    private static Properties loadProperties(World world) {

        Path storageFile = world.getWorldFolder().toPath().resolve(STORAGE_FILE_NAME);

        Properties properties = new Properties();

        if (Files.exists(storageFile)) {
            try (Reader reader = Files.newBufferedReader(storageFile, Charset.forName("UTF-8"))) {
                properties.load(reader);
            } catch (IOException e) {
                log.log(Level.SEVERE, "Can't read the movie set storage file for the world '" + world.getName() + "' (path is '" + storageFile + "').", e);
            }
        }

        return properties;
    }

    private static void storeProperties(World world, Properties properties) {

        Path storageFile = world.getWorldFolder().toPath().resolve(STORAGE_FILE_NAME);

        try (Writer writer = Files.newBufferedWriter(storageFile, Charset.forName("UTF-8"))) {
            properties.store(writer, "Internal MovieSets storage file for this world; do not edit!");
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't write the movie set storage file for the world '" + world.getName() + "' (path is '" + storageFile + "').", e);
        }
    }

    private MovieSetStorage() {

    }

}

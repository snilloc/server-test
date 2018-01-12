package server.db.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.config.AppConfiguration;
import server.data.model.Movie;
import server.db.dao.MovieDao;
import server.exception.DaoDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ListMovieDaoImp implements MovieDao
 *
 * Provides all of the Data Access methods for an Object
 */
public class ListMovieDaoImpl implements MovieDao {

    private List<Movie> movies;
    private static final Logger log = LoggerFactory.getLogger(ListMovieDaoImpl.class.getName());

    /**
     * List Movie Data Access Object
     *
     * @param configuration
     */
    public ListMovieDaoImpl(AppConfiguration configuration) {
        loadData();
    }

    /**
     * Retrieve a list of all of the movies
     *
     * @return
     */
    @Override
    public List<Movie> get() {
        return movies;
    }

    /**
     * Retrieve the Movie information given the movie id
     *
     * @param id - movie id as UUID
     * @return
     * @throws DaoDataException
     */
    public Movie get(UUID id) throws DaoDataException {
        Movie found = movies.stream().filter(n -> n.getId().toString().equals(id.toString())).findFirst().orElse(null);
        return found;
    }

    /**
     * Delete the movie from the database
     *
     * @param id of the movie id
     * @throws DaoDataException if it failed
     */
    @Override
    public void delete(UUID id) throws DaoDataException {
        for (int i=0; i < movies.size(); i++) {
            if (movies.get(i).getId().equals(id)) {
                movies.remove(i);
                return;   // Could be duplicates
            }
        }
    }

    /**
     * Save the Movie to the Database
     *
     * @param movie to be saved as Movie
     * @return movie id as UUID
     * @throws DaoDataException if failed to save
     */
    public UUID save(Movie movie) throws DaoDataException {
        UUID id = UUID.randomUUID();
        movie.setId(id);
        movies.add(movie);
        return id;
    }

    /**
     * Update the Movie with the fields that are populated
     *
     * @param movie to update
     * @throws DaoDataException if the update failed
     */
    public void update(Movie movie) throws DaoDataException {
        if ((movie != null) && (movie.getId() != null)) {
                Movie currentMovie = null;
                for (int i=0; i < movies.size(); i++) {
                    currentMovie = movies.get(i);
                    if (currentMovie.getId().toString().equals(movie.getId().toString())) {
                        String name = (movie.getName() != null)? movie.getName() : currentMovie.getName();
                        String genre = (movie.getGenre() != null)? movie.getGenre() : currentMovie.getGenre();
                        int year = (movie.getYear() > 0)? movie.getYear() : currentMovie.getYear();
                        float rating = (movie.getYear() > 0)? movie.getRating() : currentMovie.getRating();
                        Movie newMovie = new Movie(movie.getId(), name, genre, year, rating);
                        log.info("Removing movie: " + movies.get(i).toString());
                        movies.remove(i);
                        log.info("Adding movie: " + newMovie.toString());
                        movies.add(newMovie);
                        return;
                    }
                }

                // Never found match, then add it
                log.warn("Id did not find a match, so saving the movie");
                save(movie);

        } else {
            log.warn("Id was not provided");
            throw new DaoDataException("Failed to update without a movie id.");
        }
    }

    private void loadData() {
        movies = new ArrayList<>();
        Movie movie = new Movie(UUID.fromString("16af8093-e43b-4756-8d2b-c214ecac6256"), "300", "Action", 2004, 4.9F);
        movies.add(movie);
        movie = new Movie(UUID.fromString("7bd6e7a3-7b00-49e5-a3df-1d56173386dd"), "2Toy Story", "Kids", 2004, 4.9F);
        movies.add(movie);
        movie = new Movie(UUID.fromString("9492a56c-87f8-4015-8810-23bb3743fedf"), "Batman", "Action", 2004, 4.9F);
        movies.add(movie);
    }
}

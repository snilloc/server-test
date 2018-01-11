package server.db.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.config.AppConfiguration;
import server.data.model.Movie;
import server.db.dao.MovieDao;
import server.exception.DaoDataException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class HashMovieDaoImpl implements MovieDao {

    private Set<Movie> movies;
    private static final Logger log = LoggerFactory.getLogger(ListMovieDaoImpl.class.getName());

    public HashMovieDaoImpl(AppConfiguration configuration) {
        loadData();
    }

    @Override
    public List<Movie> get() {
        List<Movie> listMovies = new ArrayList<>(movies.size());
        for (Movie movie : movies) {
            listMovies.add(movie);
        }
        return listMovies;
    }

    public Movie get(UUID id) throws DaoDataException {
        Movie found = movies.stream().filter(n -> n.getId().toString().equals(id.toString())).findFirst().orElse(null);
        return found;
    }

    @Override
    public void delete(UUID id) throws DaoDataException {
        Movie movie = movies.stream().filter(n -> n.getId().toString().equals(id.toString())).findFirst().orElse(null);
        if (movie != null) {
            movies.remove(movie);
        }
    }

    public UUID save(Movie movie) throws DaoDataException {
        UUID id = UUID.randomUUID();
        movie.setId(id);
        movies.add(movie);
        return id;
    }

    public void update(Movie movie) throws DaoDataException {

        if ((movie != null) && (movie.getId() != null)) {
            Movie currentMovie = movies.stream().filter(n -> n.getId().toString().equals(movie.getId().toString())).findFirst().orElse(null);
            if (movie != null) {
                String name = (movie.getName() != null)? movie.getName() : currentMovie.getName();
                String genre = (movie.getGenre() != null)? movie.getGenre() : currentMovie.getGenre();
                int year = (movie.getYear() > 0)? movie.getYear() : currentMovie.getYear();
                float rating = (movie.getYear() > 0)? movie.getRating() : currentMovie.getRating();
                Movie newMovie = new Movie(currentMovie.getId(), name, genre, year, rating);
                movies.remove(currentMovie);
                movies.add(newMovie);
                return;
            } else {
                // Never found match, then add it
                log.warn("Id did not find a match, so saving the movie");
                save(movie);
            }

        } else {
            // Id never provided, so save instead
            log.warn("Id was not provided");
            //save(movie);
            throw new DaoDataException("Failed to update without a movie id.");
        }
    }

    private void loadData() {
        movies = new HashSet<>();
        UUID id = UUID.randomUUID();
        Movie movie = new Movie(UUID.fromString("16af8093-e43b-4756-8d2b-c214ecac6256"), "300", "Action", 2004, 4.9F);
        movies.add(movie);
        id = UUID.randomUUID();
        movie = new Movie(UUID.fromString("7bd6e7a3-7b00-49e5-a3df-1d56173386dd"), "2Toy Story", "Kids", 2004, 4.9F);
        movies.add(movie);
        id = UUID.randomUUID();
        movie = new Movie(UUID.fromString("9492a56c-87f8-4015-8810-23bb3743fedf"), "Batman", "Action", 2004, 4.9F);
        movies.add(movie);
    }
}

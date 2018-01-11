package server.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.config.AppConfiguration;
import server.data.model.Movie;
import server.db.dao.MovieDao;
import server.db.dao.impl.ListMovieDaoImpl;
import server.exception.DaoDataException;
import server.exception.MovieWebException;
import server.exception.ServiceException;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;


public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class.getName());

    private MovieDao movieDao;
    private MovieTransformer movieTransformer;

    public MovieService(AppConfiguration configuration) {
        movieDao = new ListMovieDaoImpl(configuration);
        movieTransformer = new MovieTransformer(configuration.getTemplate(), configuration.getDefaultName());
    }

    public Object process(String method, Object request) throws ServiceException, MovieWebException {
        // Handle Request
        switch(method) {
            case HttpMethod.GET:
                return get((String) request);
            case HttpMethod.DELETE:
                delete((UUID) request);
                return Response.ok();
            case HttpMethod.POST:
                return save((Movie) request);
            case "PATCH":
            case HttpMethod.PUT:
                return update((Movie) request);
            case "SEARCH":
                 throw new MovieWebException(Response.Status.NOT_IMPLEMENTED, "Still under development");
            case HttpMethod.HEAD:
            case HttpMethod.OPTIONS:
            default:
                throw new MovieWebException(Response.Status.METHOD_NOT_ALLOWED, "ONLY GET, DELETE, POST, PUT, PATCH are supported.");
        }
    }

    private String get(String id) throws ServiceException {
        try {
            // fetch data from database
            if (id == null) {
                List<Movie> movies = movieDao.get();
                return movieTransformer.transform(movies);
            } else {
                Movie movie = movieDao.get(UUID.fromString(id));
                if (movie != null) {
                    return movieTransformer.transform(movie);
                } else {
                    log.error("Failed to retrieve movie id: " + id);
                    throw new MovieWebException(Response.Status.NOT_FOUND, "Failed to retrieve movie id: " + id);
                }
            }
        } catch (Exception ex) {
            log.error("Failed to find id", ex);
            throw new ServiceException(ex);
        }
    }

    private long update(Movie movie) throws ServiceException {
        try {
            movieDao.update(movie);
            return 0;
        } catch (DaoDataException ex) {
            log.error("Failed to update id: " + movie.getId(), ex);
            throw new ServiceException("Failed to update id: " + movie.getId(), ex);
        }
    }

    private void delete(UUID id) throws ServiceException {
        try {
            movieDao.delete(id);
            return;
        } catch (DaoDataException ex) {
            log.error("Failed to delete id: " + id, ex);
            throw new ServiceException(ex);
        }
    }

    /**
     * Saves the Movie
     * @param movie
     * @return
     * @throws ServiceException
     */
    private UUID save(Movie movie) throws ServiceException {
        try {
            UUID id = movieDao.save(movie);
            return id;
        } catch (DaoDataException ex) {
            throw new ServiceException(ex);
        }
    }

    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

}

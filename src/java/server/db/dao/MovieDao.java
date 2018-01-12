package server.db.dao;

import server.data.model.Movie;
import server.exception.DaoDataException;

import java.util.List;
import java.util.UUID;

/**
 * Movie Data Access Object Interface Definition
 *
 */
public interface MovieDao {

    /**
     * Delete the Move from the Data Access Object
     *
     * @param id of the movie id
     * @throws DaoDataException
     */
    public void delete(UUID id) throws DaoDataException;

    /**
     * Update the movie
     * @param movie to update
     * @throws DaoDataException
     */
    public void update(Movie movie) throws DaoDataException;

    /**
     * SAve or insert new Movie into the Data Access Object
     *
     * @param movie
     * @return the new UUID of the movie
     * @throws DaoDataException
     */
    public UUID save(Movie movie) throws DaoDataException;

    /**
     * Retrieve the Movie for a given movie id
     * @param id - movie id as UUID
     * @return Movie if found or null if not found
     * @throws DaoDataException
     */
    public Movie get(UUID id) throws DaoDataException;

    /**
     * Retrieve all of the Movies from the DataAccess Object
     *
     * @return
     * @throws DaoDataException
     */
    public List<Movie> get() throws DaoDataException;

}

package server.db.dao;

import server.data.model.Movie;
import server.exception.DaoDataException;

import java.util.List;
import java.util.UUID;

public interface MovieDao {

    public void delete(UUID id) throws DaoDataException;

    public void update(Movie movie) throws DaoDataException;

    public UUID save(Movie movie) throws DaoDataException;

    public Movie get(UUID id) throws DaoDataException;

    /**
     * Retrieve all of the Movies
     *
     * @return
     * @throws DaoDataException
     */
    public List<Movie> get() throws DaoDataException;

    /* TODO  Future
    public List<Movie> get(String name) throws DaoDataException;

    public List<Movie> getByGenre(String genre) throws DaoDataException;

    public List<Movie> get(int year) throws DaoDataException;

    public List<Movie> get(float rating) throws DaoDataException;
    */

}

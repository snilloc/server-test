package server.db.dao.impl;

import server.config.AppConfiguration;
import server.data.model.Movie;
import server.db.dao.MovieDao;
import server.exception.DaoDataException;

import java.util.List;
import java.util.UUID;

public class H2MovieDaoImpl implements MovieDao {


    public H2MovieDaoImpl(AppConfiguration configuration) {

    }

    public void delete(UUID id) throws DaoDataException {

    };

    public void update(Movie movie) throws DaoDataException {

    };

    public UUID save(Movie movie) throws DaoDataException {
        return UUID.randomUUID();
    };

    public Movie get(UUID id) throws DaoDataException {
        return null;
    };

    public List<Movie> get() throws DaoDataException {
        return null;
    }



}

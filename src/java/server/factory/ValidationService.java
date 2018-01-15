package server.factory;

import server.data.model.Movie;
import server.exception.ServiceException;

public interface ValidationService {


    /**
     * Validate the Movie fields are valid and within the range.
     *
     * @param movie
     */
    public void validate(Movie movie) throws ServiceException;
}

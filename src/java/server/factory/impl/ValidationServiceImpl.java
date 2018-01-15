package server.factory.impl;

import server.data.model.Movie;
import server.exception.ServiceException;
import server.factory.ValidationService;

/**
 *
 */
public class ValidationServiceImpl implements ValidationService {

    public void validate(Movie movie) throws ServiceException {
        if ((movie.getYear() < 0) || (movie.getYear() > 2018)) {
            throw new ServiceException("Invalid Year!  Must be between 0-2018.  Year: " + movie.getYear());
        }

        if ((movie.getRating() < -1) || (movie.getRating() > 5.0)) {
            throw new ServiceException("Invalid Rating.  Must be between 0.0-5.0");
        }

        if ((movie.getName().length() > 255)) {
            throw new ServiceException("Name of Movie too long.  Must be less than 255 characters");
        }

        if ((movie.getGenre().length() > 255)) {
            throw new ServiceException("Genre of Movie too long.  Must be less than 255 characters");
        }
    }
}

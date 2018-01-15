package server.factory;

import server.db.dao.MovieDao;
import server.exception.MovieWebException;
import server.exception.ServiceException;

public interface MovieService {

    /**
     * Process for handling all the different request for Movie
     *
     * @param method
     * @param request
     * @return Object of the results (UUID when insert, json
     * @throws ServiceException - when an error occurrs in process the request
     * @throws MovieWebException - when the client request is invalid
     */
    public Object process(String method, Object request) throws ServiceException, MovieWebException;


    /**
     * Override the default Movie DAO
     *
     * @param movieDao
     */
    public void setMovieDao(MovieDao movieDao);

    /**
     * Override the default Validation Service
     * @param validationService
     */
    public void setValidationService(ValidationService validationService);

}

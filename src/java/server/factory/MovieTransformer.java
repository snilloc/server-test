package server.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.data.model.Movie;
import server.exception.ServiceException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Movie Transformer
 */
public class MovieTransformer {

    private String template;
    private String defaultName;
    private static final Logger log = LoggerFactory.getLogger(MovieTransformer.class.getName());
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Movie Transformer Constructor
     *
     * @param template
     * @param defaultName
     */
    MovieTransformer(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
    }

    /**
     * Transform the Movie Object into a Json String
     *
     * @param movie as Movie Object
     * @return json String of the Movie object
     * @throws ServiceException - in processing the object
     */
    public String transform(Movie movie) throws ServiceException {
        try {
            return mapper.writeValueAsString(movie);
        } catch (JsonProcessingException ex) {
            log.error("Failed to transform movie");
            throw new ServiceException(ex);
        }
    }

    /**
     * Transform a List of Movie Objects into a Json String
     *
     * @param movies
     * @return
     * @throws ServiceException
     */
    public String transform(List<Movie> movies) throws ServiceException {
        try {
            ByteArrayOutputStream results = new ByteArrayOutputStream();
            mapper.writeValue(results, movies);
            final byte[] data = results.toByteArray();
            return new String(data, "UTF8");
        } catch (IOException ex) {
            log.error("Failed to transform movies into Json Object");
            throw new ServiceException(ex);
        }
    }

    /**
     * Tranform a String Movie Json Object to a Movie Object
     *
     * @param movie as a json string object
     * @return Movie object
     * @throws ServiceException if failed to process an json string
     */
    public Movie transform(String movie) throws ServiceException {
        try {
            Movie result = mapper.readValue(movie, Movie.class);
            return result;
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }
}

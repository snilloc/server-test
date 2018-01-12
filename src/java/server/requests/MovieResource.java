package server.requests;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.config.AppConfiguration;
import server.data.model.Movie;
import server.exception.MovieWebException;
import server.exception.ServiceException;
import server.factory.MovieService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.UUID;

/**
 *
 */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

    private static Logger log = LoggerFactory.getLogger(MovieResource.class.getName());

    private final String template;
    private final String defaultName;
    private final MovieService movieService;

    public MovieResource(AppConfiguration configuration) {
        this.template = configuration.getTemplate();
        this.defaultName = configuration.getDefaultName();
        movieService = new MovieService(configuration);
    }

    /**
     * Retrieve a move based upon the unique movie id
     *
     * @param id
     * @return Json Movie Response
     */
    @GET
    @Path("/movie/{id}")
    @Timed
    public String get(@PathParam("id") Optional<String> id) {
        try {
            if (id.isPresent()) {
                log.info("Getting Movie: " + id.get());
                return (String) movieService.process(HttpMethod.GET, id.get());
            } else {
                log.error("BAD_REQUEST!  Missing id");
                throw new MovieWebException(Response.Status.BAD_REQUEST, "Missing id: " + id.get());
            }
        } catch (ServiceException ex) {
            log.error("Failed to retrieve id: " + id.get(), ex);
            throw new MovieWebException(Response.Status.NOT_FOUND, ex.getMessage());
        }
    }

    /**
     * Retrieve a list of available movies
     *
     * @return Json list of movies responses as a String
     */
    @GET
    @Path("/movie/list")
    @Timed
    public Object get() {
        try {
            log.info("Getting all of the movies");
            String jsonMovies =  (String) movieService.process(HttpMethod.GET, null);
            return jsonMovies;
        } catch(ServiceException ex) {
            log.error("Error: in retrieving a list of movies", ex);
            throw new MovieWebException(Response.Status.NOT_FOUND, "Database Down");
        }
    }

    /**
     * Save a movie to a database
     *
     * @param name - name of the movie (required)
     * @param genre - genre of the movie (optional)
     * @param year - year of when the movie was released (optional)
     * @param rating - rating of the movie (0-5) (optional)
     * @return json response with unique movie id (optional)
     */
    @POST
    @Path("/movie/create")
    @Timed
    public String create(@QueryParam("name") String name, @QueryParam("genre") Optional<String> genre,
                         @QueryParam("year") Optional<String> year, @QueryParam("rating") Optional<String> rating) {
        try {
            if (name != null) {
                log.info("Saving Movie: " + name + " genre: " + genre.orElse(null) + " year" + year.orElse(null));
                Movie movie = new Movie(null, name, genre.orElse(null),
                        year.orElse("-2"), rating.orElse("-1.0"));
                UUID id = (UUID) movieService.process(HttpMethod.POST, movie);
                return getMessage(id.toString(), 1);
            } else {
                log.error("Missing Name from Movie");
                throw new MovieWebException(Response.Status.BAD_REQUEST, "Name is Rquired.  'name' is Missing.");
            }
        } catch (ServiceException ex) {
            log.error("Error: in saving movie : " + name, ex);
            throw new MovieWebException(Response.Status.BAD_REQUEST, "Error in saving move: " + name);
        }
    }

    private String getMessage(String id, int num) {
        StringBuilder builder = new StringBuilder();

        switch(num) {
            case 1:
                builder.append("[  {\"Status\":\"CREATED\", \"ID\":\"").append(id).append("\" } ]");
                break;
            case 2:
                //return Response.status(Response.Status.OK).entity("id was updated").build().toString();
                builder.append("[  {\"Status\":\"OK\", \"ID\":\"").append(id).append("\" } ]");
                break;
            case 3:
                //return Response.status(Response.Status.NO_CONTENT).entity("id was deleted").build().toString();
                builder.append("[  {\"Status\":\"NO_CONTENT\", \"ID\":\"").append(id).append("\" } ]");
                break;
        }

        return builder.toString();
    }

    /**
     *
     * @param id
     * @param name
     * @param genre
     * @param year
     * @param rating
     * @return
     */
    @PUT
    @Path("/movie/update")
    @Timed
    public String update(@QueryParam("id") String id,
                         @QueryParam("name") Optional<String> name, @QueryParam("genre") Optional<String> genre,
                        @QueryParam("year") Optional<String> year, @QueryParam("rating") Optional<String> rating) {
        try {
            if (id == null) {
               log.error("Invalid Request! Missing movie id in Update");
               // Throws Server Error 500 - when not a server issue, but a client issue
               // throw new IllegalArgumentException("Missing id in Update");
              // return "\"Status\":\"404\" Invalid Request!  Missing id";
               throw new MovieWebException(Response.Status.BAD_REQUEST, "Missing movie id.");
            }

            log.info("Found : " + id + " name: " + name.orElse("") + " genre: " + genre.orElse(""));
            Movie movie = new Movie(UUID.fromString(id), name.orElse(null), genre.orElse(null),
                year.orElse("-1"), rating.orElse("-1.0F"));
            log.info("Updating Movie: " + movie.toString());
            movieService.process(HttpMethod.PUT, movie);
            return getMessage(id.toString(), 2);
        } catch (ServiceException ex) {
            log.error("Error: in updating id: " + id, ex);
            throw new MovieWebException(Response.Status.NOT_MODIFIED, "Error in updating id: " + id);
        }
    }

    /**
     * Delete a movie based upon the unqiue movie id
     *
     * @param id - movie id
     * @return
     */
    @DELETE
    @Path("/movie/delete/{id}")
    @Timed
    public String delete(@PathParam("id") String id) {
        try {
            if (id != null) {
                log.info("Deleting Movie: " + id);
                UUID uuid = UUID.fromString(id);
                movieService.process(HttpMethod.DELETE, uuid);
                return getMessage(uuid.toString(), 3);
            } else {
                log.error("Missing movie id");
                throw new MovieWebException(Response.Status.BAD_REQUEST, "Missing movie id");
            }
        } catch (ServiceException ex) {
            log.error("Error: in deleting id: " + id, ex);
            throw new MovieWebException(Response.Status.NOT_FOUND, "Error in deleting id: " + id);
        }
    }
}


package server;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.config.AppConfiguration;
import server.data.model.Movie;
import server.requests.MovieResource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.UUID;

/**
 * Regression Test for Movie Resource
 *
 */
public class MovieResourceValidTest {


    MovieResource movieResource;
    String port = "8090";

    AppConfiguration config = new AppConfiguration();

    /*
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MovieResource(config))
            .build(); */

    @Before
    public void setup() {
        AppConfiguration app = new AppConfiguration();
        movieResource = new MovieResource(app);
    }

    @After
    public void tearDown(){
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        //reset(dao);
    }

    @Test
    public void testGetInvalidId() {

        UUID id = UUID.randomUUID();
        Optional<String> oid = Optional.of(id.toString());
        try {
            String movie = (String) movieResource.get(oid);
            Assert.fail("Exception for not found should have been thrown");
        } catch (WebApplicationException ex) {
            //Assert.assertThat("Movie Excpetion", MovieWebException, ex); // .assertTrue(movie.contains("Status"));
        }
    }


    public void testPostMovie() {
        Client client = ClientBuilder.newClient();
        String url = "http://localhost:"+ port + "/api/movie";
        WebTarget webTarget = client.target(url);
        Movie movie = new Movie(UUID.randomUUID(), "Good Times", "Drama", 1978, 3.4F);
        Entity<Movie> request = null; // new Entity<Movie>(movie);
        Response response = webTarget.request().put(request); //String.class);
        System.out.println(response);
    }

    @Test
    public void testDeleteMovieId() {
        /*
        Client client = ClientBuilder.newClient();
        String url = "http://localhost:"+ port + "/api/movie/7bd6e7a3-7b00-49e5-a3df-1d56173386dd";

        WebTarget webTarget = client.target(url);
        String response = webTarget.request().delete(String.class);
        System.out.println(response); */
        String movies = (String) movieResource.delete("7bd6e7a3-7b00-49e5-a3df-1d56173386dd");

    }


    @Test
    public void testGetAllMovies() {
        /*
        Client client = ClientBuilder.newClient();
        String url = "http://localhost:"+ port + "/api/movie";
        WebTarget webTarget = client.target(url);
        String response = webTarget.request().get(String.class);
        System.out.println(response);
        */
        String movies = (String) movieResource.get();
        Assert.assertNotNull(movies);
    }

    @Test
    public void testGetMovieId() {
        /*
        Client client = ClientBuilder.newClient();
        String uuid ="16af8093-e43b-4756-8d2b-c214ecac6256";
        String url = "http://localhost:"+ port + "/api/movies/"+uuid;
        WebTarget webTarget = client.target(url);
        String response = webTarget.request().get(String.class);
        System.out.println(response); */
        Optional<String> uuid = Optional.of("16af8093-e43b-4756-8d2b-c214ecac6256");
        String movies = (String) movieResource.get(uuid);
        Assert.assertTrue(movies.contains("16af8093-e43b-4756-8d2b-c214ecac6256"));
    }

}

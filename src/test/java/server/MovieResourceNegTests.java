package server;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import server.config.AppConfiguration;
import server.requests.MovieResource;

import javax.ws.rs.WebApplicationException;
import java.util.Optional;
import java.util.UUID;

public class MovieResourceNegTests {

    MovieResource movieResource = null;

    @ClassRule
//      public static final DropwizardClientRule dropwizard = new DropwizardClientRule(new MovieResource(new AppConfiguration()));

    /*
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MovieResource(new AppConfiguration()))
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
    public void testDeleteInvalidId() {
        try {
            // This random UUID should fail
            movieResource.delete(UUID.randomUUID().toString());
            Assert.fail();
        } catch (Exception ex) {

        }
    }

    @Test
    public void testPostInvalidMovieYear() {
        try {
            String uuid ="16af8093-e43b-4756-8d2b-c214ecac6256";
            movieResource.update(uuid, Optional.of("testName"), null, Optional.of("-32017"), Optional.of("1.0"));
            Assert.fail("Invalid Year value should have thrown an exemption");
        } catch( Exception ex) {

        }

    }

    @Test
    public void testPostInvalidRating() {
        try {
            String uuid ="16af8093-e43b-4756-8d2b-c214ecac6256";
            movieResource.update(uuid, Optional.of("testName"), null, Optional.of("2013"), Optional.of("187.0"));
            Assert.fail("Invalid Rating value should have thrown an exemption");
        } catch( Exception ex) {

        }

    }


    @Test
    public void testPutInvalidId() {
        try {
            String uuid ="16af8093-e43b-4756-8d2b-c214ecac6256";
            movieResource.update(uuid, Optional.of("testName"), null, Optional.of("2017"), Optional.of("1.0"));
            Assert.fail("Invalid Id should throw an exemption");
        } catch( Exception ex) {

        }
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

}

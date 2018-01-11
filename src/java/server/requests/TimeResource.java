package server.requests;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public class TimeResource {

    private final Logger log = LoggerFactory.getLogger(TimeResource.class.getName());

    /**
     *
     * @return
     */
    @GET
    @Path("timeOfDay")
    @Timed
    public String getTimeOfDay() {

        // Search for movie with name
        long time = System.currentTimeMillis();

        log.info("Time is : " + time);
        return "Time is: " + time;
    }
}

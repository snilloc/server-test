package server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.config.AppConfiguration;
import server.healthcheck.AppHealthCheck;

/**
 * Main application
 */
public class MainApplication extends Application<AppConfiguration>
{
	final static Logger logger = LoggerFactory.getLogger(MainApplication.class);


	public static void main(String[] args) throws Exception
	{
        new MainApplication().run(args);
    }

    @Override
    public String getName()
	{
        return "Test Server";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap)
	{
        // framework bootstrap initialization
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception
	{
		try
		{
			logger.info("Starting...");

			// application initialization goes here
		}
		catch (Exception exc)
		{
			// log failure to set up app
			logger.error("Failed to initialize application, exiting...", exc);
			throw new RuntimeException();
		}

		environment.healthChecks().register("app", new AppHealthCheck());

        // register servlet route handlers
		// environment.jersey().register(new YourServlet());
    }
}

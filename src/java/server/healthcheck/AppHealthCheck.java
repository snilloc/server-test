package server.healthcheck;

import com.codahale.metrics.health.HealthCheck;

public class AppHealthCheck extends HealthCheck
{

	private final String template;

	public AppHealthCheck(String template) {
		this.template = template;
	}


	@Override
	protected Result check() throws Exception
	{
		return Result.healthy();
	}
}

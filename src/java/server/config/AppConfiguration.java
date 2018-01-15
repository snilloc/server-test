package server.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Application Configuration
 */
public class AppConfiguration extends Configuration
{

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName;

    // From the YAML file
    @NotEmpty
    private String driverClass;
    @NotEmpty
    private String user;
    @NotEmpty
    private String password;
    @NotEmpty
    private String connection;

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @JsonProperty
    public Connection getDbConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(connection, user, password);
        } catch (SQLException ex) {
            System.out.println("ERROR connection to DB");
        }
        return dbConnection;
    }

    @JsonProperty
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @JsonProperty
    public void setUser(String user) {
        this.user = user;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    public void setConnection(String connection) {
        this.connection = connection;
    }
}

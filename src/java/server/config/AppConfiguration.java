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
    private String template; // = "movie-api";

    @NotEmpty
    private String defaultName; // = "Movies";

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

    // From the YAML file
    private String driverClass;
    private String user;
    private String password;
    private String connection;

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

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
    /*
    private String getDbDriver() {
        return "org.h2.Driver";
    }

    private String getConnection() {
        return "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    } */

}

package server.db.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.config.AppConfiguration;
import server.data.model.Movie;
import server.db.dao.MovieDao;
import server.exception.DaoDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class H2MovieDaoImpl implements MovieDao {

    private Connection connection;
    private static Logger log = LoggerFactory.getLogger(H2MovieDaoImpl.class.getName());

    // TABLE
    private final String CREATE_MOVIE_TABLE_SQL = "CREATE_TABLE MOVIE(ID UUID PRIMARY KEY, " +
            "NAME VARCHAR(255), GENRE VARCHAR(255), YEAR SMALLINT, RATING FLOAT)";

    // SQL
    private final String GET_MOVIE_BY_ID_SQL = "SELECT * FROM MOVIE WHERE ID = ?";
    private final String GET_ALL_MOVIE_SQL = "SELECT * FROM MOVIE";
    private final String DELETE_MOVIE_BY_ID_SQL = "DELETE MOVIE WHERE ID = ?";
    private final String UPDATE_MOVIE_BY_ID_SQL= "UPDATE MOVIE SET NAME=? GENRE=? YEAR=? RATING=? WHERE ID = ?";

    public H2MovieDaoImpl(AppConfiguration configuration) throws DaoDataException {
        PreparedStatement createStatement = null;
        try {
            connection = configuration.getDbConnection();
            connection.setAutoCommit(false);

            createStatement = connection.prepareStatement(CREATE_MOVIE_TABLE_SQL);
            createStatement.executeUpdate();
        } catch(SQLException ex) {
            throw new DaoDataException(ex);
        } catch(Exception ex) {
            throw new DaoDataException(ex);
        } finally {
            close(null, createStatement, null);
        }
    }

    public void delete(UUID id) throws DaoDataException {
        PreparedStatement deleteStatement  =null;
         try {
             deleteStatement = connection.prepareStatement(DELETE_MOVIE_BY_ID_SQL);
             deleteStatement.setString(1, id.toString());
             deleteStatement.executeUpdate();
         } catch (SQLException ex) {
             throw new DaoDataException(ex);
         } catch (Exception ex) {
             throw new DaoDataException(ex);
         } finally {
             close(null, deleteStatement, null);
         }
    }

    public void update(Movie movie) throws DaoDataException {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement(UPDATE_MOVIE_BY_ID_SQL);
            updateStatement.executeUpdate();
            updateStatement.close();
        } catch (SQLException ex) {
            throw new DaoDataException(ex);
        } finally {
            close(null, updateStatement, null);
        }
    };

    public UUID save(Movie movie) throws DaoDataException {
        PreparedStatement insertStatement = null;
        try {
            UUID newId = UUID.randomUUID();
            movie.setId(newId);

            // Check if newID does not exists


            // Save movie to DB
            insertStatement = connection.prepareStatement(UPDATE_MOVIE_BY_ID_SQL);
            insertStatement.setString(1, movie.getId().toString());
            insertStatement.setString(2, movie.getName());
            insertStatement.setString(3, movie.getGenre());
            insertStatement.setInt(4, movie.getYear());
            insertStatement.setFloat(5, movie.getYear());
            insertStatement.executeUpdate();
//            stmt.execute("INSERT INTO MOVIE(ID, NAME, GENRE, YEAR, RATING) " +
 //                   "VALUES(newId, 'Anju', 'Family', 2010, 3.4");
            return movie.getId();
        } catch (SQLException ex) {
            throw new DaoDataException(ex);
        } finally {
            close(null, insertStatement, null);
        }
    }

    public Movie get(UUID id) throws DaoDataException {
        PreparedStatement getStatement = null;
        try {
            getStatement = connection.prepareStatement(GET_MOVIE_BY_ID_SQL);
            getStatement.setString(1, id.toString());
            ResultSet rs = getStatement.executeQuery();
            Movie movie = null;
            while (rs.next()) {
                String name = rs.getString("NAME");
                String genre = rs.getString("GENRE");
                Integer year = rs.getInt("YEAR");
                Float rating = Float.parseFloat(rs.getString("RATING"));
                System.out.println("ID: " + id.toString() + " NAME: " + name + " GENRE: " + genre +
                        " YEAR:" + year + " RATING:" + rating);
                movie = new Movie(id, name, genre, year, rating);
            }
            return movie;
        } catch (SQLException ex) {
            throw new DaoDataException(ex);
        } finally {
            close(null, getStatement, null);
        }
    };

    public List<Movie> get() throws DaoDataException {

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_MOVIE_SQL);
            System.out.println("H2 In-Memory Database inserted through Statement");
            List<Movie> movies = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String genre = rs.getString("GENRE");
                Integer year = rs.getInt("YEAR");
                Float rating = Float.parseFloat(rs.getString("RATING"));
                System.out.println("ID: " + id + " NAME: " + name + " GENRE: " + genre +
                        " YEAR:" + year + " RATING:" + rating);
                UUID uid = UUID.fromString(id);
                Movie movie = new Movie(uid, name, genre, year, rating);
                movies.add(movie);
            }
            stmt.close();
            return movies;
        } catch (NumberFormatException ex) {
            throw new DaoDataException(ex);
        } catch (SQLException ex) {
            throw new DaoDataException(ex);
        } finally {
            close(null, stmt, null);
        }
    }

    public static void close(ResultSet rs, Statement ps, Connection conn)
    {
        if (rs!=null)
        {
            try
            {
                rs.close();

            }
            catch(SQLException e)
            {
                log.error("The result set cannot be closed.", e);
            }
        }
        if (ps != null)
        {
            try
            {
                ps.close();
            } catch (SQLException e)
            {
                log.error("The statement cannot be closed.", e);
            }
        }
        if (conn != null)
        {
            try
            {
                conn.close();
            } catch (SQLException e)
            {
                log.error("The data source connection cannot be closed.", e);
            }
        }

    }

}

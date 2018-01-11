package server.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public class Movie {

    private UUID id;

    private final String name;

    private final String genre;

    @Length(max=4)
    private final int year;

    private final float rating;


    public Movie(UUID id, String name, String genre, int year, float rating) {

        this.id = id;
        this.name = name;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }

    public Movie(UUID id, String name, String genre, String year, String rating) {
            this.id = id;
            this.name = name;
            this.genre = genre;
            this.year = Integer.parseInt(year);
            this.rating = Float.parseFloat(rating);
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty
    public UUID getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getGenre() {
        return genre;
    }

    @JsonProperty
    public int getYear() {
        return year;
    }

    @JsonProperty
    public float getRating() {
        return rating;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\"id\"").append(id);
        builder.append("\"name\"").append(name);
        builder.append("\"genre\"").append(genre);
        builder.append("\"year\"").append(year);
        builder.append("\"rating\"").append(rating);
        return builder.toString();
    }
}

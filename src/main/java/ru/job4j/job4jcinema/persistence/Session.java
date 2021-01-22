package ru.job4j.job4jcinema.persistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Session {
    private int id;
    private int hallId;
    private LocalDateTime dataStart;
    private String movieTitle;

    public Session() {
    }

    public Session(int id, int hallId, LocalDateTime dataStart, String movieTitle) {
        this.id = id;
        this.hallId = hallId;
        this.dataStart = dataStart;
        this.movieTitle = movieTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHallId() {
        return hallId;
    }



    public void setDataStart(LocalDateTime dataStart) {
        this.dataStart = dataStart;
    }

    public String getDataStartString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        return dataStart.format(dtf);
    }

    public String getMovieTitle() {
        return movieTitle;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return id == session.id && hallId == session.hallId
                && Objects.equals(dataStart, session.dataStart)
                && Objects.equals(movieTitle, session.movieTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hallId, dataStart, movieTitle);
    }
}

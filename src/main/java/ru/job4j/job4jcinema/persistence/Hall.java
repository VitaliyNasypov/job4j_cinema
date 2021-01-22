package ru.job4j.job4jcinema.persistence;

import java.util.Objects;

public class Hall {
    private int id;
    private String title;
    private int numberOfPlaces;
    private int numberOfRows;
    private int capacity;

    public Hall() {
    }

    public Hall(int id, String title, int numberOfPlaces, int numberOfRows, int capacity) {
        this.id = id;
        this.title = title;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfRows = numberOfRows;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hall hall = (Hall) o;
        return id == hall.id && numberOfPlaces == hall.numberOfPlaces
                && numberOfRows == hall.numberOfRows && capacity == hall.capacity
                && Objects.equals(title, hall.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, numberOfPlaces, numberOfRows, capacity);
    }
}

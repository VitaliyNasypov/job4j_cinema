package ru.job4j.job4jcinema.persistence;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
    private int id;
    private LocalDateTime dataOfSale;
    private int sessionId;
    private int row;
    private int place;
    private int price;
    private String status;
    private String nameUser;
    private String phoneUser;

    public Ticket() {
    }

    public Ticket(LocalDateTime dataOfSale, int sessionId, int row, int place,
                  int price, String nameUser, String phoneUser) {
        this.dataOfSale = dataOfSale;
        this.sessionId = sessionId;
        this.row = row;
        this.place = place;
        this.price = price;
        this.nameUser = nameUser;
        this.phoneUser = phoneUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id && sessionId == ticket.sessionId
                && row == ticket.row && place == ticket.place && price == ticket.price
                && Objects.equals(dataOfSale, ticket.dataOfSale)
                && Objects.equals(status, ticket.status)
                && Objects.equals(nameUser, ticket.nameUser)
                && Objects.equals(phoneUser, ticket.phoneUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataOfSale, sessionId, row, place, price, status,
                nameUser, phoneUser);
    }
}


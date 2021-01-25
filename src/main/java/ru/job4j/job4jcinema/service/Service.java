package ru.job4j.job4jcinema.service;

import ru.job4j.job4jcinema.persistence.Hall;
import ru.job4j.job4jcinema.persistence.Ticket;

import java.util.List;

public interface Service {
    boolean buyTicket(int row, int place, int sessionId, String nameUser, String phoneUser);

    Hall getHall(int sessionId);

    List<Ticket> getPurchasedSeats(int sessionId, String statusTicket);
}

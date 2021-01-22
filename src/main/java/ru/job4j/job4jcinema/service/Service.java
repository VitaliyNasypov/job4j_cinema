package ru.job4j.job4jcinema.service;

import ru.job4j.job4jcinema.persistence.Hall;

public interface Service {
    boolean buyTicket(int row, int place, int sessionId, String nameUser, String phoneUser);

    Hall getHall(int sessionId);

    String[] getPurchasedSeats(int sessionId, String statusTicket);
}

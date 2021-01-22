package ru.job4j.job4jcinema.service;

import ru.job4j.job4jcinema.persistence.Hall;
import ru.job4j.job4jcinema.persistence.Session;
import ru.job4j.job4jcinema.persistence.Ticket;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ServiceFake implements Service {
    private static final ServiceFake INST = new ServiceFake();
    private static final AtomicInteger HALL_ID = new AtomicInteger(1);
    private static final AtomicInteger TICKET_ID = new AtomicInteger(2);
    private static final AtomicInteger SESSION_ID = new AtomicInteger(1);
    private Map<Integer, Hall> halls = new HashMap<>();
    private Map<Integer, Ticket> tickets = new HashMap<>();
    private Map<Integer, Session> sessions = new HashMap<>();

    private ServiceFake() {
        Hall hall = new Hall(1, "Green", 9, 5, 45);
        Session session = new Session(1, 1,
                LocalDateTime.of(2021, 01, 23, 10, 20),
                "The Best Movie 0+");
        Ticket ticketOne = new Ticket(
                LocalDateTime.of(2021, 01, 19, 15, 23, 46),
                session.getId(), 2, 4, 200, "User One",
                "81234567890");
        ticketOne.setId(1);
        ticketOne.setStatus("Sold");
        Ticket ticketTwo = new Ticket(
                LocalDateTime.of(2021, 01, 19, 12, 23, 46),
                session.getId(), 2, 4, 200, "User Two",
                "81234567890");
        ticketTwo.setId(1);
        ticketTwo.setStatus("Sold");
        halls.put(1, hall);
        tickets.put(1, ticketOne);
        tickets.put(2, ticketTwo);
        sessions.put(1, session);
    }

    public static Service instOf() {
        return INST;
    }

    @Override
    public boolean buyTicket(int row, int place, int sessionId, String nameUser, String phoneUser) {
        boolean result = true;
        for (Map.Entry<Integer, Ticket> entry : tickets.entrySet()) {
            int sessionIdCompare = entry.getValue().getSessionId();
            int rowCompare = entry.getValue().getRow();
            int placeCompare = entry.getValue().getPlace();
            if (row == rowCompare && place == placeCompare && sessionId == sessionIdCompare) {
                result = false;
            }
            if (result) {
                Ticket ticketAdd = new Ticket(LocalDateTime.now(),
                        sessionId, row, place, 200, nameUser, phoneUser);
                ticketAdd.setId(TICKET_ID.incrementAndGet());
                ticketAdd.setStatus("Sold");
                tickets.put(ticketAdd.getId(), ticketAdd);
            }
        }
        return result;
    }

    @Override
    public Hall getHall(int sessionId) {
        int hallId = sessions.get(sessionId).getHallId();
        return halls.get(hallId);
    }

    @Override
    public String[] getPurchasedSeats(int sessionId, String statusTicket) {

        return new String[0];
    }
}

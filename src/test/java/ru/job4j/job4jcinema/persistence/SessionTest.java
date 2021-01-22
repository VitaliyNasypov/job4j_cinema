package ru.job4j.job4jcinema.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SessionTest {
    @Test
    public void shouldGetCorrectFormatString() {
        Session session = new Session();
        session.setDataStart(LocalDateTime.of(2021, 01, 23, 10, 20));
        Assertions.assertEquals("10:20 23.01.2021", session.getDataStartString());
    }
}

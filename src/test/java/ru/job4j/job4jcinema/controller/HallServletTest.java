package ru.job4j.job4jcinema.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;
import ru.job4j.job4jcinema.service.Service;
import ru.job4j.job4jcinema.service.ServiceFake;
import ru.job4j.job4jcinema.service.ServicePsql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ServicePsql.class, LoggerFactory.class})
public class HallServletTest {

    @Test
    public void shouldReturnCorrectJson() throws IOException {
        Service service = ServiceFake.instOf();
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.mockStatic(ServicePsql.class);
        Mockito.when(ServicePsql.instOf()).thenReturn(service);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        BufferedReader br = Mockito.mock(BufferedReader.class);
        StringWriter stringWriter = new StringWriter();
        stringWriter.write("");
        PrintWriter pwMock = new PrintWriter(stringWriter);
        Mockito.when(req.getReader()).thenReturn(br);
        Mockito.when(br.readLine()).thenReturn("{\"sessionId\":1}");
        Mockito.when(resp.getWriter()).thenReturn(pwMock);
        new HallServlet().doPost(req, resp);
        String expected = "[{\"id\":1,\"title\":\"Green\",\"numberOfPlaces\":9,\"numberOfRows\":5,"
                + "\"capacity\":45},{\"id\":1,\"dataOfSale\":{\"date\":{\"year\":2021,\"month\":1,"
                + "\"day\":19},\"time\":{\"hour\":15,\"minute\":23,\"second\":46,\"nano\":0}},"
                + "\"sessionId\":1,\"row\":2,\"place\":4,\"price\":200,\"status\":\"Sold\","
                + "\"nameUser\":\"User One\",\"phoneUser\":\"81234567890\"},"
                + "{\"id\":1,\"dataOfSale\""
                + ":{\"date\":{\"year\":2021,\"month\":1,\"day\":19},\"time\":{\"hour\":12,"
                + "\"minute\":23,"
                + "\"second\":46,\"nano\":0}},\"sessionId\":1,\"row\":2,\"place\":4,\"price\":200,"
                + "\"status\":\"Sold\",\"nameUser\":\"User Two\",\"phoneUser\":\"81234567890\"}]";
        Assert.assertEquals(expected, stringWriter.toString());
    }
}

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
public class PaymentServletTest {
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
        Mockito.when(br.readLine()).thenReturn("{sessionId: 1, row: \"1\", place: \"5\", "
                + "price: \"200\", userName: \"Test\", phoneUser: \"123456789\"}");
        Mockito.when(resp.getWriter()).thenReturn(pwMock);
        new PaymentServlet().doPost(req, resp);
        Assert.assertTrue(Boolean.parseBoolean(stringWriter.toString()));
    }
}

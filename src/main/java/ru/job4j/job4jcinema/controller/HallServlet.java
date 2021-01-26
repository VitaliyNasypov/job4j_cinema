package ru.job4j.job4jcinema.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.job4j.job4jcinema.persistence.Hall;
import ru.job4j.job4jcinema.persistence.Ticket;
import ru.job4j.job4jcinema.service.Service;
import ru.job4j.job4jcinema.service.ServicePsql;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet("/halls")
public class HallServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader br = req.getReader();
        String inputJson = br.readLine();
        JsonElement jsonElement = JsonParser.parseString(inputJson);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String sessionId = jsonObject.get("sessionId").getAsString();
        Service service = ServicePsql.instOf();
        Hall hall = service.getHall(Integer.parseInt(sessionId));
        List<Ticket> ticketList = service.getPurchasedSeats(Integer.parseInt(sessionId), "Sold");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Collection collection = new ArrayList();
        collection.add(hall);
        for (Ticket ticket : ticketList) {
            collection.add(ticket);
        }
        resp.getWriter().print(new Gson().toJson(collection));
        resp.getWriter().flush();
    }
}

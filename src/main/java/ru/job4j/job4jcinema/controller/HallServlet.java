package ru.job4j.job4jcinema.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.job4j.job4jcinema.persistence.Hall;
import ru.job4j.job4jcinema.service.Service;
import ru.job4j.job4jcinema.service.ServicePsql;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/halls")
public class HallServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String inputJson = br.readLine();
        JsonElement jsonParser = JsonParser.parseString(inputJson);
        JsonObject jsonObject = jsonParser.getAsJsonObject();
        String sessionId = jsonObject.get("sessionId").getAsString();
        Service service = ServicePsql.instOf();
        Hall hall = service.getHall(Integer.parseInt(sessionId));
        String[] purchasedSeats = service.getPurchasedSeats(Integer.parseInt(sessionId), "Sold");
        String[] result = new String[4 + purchasedSeats.length];
        result[0] = hall.getTitle();
        result[1] = String.valueOf(hall.getCapacity());
        result[2] = String.valueOf(hall.getNumberOfRows());
        result[3] = String.valueOf(hall.getNumberOfPlaces());
        System.arraycopy(purchasedSeats, 0, result, 4, purchasedSeats.length);
        Gson gson = new Gson();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(gson.toJson(result));
        resp.getWriter().flush();
    }
}

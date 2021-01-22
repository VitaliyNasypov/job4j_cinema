package ru.job4j.job4jcinema.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.job4j.job4jcinema.service.Service;
import ru.job4j.job4jcinema.service.ServicePsql;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String inputJson = br.readLine();
        JsonElement jsonParser = JsonParser.parseString(inputJson);
        JsonObject jsonObject = jsonParser.getAsJsonObject();
        String sessionId = jsonObject.get("sessionId").getAsString();
        String row = jsonObject.get("row").getAsString();
        String place = jsonObject.get("place").getAsString();
        String userName = jsonObject.get("userName").getAsString();
        String phoneUser = jsonObject.get("phoneUser").getAsString();
        Service service = ServicePsql.instOf();
        boolean result = service.buyTicket(Integer.parseInt(row), Integer.parseInt(place),
                Integer.parseInt(sessionId), userName, phoneUser);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        resp.getWriter().print(gson.toJson(result));
        resp.getWriter().flush();
    }
}
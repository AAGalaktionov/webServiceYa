package app.servlets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InfoSerlets extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.getRequestDispatcher("/index.jsp").forward(req,resp);
        System.out.println("Method GET from InfoServlet  " + req.getQueryString());

        RequestProcessing requestProcessing = new RequestProcessing();
        requestProcessing.requestDevider(req.getQueryString().substring(req.getQueryString().indexOf("[=]") + 1), requestProcessing);
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        ArrayList<String> listForParse = new ArrayList<>();
        try {

            listForParse = requestProcessing.returnerRespFromYa(threadPool, requestProcessing);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < listForParse.size(); i++) {
            System.out.println(listForParse.get(i));
        }
        HashMap<String, Integer> mapResult = new HashMap<>();
        for (int i = 0; i < listForParse.size(); i++) {
            mapResult = ParserRequest.parserToMap(listForParse.get(i));
        }
        JsonObject json = new JsonObject();
        for (Map.Entry<String, Integer> entry : mapResult.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            json.addProperty(entry.getKey(), entry.getValue().toString());
        }
        PrintWriter writer = resp.getWriter();
        writer.println(json.toString().split("")[0]);
        for (int i = 0; i < json.toString().split(",").length; i++) {
            if (i==0)
            writer.println(json.toString().split(",")[i].substring(1));
            else if (i==json.toString().split(",").length-1)
                writer.println(json.toString().split(",")[i].substring(0, json.toString().split(",")[i].length()-1));
            else
                writer.println(json.toString().split(",")[i]);
        }
        writer.println(json.toString().split("")[json.toString().split("").length-1]);


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        PrintWriter writer = resp.getWriter();
        writer.println("Method POST from InfoServlet");
    }
}

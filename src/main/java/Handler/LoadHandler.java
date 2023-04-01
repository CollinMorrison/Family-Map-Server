package Handler;
import java.io.*;
import java.net.*;

import Model.Event;
import Model.Person;
import Model.User;
import Result.LoadResult;
import Service.LoadService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.*;
import Request.*;

public class LoadHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        LoadService loadService = new LoadService();
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = new String(reqBody.readAllBytes());
                System.out.println(reqData);
                // Get the data as JSON arrays
                JsonObject reqObject = gson.fromJson(reqData, JsonObject.class);
                JsonArray users = reqObject.get("users").getAsJsonArray();
                JsonArray persons = reqObject.get("persons").getAsJsonArray();
                JsonArray events = reqObject.get("events").getAsJsonArray();
                // Convert the data to arrays of objects
                User[] userArray = gson.fromJson(users, User[].class);
                Person[] personArray = gson.fromJson(persons, Person[].class);
                Event[] eventArray = gson.fromJson(events, Event[].class);
                // Construct a load request
                LoadRequest loadRequest = new LoadRequest(userArray, personArray, eventArray);
                // Get response
                LoadResult response = loadService.load(loadRequest);
                // if the response was valid
                if (response != null) {
                    String respData = gson.toJson(response);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                    success = true;
                } else {
                    System.out.println("Invalid request");
                }
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}

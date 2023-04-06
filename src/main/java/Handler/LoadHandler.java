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
        LoadResult response;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = new String(reqBody.readAllBytes());
                System.out.println(reqData);
                // Get the data as JSON arrays
                JsonObject reqObject = gson.fromJson(reqData, JsonObject.class);
                // If there is anything missing from the request
                if (
                        reqObject.has("users") &&
                                reqObject.has("persons") &&
                                reqObject.has("events")
                ) {
                    JsonArray users = reqObject.get("users").getAsJsonArray();
                    User[] userArray = gson.fromJson(users, User[].class);
                    JsonArray persons = reqObject.get("persons").getAsJsonArray();
                    Person[] personArray = gson.fromJson(persons, Person[].class);
                    JsonArray events = reqObject.get("events").getAsJsonArray();
                    Event[] eventArray = gson.fromJson(events, Event[].class);
                    // Construct a load request
                    LoadRequest loadRequest = new LoadRequest(userArray, personArray, eventArray);
                    // Get response
                    response = loadService.load(loadRequest);
                } else {
                    response = new LoadResult("Error: Request body must include users, persons, and events", false);
                }
                String respData = gson.toJson(response);
                // if the response was valid
                if (response.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            response = new LoadResult("Error: Internal Server Error", false);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String respData = gson.toJson(response);
            OutputStream respBody = exchange.getResponseBody();
            writeString(respData, respBody);
            respBody.close();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}

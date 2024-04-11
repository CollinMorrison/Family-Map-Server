package Handler;
import java.io.*;
import java.net.*;

import Result.EventResult;
import Service.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class EventHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        EventService eventService = new EventService();
        boolean success = false;
        EventResult response;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    InputStream reqBody = exchange.getRequestBody();
                    String reqData = new String(reqBody.readAllBytes());
                    System.out.println(reqData);
                    response = eventService.event(authToken);
                    String respData = gson.toJson(response);
                    // if the response was valid
                    if (response.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        success = true;
                    } else if (response.getMessage().equals("Error: Invalid AuthToken")){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                    }
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                } else {
                    response = new EventResult(
                            null,
                            false,
                            "Error: Request must include an Authorization token"
                    );
                    String respData = gson.toJson(response);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                }
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            e.printStackTrace();
            response = new EventResult(
                    null,
                    false,
                    "Error: Internal Server Error"
            );
            String respData = gson.toJson(response);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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

package Handler;
import java.io.*;
import java.net.*;

import Result.EventIDResult;
import Service.EventIDService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class EventIDHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        EventIDService eventIDService = new EventIDService();
        EventIDResult response;
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                // if the request has a valid auth token
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    InputStream reqBody = exchange.getRequestBody();
                    String reqData = new String(reqBody.readAllBytes());
                    System.out.println(reqData);
                    // Get personID from the path
                    URI uri = exchange.getRequestURI();
                    String path = uri.getPath();
                    String[] variables = path.split("/");
                    String eventID = variables[2];
                    response = eventIDService.eventID(authToken, eventID);
                    String respData = gson.toJson(response);
                    // if the response was valid
                    if (response.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        success = true;
                    } else if (response.getMessage() == "Error: Invalid AuthToken"
                    || response.getMessage() == "Error: Invalid eventID"
                    || response.getMessage() == "Error: Requested event does not belong to this user"){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                    }
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                } else {
                    response = new EventIDResult(
                            null,
                            null,
                            null,
                            Float.NaN,
                            Float.NaN,
                            null,
                            null,
                            null,
                            Integer.MIN_VALUE,
                            false,
                            "Error: An authorization token is required"
                    );
                    String respData = gson.toJson(response);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            response = new EventIDResult(
                    null,
                    null,
                    null,
                    Float.NaN,
                    Float.NaN,
                    null,
                    null,
                    null,
                    Integer.MIN_VALUE,
                    false,
                    "Error: Internal Server Error"
            );
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

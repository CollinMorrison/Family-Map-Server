package Handler;
import java.io.*;
import java.net.*;

import DataAccess.UserDao;
import Result.PersonIDResult;
import Service.PersonIDService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class PersonIDHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        PersonIDService personIDService = new PersonIDService();
        PersonIDResult response;
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
                    String personID = variables[2];
                    response = personIDService.personID(authToken, personID);
                    // if the response was valid
                    String respData = gson.toJson(response);
                    if (response.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        success = true;
                    } else if (
                            response.getMessage().equals("Error: Invalid AuthToken") ||
                            response.getMessage().equals("Error: Invalid personID") ||
                            response.getMessage().equals("Error: The person is not associated with this user")
                    ){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                    }
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                } else {
                    response = new PersonIDResult(
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            false,
                            "Error: AuthToken is required"
                    );
                    String respData = gson.toJson(response);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            response = new PersonIDResult(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    false,
                    "Error: Internal Server Error"
            );
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR,0);
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

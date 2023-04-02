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
                    PersonIDResult response = personIDService.personID(authToken, personID);
                    // if the response was valid
                    if (response != null) {
                        String respData = gson.toJson(response);
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(respData, respBody);
                        respBody.close();
                        success = true;
                    } else {
                        System.out.println("The person could not be retrieved");
                    }
                }
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR,0);
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

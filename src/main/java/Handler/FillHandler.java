package Handler;
import java.io.*;
import java.net.*;

import DataAccess.DataAccessException;
import Model.User;
import Result.FillResult;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class FillHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        FillService fillService = new FillService();
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                URI uri = exchange.getRequestURI();
                String path = uri.getPath();
                // Get variables from the path
                String[] variables = path.split("/");
                String username = variables[2];
                int generations = 0;
                // If the path includes a generations value
                if (variables.length == 4) {
                    generations = Integer.parseInt(variables[3]);
                }
                FillResult response = fillService.fill(generations, username);
                // If the response was valid
                if (response != null) {
                    String respData = gson.toJson(response);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                    success = true;
                }
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
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

package Handler;
import java.io.*;
import java.net.*;

import Result.FillResult;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class FillHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        FillService fillService = new FillService();
        FillResult response;
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
                response = fillService.fill(generations, username);
                String respData = gson.toJson(response);
                // If the response was valid
                if (response.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else if (response.getMessage() == "Error: Internal Server Error"){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            response = new FillResult(
                    "Error: Internal Server Error",
                    false
            );
            OutputStream respBody = exchange.getResponseBody();
            String respData = gson.toJson(response);
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

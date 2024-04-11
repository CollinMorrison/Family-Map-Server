package Handler;
import java.io.*;
import java.net.*;

import Result.ClearResult;
import Service.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class ClearHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        ClearService clearService = new ClearService();
        ClearResult response;
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                response = clearService.clear();
                String respData = gson.toJson(response);
                if (response.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    success = true;
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            response = new ClearResult("Error: Internal Server Error", false);
            String respData = gson.toJson(response);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
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

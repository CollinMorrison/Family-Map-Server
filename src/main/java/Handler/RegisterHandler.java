package Handler;
import java.io.*;
import java.net.*;

import DataAccess.DataAccessException;
import Service.RegisterService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.*;
import Request.RegisterRequest;
import Result.RegisterResult;

import static java.nio.file.Files.writeString;

public class RegisterHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        RegisterService registerService = new RegisterService();
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = new String(reqBody.readAllBytes());
                System.out.println(reqData);
                // construct a register request
                JsonObject reqObject = gson.fromJson(reqData, JsonObject.class);
                String username = reqObject.get("username").getAsString();
                String password = reqObject.get("password").getAsString();
                String email = reqObject.get("email").getAsString();
                String firstName = reqObject.get("firstName").getAsString();
                String lastName = reqObject.get("lastName").getAsString();
                String gender = reqObject.get("gender").getAsString();
                RegisterRequest request = new RegisterRequest(username, password, email, firstName, lastName, gender);
                // Send request and get response
                RegisterResult response = registerService.register(request);
                // if the response was valid
                if (response != null) {
                    String respData = gson.toJson(response);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                    success = true;
                } else {
                    System.out.println("The user could not be registered");
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

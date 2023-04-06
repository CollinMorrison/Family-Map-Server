package Handler;
import java.io.*;
import java.net.*;

import Service.RegisterService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.*;
import Request.RegisterRequest;
import Result.RegisterResult;


public class RegisterHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        RegisterService registerService = new RegisterService();
        RegisterResult response;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = new String(reqBody.readAllBytes());
                System.out.println(reqData);
                // construct a register request
                JsonObject reqObject = gson.fromJson(reqData, JsonObject.class);
                // If there is a property missing from the request
                if (
                        !reqObject.has("username") ||
                        !reqObject.has("password") ||
                        !reqObject.has("email") ||
                        !reqObject.has("firstName") ||
                        !reqObject.has("lastName") ||
                        !reqObject.has("gender")
                ) {
                    response = new RegisterResult(
                            null,
                            null,
                            null,
                            false,
                            "Error: There is a missing property in the request"
                    );
                } else {
                    String username = reqObject.get("username").getAsString();
                    String password = reqObject.get("password").getAsString();
                    String email = reqObject.get("email").getAsString();
                    String firstName = reqObject.get("firstName").getAsString();
                    String lastName = reqObject.get("lastName").getAsString();
                    String gender = reqObject.get("gender").getAsString();
                    RegisterRequest request = new RegisterRequest(username, password, email, firstName, lastName, gender);
                    // Send request and get response
                    response = registerService.register(request);
                }
                String respData = gson.toJson(response);
                if (!response.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            response = new RegisterResult(
                    null,
                    null,
                    null,
                    false,
                    "Error: Internal Server Error"
            );
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

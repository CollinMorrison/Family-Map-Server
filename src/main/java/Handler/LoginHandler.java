package Handler;
import java.io.*;
import java.net.*;
import Result.LoginResult;
import Service.LoginService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.*;
import Request.LoginRequest;

public class LoginHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //System.out.println("In login handler");
        Gson gson = new Gson();
        LoginService loginService = new LoginService();
        LoginResult response;
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                //Get the request body
                InputStream reqBody = exchange.getRequestBody();
                String reqData = new String(reqBody.readAllBytes());
                System.out.println(reqData);
                //construct a login request
                // If there is a property missing from the request
                JsonObject reqObject = gson.fromJson(reqData, JsonObject.class);
                if (!reqObject.has("username") || !reqObject.has("password")) {
                    response = new LoginResult(
                            null,
                            null,
                            null,
                            false,
                            "Error: A username or password is missing"
                    );
                } else {
                    String username = reqObject.get("username").getAsString();
                    String password = reqObject.get("password").getAsString();
                    LoginRequest request = new LoginRequest(username, password);
                    //Send request and get response
                    response = loginService.login(request);
                    success = true;
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
            response = new LoginResult(
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

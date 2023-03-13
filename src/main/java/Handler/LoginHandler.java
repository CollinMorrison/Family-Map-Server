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
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                //System.out.println("in if block");
                //Headers reqHeaders = exchange.getRequestHeaders();
                //System.out.println("Checking for authorization");
                //if (reqHeaders.containsKey("Authorization")) {
                    //System.out.println("Authorization successful");
                    //String authToken = reqHeaders.getFirst("Authorization");
                    //System.out.println(authToken);
                    //Get the request body
                    InputStream reqBody = exchange.getRequestBody();
                    String reqData = new String(reqBody.readAllBytes());
                    System.out.println(reqData);
                    //construct a login request
                    JsonObject reqObject = gson.fromJson(reqData, JsonObject.class);
                    String username = reqObject.get("username").getAsString();
                    String password = reqObject.get("password").getAsString();
                    System.out.println(username + " " + password);
                    LoginRequest request = new LoginRequest(username, password);
                    //Check if the authToken is valid, if it is, get what user it belongs to
                    //if the auth token is valid
                    if (loginService.authTokenIsValid(username)) {
                        //System.out.println(authToken);
                        //Send request and get response
                        LoginResult response = loginService.login(request);
                        String respData = gson.toJson(response);
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(respData, respBody);
                        respBody.close();
                        success = true;
                    }
                }
            //}
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

package Handler;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import com.sun.net.httpserver.*;

public class FileHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //if the request is a GET request
        if (exchange.getRequestMethod().equals("GET")) {
            //get the request URL from the exchange
            String urlPath = exchange.getRequestURI().toString();
            //if urlPath is null or "/", set urlPath to "/index.html"
            if (urlPath.equals(null) || urlPath.equals("/")) {
                urlPath = "/index.html";
            }
            //direct the paths to the actual physical files
            String filePath = "web" + urlPath;
            //create a file object and check if the file exists (file.exists())
            //return a 404 (not found) error if the file noes not exist
            File file = new File(filePath);
            if (!file.exists()) {
                exchange.sendResponseHeaders(404, 0);
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(Path.of("web/HTML/404.html"), respBody);
                respBody.close();
            } else {
                Headers responseHeaders = exchange.getResponseHeaders();
                responseHeaders.set("Content-Type", Files.probeContentType(file.toPath()));
                exchange.sendResponseHeaders(200, file.length());
                //If the file does exist, read the file and write it to the HttpExchange's output stream
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(file.toPath(), respBody);
                respBody.close();
            }
        }
    }
}

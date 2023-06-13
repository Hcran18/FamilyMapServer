package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

/**
 * The FileHandler class implements HttpHandler to handle HTTP requests related to file retrieval.
 */
public class FileHandler implements HttpHandler {
    /**
     * Handles the HTTP request for the main file retrieval and generates the appropriate response.
     *
     * @param exchange The HttpExchange object representing the HTTP request and response.
     * @throws IOException If an IO error occurs while handling the request or response.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                String urlPath = exchange.getRequestURI().toString();

                if (urlPath == null || urlPath.equals("/")) {
                    urlPath = "/index.html";
                }

                String filePath = "web" + urlPath;

                File file = new File(filePath);
                if (file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);
                    respBody.close();
                }
                else {
                    // Return 404 error (File Not Found)
                    filePath = "web/HTML/404.html";
                    File errorFile = new File(filePath);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(errorFile.toPath(), respBody);
                    respBody.close();
                }
            }
            else {
                // Return 405 error (Method Not Allowed)
                exchange.sendResponseHeaders(405, 0);
                OutputStream respBody = exchange.getResponseBody();
                respBody.close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}

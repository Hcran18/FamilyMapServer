package Handler;

import Result.FillResult;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import passoffrequest.FillRequest;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * The FillHandler class implements HttpHandler to handle HTTP requests related to filling data.
 */
public class FillHandler implements HttpHandler {
    /**
     * Handles the HTTP request and generates the appropriate response.
     *
     * @param exchange The HttpExchange object representing the HTTP request and response.
     * @throws IOException If an IO error occurs while handling the request or response.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String username = null;
        int generations = 4;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                String path = exchange.getRequestURI().getPath();
                String[] pathParts = path.split("/");

                if (pathParts.length >= 3) {
                    username = pathParts[2];
                    if (pathParts.length >= 4) {
                        generations = Integer.parseInt(pathParts[3]);
                    }
                }

                if (username != null) {
                    FillService service = new FillService();
                    FillResult result = service.fill(username, generations);

                    Gson gson = new Gson();

                    if (result.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }

                    OutputStream resBody = exchange.getResponseBody();
                    Writer writer = new OutputStreamWriter(resBody);
                    gson.toJson(result, writer);
                    writer.close();
                    resBody.close();

                }
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}

package Handler;

import Result.ClearResult;
import Service.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                ClearService service = new ClearService();
                ClearResult result = service.clear();

                Gson gson = new Gson();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream resBody = exchange.getResponseBody();
                Writer writer = new OutputStreamWriter(resBody);
                gson.toJson(result, writer);
                writer.close();
                resBody.close();

                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}

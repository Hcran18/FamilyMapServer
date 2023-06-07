package Handler;

import Result.FillResult;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import passoffrequest.FillRequest;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
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
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream resBody = exchange.getResponseBody();
                    Writer writer = new OutputStreamWriter(resBody);
                    gson.toJson(result, writer);
                    writer.close();
                    resBody.close();

                    success = true;
                }
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

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);

        char[] buf = new char[1024];
        int length;
        while ((length = sr.read(buf)) > 0) {
            sb.append(buf, 0, length);
        }
        return sb.toString();
    }
}

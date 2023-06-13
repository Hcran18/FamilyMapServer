package Handler;

import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.*;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * The LoginHandler class implements HttpHandler to handle HTTP requests related to user login.
 */
public class LoginHandler implements HttpHandler {
    /**
     * Handles the HTTP request and generates the appropriate response.
     *
     * @param exchange The HttpExchange object representing the HTTP request and response.
     * @throws IOException If an IO error occurs while handling the request or response.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();

                String reqData = readString(reqBody);

                Gson gson = new Gson();
                LoginRequest request = gson.fromJson(reqData, LoginRequest.class);

                LoginService service = new LoginService();
                LoginResult result = service.login(request);

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
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    /**
     * Reads the input stream and converts it to a String.
     *
     * @param is The InputStream object.
     * @return A String representing the contents of the input stream.
     * @throws IOException If an IO error occurs while reading the input stream.
     */
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

package Handler;

import DataAccess.AuthtokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Result.EventResult;
import Result.Event_EventIDResult;
import Service.EventService;
import Service.Event_EventIDService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Authtoken;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.sql.Connection;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String eventID = null;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {
                    String authtoken = reqHeaders.getFirst("Authorization");

                    Database db = new Database();

                    try {
                        db.openConnection();
                        Connection conn = db.getConnection();

                        AuthtokenDao aDao = new AuthtokenDao(conn);
                        Authtoken user = aDao.findByAuthtoken(authtoken);

                        if (user != null) {
                            String username = user.getUsername();
                            db.closeConnection(true);

                            String path = exchange.getRequestURI().getPath();
                            String[] pathParts = path.split("/");

                            if (pathParts.length >= 3) {
                                eventID = pathParts[2];

                                Event_EventIDService service = new Event_EventIDService();
                                Event_EventIDResult result = service.event_eventID(username, eventID);

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
                            else {
                                EventService service = new EventService();
                                EventResult result = service.events(username);

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
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                            EventResult result = new EventResult();
                            result.setMessage("Error: Incorrect Authtoken");
                            result.setSuccess(false);

                            Gson gson = new Gson();
                            OutputStream resBody = exchange.getResponseBody();
                            Writer writer = new OutputStreamWriter(resBody);
                            gson.toJson(result, writer);
                            writer.close();
                            resBody.close();
                        }
                    }
                    catch (DataAccessException e) {
                        db.closeConnection(false);
                        e.printStackTrace();
                    }
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
            else {
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

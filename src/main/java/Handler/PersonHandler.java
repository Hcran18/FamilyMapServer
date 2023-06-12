package Handler;

import DataAccess.AuthtokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Result.PersonResult;
import Result.Person_PersonIDResult;
import Service.PersonService;
import Service.Person_PersonIDService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Authtoken;

import java.io.*;
import java.net.HttpURLConnection;
import java.sql.Connection;

/**
 * The PersonHandler class implements the HttpHandler interface to handle HTTP requests related to person data.
 */
public class PersonHandler implements HttpHandler {
    /**
     * Handles the HTTP request and generates the appropriate response.
     *
     * @param exchange The HttpExchange object representing the HTTP request and response.
     * @throws IOException If an I/O error occurs while handling the request or response.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String personID = null;

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
                                personID = pathParts[2];

                                Person_PersonIDService service = new Person_PersonIDService();
                                Person_PersonIDResult result = service.person_personID(username, personID);

                                Gson gson = new Gson();

                                if (result.isSuccess()){
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
                                PersonService service = new PersonService();
                                PersonResult result = service.persons(username);

                                Gson gson = new Gson();

                                if (result.isSuccess()){
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

                            PersonResult result = new PersonResult();
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

import java.io.*;
import java.net.*;

import FakeFamilyData.*;
import Handler.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

/**
 * The main class that initializes and runs the HTTP server.
 */
public class Server {

	/**
	 * The maximum number of waiting incoming connections to queue.
	 */
	private static final int MAX_WAITING_CONNECTIONS = 12;

	/**
	 * The HttpServer object used for handling incoming HTTP requests.
	 */
	private HttpServer server;

	/**
	 * Runs the HTTP server on the specified port number.
	 *
	 * @param portNumber The port number on which the server will run.
	 */
	private void run(String portNumber) {

		System.out.println("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(
						new InetSocketAddress(Integer.parseInt(portNumber)),
						MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}

		server.setExecutor(null);

		System.out.println("Creating contexts");

		server.createContext("/user/register", new RegisterHandler());

		server.createContext("/user/login", new LoginHandler());

		server.createContext("/fill", new FillHandler());

		server.createContext("/clear", new ClearHandler());

		server.createContext("/person", new PersonHandler());

		server.createContext("/event", new EventHandler());

		server.createContext("/load", new LoadHandler());

		server.createContext("/", new FileHandler());

		System.out.println("Starting server");

		server.start();

		System.out.println("Server started");
	}

	/**
	 * Stores the necessary data from JSON files into the cache.
	 */
	private static void storeData() {
		try {
			System.out.println("Storing Data");

			Gson gson = new Gson();

			Reader locationReader = new FileReader("json/locations.json");
			LocationData locData = gson.fromJson(locationReader, LocationData.class);
			Cache.setLocations(locData);

			Reader mNamesReader = new FileReader("json/mnames.json");
			MNamesData maleData = gson.fromJson(mNamesReader, MNamesData.class);
			Cache.setMaleNames(maleData);

			Reader fNamesReader = new FileReader("json/fnames.json");
			FNamesData femaleData = gson.fromJson(fNamesReader, FNamesData.class);
			Cache.setFemaleNames(femaleData);

			Reader sNamesReader = new FileReader("json/snames.json");
			SNamesData surnamesData = gson.fromJson(sNamesReader, SNamesData.class);
			Cache.setSurnames(surnamesData);

			System.out.println("Data Stored");
		}
		catch (FileNotFoundException e) {
			System.out.println("Unable to Store Data");
			e.printStackTrace();
		}
	}

	/**
	 * The entry point of the server application.
	 *
	 * @param args The command line arguments. The first argument should be the port number.
	 */
	public static void main(String[] args) {		
		String portNumber = args[0];
		storeData();
		new Server().run(portNumber);
	}
}


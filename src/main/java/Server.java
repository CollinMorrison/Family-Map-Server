import java.io.*;
import java.net.*;
import java.util.Arrays;

import Handler.*;
import com.google.gson.FieldAttributes;
import com.sun.net.httpserver.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS
            );
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);

        System.out.println("Creating contexts");

        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill/", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person/", new PersonIDHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event/", new EventIDHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/", new FileHandler());

       loadLocations();
       loadFNames();
       loadMNames();
       loadSNames();

        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }

    static class Location {
        String latitude;
        String longitude;
        String city;
        String country;
    }

    static class LocationData {
        Location[] data;
    }
    public static void loadLocations() {
        Gson gson = new Gson();
        System.out.println("Loading Locations");
        try {
            Reader reader = new FileReader("json/locations.json");
            LocationData locData = (LocationData)gson.fromJson(reader, LocationData.class);
        } catch (FileNotFoundException e) {
            System.out.println("Error encountered loading locations");
        }
    }

    static class FNamesData {
        String[] data;
    }

    public static void loadFNames() {
        Gson gson = new Gson();
        System.out.println("Loading First Names");
        try {
            Reader reader = new FileReader("json/fnames.json");
            FNamesData firstNameData = (FNamesData)gson.fromJson(reader, FNamesData.class);
        } catch (FileNotFoundException e) {
            System.out.println("Error encountered while loading First Names");
        }
    }

    static class MNamesData {
        String[] data;
    }

    public static void loadMNames() {
        Gson gson = new Gson();
        System.out.println("Loading Middle Names");
        try {
            Reader reader = new FileReader("json/mnames.json");
            MNamesData middleNameData = (MNamesData)gson.fromJson(reader, MNamesData.class);
        } catch (FileNotFoundException e) {
            System.out.println("Error encountered while loading Middle Names");
        }
    }

    static class SNamesData {
        String[] data;
    }

    public static void loadSNames() {
        Gson gson = new Gson();
        System.out.println("Loading Surnames");
        try {
            Reader reader = new FileReader("json/snames.json");
            SNamesData surnameData = (SNamesData)gson.fromJson(reader, SNamesData.class);
        } catch (FileNotFoundException e) {
            System.out.println("Error encountered while loading Surnames");
        }
    }
}

package Serializer;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class Serializer {

    public Location[] loadLocations() {
        Gson gson = new Gson();
        System.out.println("Loading Locations");
        try {
            Reader reader = new FileReader("json/locations.json");
            LocationData locationData = gson.fromJson(reader, LocationData.class);
            System.out.println(locationData.data);
            return locationData.data;
        } catch (FileNotFoundException e) {
            System.out.println("Error encountered loading locations");
        }
        return null;
    }

    public String[] loadFNames() {
        Gson gson = new Gson();
        System.out.println("Loading First Names");
        try {
            Reader reader = new FileReader("json/fnames.json");
            FNamesData firstNameData = gson.fromJson(reader, FNamesData.class);
            return firstNameData.data;
        } catch (FileNotFoundException e) {
            System.out.println("Error encountered while loading First Names");
        }
        return null;
    }

    public String[] loadMNames() {
        Gson gson = new Gson();
        System.out.println("Loading Middle Names");
        try {
            Reader reader = new FileReader("json/mnames.json");
            MNamesData middleNameData = gson.fromJson(reader, MNamesData.class);
            return middleNameData.data;
        } catch (FileNotFoundException e) {
            System.out.println("Error encountered while loading Middle Names");
        }
        return null;
    }
    public String[] loadSNames() {
        Gson gson = new Gson();
        System.out.println("Loading Surnames");
        try {
            Reader reader = new FileReader("json/snames.json");
            SNamesData surnameData = gson.fromJson(reader, SNamesData.class);
            return surnameData.data;
        } catch (FileNotFoundException e) {
            System.out.println("Error encountered while loading Surnames");
        }
        return null;
    }
}

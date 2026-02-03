package model.entity.JSON;

import model.entity.Booking;
import model.entity.User;
import model.entity.Vehicle;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonFiles {
    private static final Jsonb jsonb = JsonbBuilder.create();

    public static List<User> readUsers(String file) {
        try {
            InputStream is = JsonFiles.class.getResourceAsStream(file);

            if (is == null) {
                System.out.println("NE MOGU NAĆI FILE: " + file);
                return new ArrayList<>();
            }

            String json = new String(is.readAllBytes());
            return jsonb.fromJson(
                    json,
                    new ArrayList<User>() {}.getClass().getGenericSuperclass()
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public static List<Booking> readBooking(String file) {
        try{
            if(!Files.exists(Paths.get("src/main/resources/" + file))) return new ArrayList<>();

            String json=Files.readString(Paths.get("src/main/resources/" + file));
           // List<Booking> list=jsonb.fromJson(json,ArrayList.class);
            return jsonb.fromJson(json, new ArrayList<Booking>(){}.getClass().getGenericSuperclass());
        }catch (Exception e){
            System.out.println("greska prilikom ispisivanja liste iz datoteku bookinga\n");
            return null;
        }
    }

    public static List<Vehicle> readVehicle(String file) {
        try{
            if(!Files.exists(Paths.get("src/main/resources/" + file))) return null;

            String json=Files.readString(Paths.get("src/main/resources/" + file));
            return jsonb.fromJson(json, new ArrayList<Vehicle>(){}.getClass().getGenericSuperclass());
        }catch (Exception e){
            System.out.println("greska prilikom ispisivanja liste iz datoteku bookinga\n");
            return null;
        }
    }

    public static <T> void writeList(String file, List<T> objects) {
        Path path = Paths.get("src/main/resources/" + file);

        try {
            String json = jsonb.toJson(objects);
            Files.writeString(path, json);
        } catch (Exception e) {
            System.out.println("greska prilikom zapisivanja liste\n");
            e.printStackTrace();

        }
    }

    public static void writeBooking(Booking booking, String file) {

        Path path= Paths.get("src/main/resources/" + file);

        try{

            Files.writeString(path,jsonb.toJson(booking));

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("ne valja upis bookinga");
        }
    }

}

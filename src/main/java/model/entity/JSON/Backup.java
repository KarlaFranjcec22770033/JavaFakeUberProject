package model.entity.JSON;

import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Backup {

    public static final Path p1= Paths.get("src/main/resources/files/backup1.bin");
    public static final Path p2= Paths.get("src/main/resources/files/backup2.bin");


    public static void save(BackupData backup){
        try(ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(p2))) {
            out.writeObject(backup);
            System.out.println("Uspjesno vracene postavke");
        } catch (Exception e) {
            System.out.println("Greska prilikom spremanja back-upa");
            e.printStackTrace();
        }
    }

    public static void reload(BackupData backup){
        try(ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(p1))) {
            out.writeObject(backup);
            System.out.println("Uspjesno spremljene promjene");
        } catch (Exception e) {
            System.out.println("Greska prilikom spremanja back-upa");
        }
    }

}

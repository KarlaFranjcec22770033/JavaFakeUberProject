package utils;

import javafx.scene.control.Alert;

public class DialogAlert {

    public static void error(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("GRESKA");
        alert.setHeaderText(message);
        alert.setContentText("Nemoguce nastaviti");
        alert.showAndWait();
    }

    public static void infoZapis(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Novi zapis");
        alert.setHeaderText("Informacije o spremanju");
        alert.setContentText("Uspjesno spremljen novi korisnik");

        alert.showAndWait();
    }

    public static void infoZapisBooking(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Novi zapis");
        alert.setHeaderText("Informacije o spremanju");
        alert.setContentText("Uspjesno spremljena nova rezervacija");

        alert.showAndWait();
    }

    public static void infoZapisVehicle(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Novi zapis");
        alert.setHeaderText("Informacije o spremanju");
        alert.setContentText("Uspjesno spremljeno novo vozilo");

        alert.showAndWait();
    }

    public static void infoZapisDelete(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Brisanje zapisa");
        alert.setHeaderText("Informacije o brisanju");
        alert.setContentText("Uspjesno obrisana odabrana stavka");

        alert.showAndWait();
    }

    public static void infoZapisBackup(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Backup tablice");
        alert.setHeaderText("Informacije o backup-u");
        alert.setContentText("Uspjesno kreirana odabrana stavka za backup");

        alert.showAndWait();
    }
}

package controllers;

import database.ReadObject;
import database.SaveObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.entity.Booking;
import model.entity.JSON.JsonFiles;
import model.entity.User;
import utils.DialogAlert;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NovaVoznjaController {

    @FXML
    private DatePicker datum;

    @FXML
    private TextField pocLokacija;

    @FXML
    private Button restart;

    @FXML
    private Button save;

    @FXML
    private TextField zavLokacija;

    public void save(){

        if(datum.getValue().isBefore(LocalDate.now())){
            DialogAlert.error("Datum mora biti u buducnosti");
            restart();
        }else {

            try {
                List<User> listUser = ReadObject.getPassangers();
                int id = listUser.stream().map(User::getId).max(Integer::compareTo).get();

                SaveObject.executeQueryBookings(datum.getValue().toString(), pocLokacija.getText(), zavLokacija.getText(), id);

                DialogAlert.infoZapisBooking();

            } catch (Exception e) {
                DialogAlert.error("Ne valja datoteka");
            }
        }

    }

    public void restart(){
        pocLokacija.clear();
        zavLokacija.clear();
        datum.setValue(null);
    }

}

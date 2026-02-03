package controllers;

import database.ReadObject;
import database.SaveObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.entity.User;
import model.entity.Vehicle;
import utils.DialogAlert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NovoVoziloController {

    @FXML
    private ComboBox<String> model;

    @FXML
    private ColorPicker boja;

    @FXML
    private TextField registracija;

    public void initialize(){

        List<String> models=new ArrayList<>();
        models.add("BMW");
        models.add("Renault");
        models.add("Opel");
        models.add("Mercedes");
        models.add("Dacia");
        models.add("Tesla");
        models.add("VW");
        models.add("Hyundai");
        models.add("Kia");
        ObservableList<String> modelList= FXCollections.observableArrayList(models);

        model.setItems(modelList);
    }

    @FXML
    void reset() {
        registracija.clear();
        boja.setValue(null);
        model.setValue(null);
    }

    @FXML
    void saveVozilo() {

        try{

            List<Vehicle> vozila=ReadObject.getVehicles();
            List<String> registracije=vozila.stream().map(Vehicle::getRegistration).toList();

            boolean jedinstven=true;

            for(String r:registracije) {
                if (registracija.getText().equals(r)) {
                    jedinstven=false;
                    DialogAlert.error("Registracija mora biti jedinstvena");
                    reset();
                }
            }

            if(jedinstven) {

                List<User> list = ReadObject.getDrivers();
                int id = list.stream().map(User::getId).toList().getLast();

                String modelValue = model.getValue();
                String registrationValue = registracija.getText();
                String colorValue = boja.getValue().toString();

                SaveObject.executeQueryVehicles(modelValue, colorValue, registrationValue, id);

                DialogAlert.infoZapisVehicle();
            }
        } catch (SQLException e) {
            DialogAlert.error(e.getMessage());
        }

    }

}

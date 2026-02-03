package controllers;

import database.ReadObject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.entity.JSON.JsonFiles;
import model.entity.Vehicle;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VozilaController {
    @FXML
    private TableView<Vehicle> vehicleTable;
    @FXML
    private TableColumn<Vehicle, Integer> vozacId;
    @FXML
    private TableColumn<Vehicle, String> marka;
    @FXML
    private TableColumn<Vehicle, String> registracija;
    @FXML
    private TableColumn<Vehicle, String> boja;
    @FXML
    private TextField idPrompt;
    @FXML
    private ColorPicker bojaPrompt;
    @FXML
    private ComboBox<String> markaPrompt;

    public void initialize() {

        vehicleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<String> lista= ReadObject.getModels();
        markaPrompt.getItems().addAll(lista);

        vozacId.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Vehicle, Integer>, ObservableValue<Integer>>() {
                    public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Vehicle, Integer> param) {
                        return new ReadOnlyObjectWrapper<>(
                                param.getValue().getDriverId()
                        );
                    }
                }
        );

        boja.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Vehicle, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Vehicle, String> param) {
                        return new ReadOnlyStringWrapper(
                                param.getValue().getColor()
                        );
                    }
                }
        );

        marka.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Vehicle,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Vehicle, String> param) {
                        return new ReadOnlyStringWrapper(
                                param.getValue().getModel()
                        );
                    }
                }
        );

        registracija.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Vehicle,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Vehicle, String> param) {
                        return new ReadOnlyStringWrapper(
                                param.getValue().getRegistration()
                        );
                    }
                }
        );
    }

    public void ucitajPoId(){
        List<Vehicle> lista= ReadObject.getVehiclesId(Integer.parseInt(idPrompt.getText()));
        vehicleTable.setItems(FXCollections.observableArrayList(lista));
    }

    public void ucitajPoBoji(){
        List<Vehicle> lista= ReadObject.getVehiclesColor(bojaPrompt.getValue().toString());

        vehicleTable.setItems(FXCollections.observableArrayList(lista));
    }

    public void ucitajPoMarki(){
        List<Vehicle> lista= ReadObject.getVehiclesModel(markaPrompt.getValue().toString());
        vehicleTable.setItems(FXCollections.observableArrayList(lista));
    }
}

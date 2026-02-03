package controllers;

import database.ReadObject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.entity.Booking;
import model.entity.JSON.JsonFiles;
import utils.DialogAlert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RezervacijeContorller {
    @FXML
    private TableView<Booking> bookingTable;
    @FXML
    private TextField idPrompt;
    @FXML
    private ComboBox<String> lokacijaPrompt;
    @FXML
    private DatePicker datumPromt;
    @FXML
    private TableColumn<Booking, Integer> idColumn;
    @FXML
    private TableColumn<Booking, String> pocLokacijeColumn;
    @FXML
    private TableColumn<Booking, String> zavLokacijeColumn;
    @FXML
    private TableColumn<Booking, String> dateColumn;

    public void initialize() {

        bookingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<String> lista=ReadObject.getLocations();

        lokacijaPrompt.getItems().addAll(lista);

        idColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Booking, Integer>,
                        ObservableValue<Integer>>() {
                    public ObservableValue<Integer> call(
                            TableColumn.CellDataFeatures<Booking, Integer> param) {
                        return new ReadOnlyObjectWrapper<>(
                                param.getValue().getUserId()
                        );
                    }
                }
        );

        pocLokacijeColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Booking, String>,
                        ObservableValue<String>>() {
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Booking, String> param) {
                        return new ReadOnlyStringWrapper(
                                param.getValue().getStart()
                        );
                    }
                }
        );

        zavLokacijeColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Booking, String>,
                        ObservableValue<String>>() {
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Booking, String> param) {
                        return new ReadOnlyStringWrapper(
                                param.getValue().getEnd()
                        );
                    }
                }
        );

        dateColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Booking, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Booking, String> param) {
                        return new ReadOnlyStringWrapper(
                                param.getValue().getDate()
                        );
                    }
                }
        );

    }

    public void ucitajPoId(){

        int id=Integer.parseInt(idPrompt.getText());

        try{
            List<Booking> lista= ReadObject.getBookingsId(id);

            bookingTable.setItems(FXCollections.observableArrayList(lista));
        } catch (Exception e) {
            DialogAlert.error("Nemoguce procitati rezervacije");
        }
    }

    public void ucitajPoDatumu() {

        String datum=datumPromt.getValue().toString();
        try {
            List<Booking> lista = ReadObject.getBookingsDate(datum);

            bookingTable.setItems(FXCollections.observableArrayList(lista));
        }catch (Exception e){
            DialogAlert.error("Nemoguce procitati rezervacije");
        }
    }

    public void ucitajPoLokaciji(){
        try {
            List<Booking> lista = ReadObject.getBookingsLocation(lokacijaPrompt.getValue());
            bookingTable.setItems(FXCollections.observableArrayList(lista));
        } catch (Exception e) {
            DialogAlert.error("Nemoguce procitati rezervacije");
        }
    }


}

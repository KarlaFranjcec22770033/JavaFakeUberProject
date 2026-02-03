package controllers;

import database.ReadObject;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entity.Booking;
import model.entity.ENUM.RoleEnum;
import model.entity.JSON.BackupData;
import model.entity.JSON.JsonFiles;
import model.entity.Passanger;
import model.entity.User;
import model.entity.Vehicle;
import utils.DialogAlert;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> userName;
    @FXML
    private TableColumn<User, String> userRole;
    @FXML
    private TableColumn<User, Integer> userId;

    @FXML
    private Button zadnje;
    @FXML
    private ProgressIndicator spinner;

    public void initialize() {

        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        userName.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<User, String>,
                        ObservableValue<String>>() {
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<User, String> param) {
                        return new ReadOnlyStringWrapper(
                                param.getValue().getName()
                        );
                    }
                }
        );

        userRole.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<User, String>,
                        ObservableValue<String>>() {
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<User, String> param) {
                        return new ReadOnlyStringWrapper(
                                param.getValue().getRole().toString()
                        );
                    }
                }
        );

        userId.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<User, Integer>, ObservableValue<Integer>>() {
                    public ObservableValue<Integer> call(
                            TableColumn.CellDataFeatures<User, Integer> param) {
                        return new ReadOnlyObjectWrapper<>(param.getValue().getId());
                    }
                }
        );
    }

    @FXML
    public void ucitajKorisnike() {
        try {

            List<User> usersList = ReadObject.getPassangers();

            userTable.setItems(FXCollections.observableArrayList(usersList));

        } catch (Exception e) {
            DialogAlert.error("Greska prilikom ucitavanja korisnika");
        }

    }

    @FXML
    public void ucitajVozace() {
        try {
            List<User> users = ReadObject.getDrivers();
            userTable.setItems(FXCollections.observableArrayList(users));
        } catch (Exception e) {
            DialogAlert.error("Greska prilikom ucitavanja vozaca");
        }
    }

    public void zadnje() {

        zadnje.setText("Učitavanje...");
        zadnje.setVisible(false);

        spinner.setVisible(true);

        Thread.startVirtualThread(() -> {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
            Platform.runLater(() -> {

                userTable.setVisible(false);

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/zadnji-view.fxml"));
                    Scene scene = new Scene(loader.load(), 450, 370);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Zadnji unos u bazu podataka");
                    stage.show();
                } catch (Exception e) {
                    Platform.runLater(() -> {
                    DialogAlert.error("Greska prilikom ucitavanja i otvaranja prozora");
                    });
                }

                spinner.setVisible(false);
                zadnje.setText("UCITAJ PONOVO");
                zadnje.setVisible(true);

                userTable.setVisible(true);

            });

        });

    }
}

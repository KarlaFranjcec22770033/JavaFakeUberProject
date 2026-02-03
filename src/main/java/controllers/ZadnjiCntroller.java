package controllers;

import database.MyConnection;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.entity.Booking;
import model.entity.ENUM.RoleEnum;
import model.entity.Passanger;
import model.entity.User;
import model.entity.Vehicle;
import model.entity.interfaces.Driver;
import utils.DialogAlert;

import javax.management.relation.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZadnjiCntroller {

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> users;

    @FXML
    private TableView<Booking> bookingsTable;
    @FXML
    private TableColumn<Booking, String> bookings;

    @FXML
    private TableView<String> rolesTable;
    @FXML
    private TableColumn<String, String> roles;

    @FXML
    private TableView<Vehicle> vehiclesTable;
    @FXML
    private TableColumn<Vehicle, String> vehicles;

    public void initialize() {


        users.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call
                            (TableColumn.CellDataFeatures<User, String> param) {
                        return new ReadOnlyStringWrapper(param.getValue().toString());
                    }
                });

        bookings.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Booking, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call
                            (TableColumn.CellDataFeatures<Booking, String> param) {
                        return new ReadOnlyStringWrapper(param.getValue().toString());
                    }
                });

        roles.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call
                            (TableColumn.CellDataFeatures<String, String> param) {
                        return new ReadOnlyStringWrapper(param.getValue());
                    }
                });

        vehicles.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Vehicle, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call
                            (TableColumn.CellDataFeatures<Vehicle, String> param) {
                        return new ReadOnlyStringWrapper(param.getValue().toString());
                    }
                });

        ucitaj();

    }

    public void ucitaj(){
        try (Connection con = MyConnection.getConnection(); Statement ps = con.createStatement()) {
            ResultSet rs = ps.executeQuery("select * from users order by id desc limit 1");

            List<User> users = new ArrayList<>();

            while (rs.next()) {
                if (rs.getInt("id") == 1) {
                    User novi = new Passanger();
                    novi.setName(rs.getString("userName"));
                    novi.setLocation(rs.getString("location"));
                    novi.setId(rs.getInt("id"));
                    novi.setRole(RoleEnum.PUTNIK);

                    users.add(novi);
                } else {
                    User novi = new Driver();
                    novi.setName(rs.getString("userName"));
                    novi.setLocation(rs.getString("location"));
                    novi.setId(rs.getInt("id"));
                    novi.setRole(RoleEnum.VOZAC);

                    users.add(novi);
                }
            }

            ResultSet rs2 = ps.executeQuery("select * from bookings order by id desc limit 1");
            List<Booking> bookings = new ArrayList<>();
            while (rs2.next()) {
                Booking booking = new Booking();
                booking.setId(rs2.getInt("id"));
                booking.setStart(rs2.getString("locationStart"));
                booking.setEnd(rs2.getString("locationEnd"));
                booking.setUserId(rs2.getInt("userId"));
                booking.setDate(rs2.getString("bookingDate"));

                bookings.add(booking);
            }

            ResultSet rs3 = ps.executeQuery("select * from vehicles order by id desc limit 1");
            List<Vehicle> vehicles = new ArrayList<>();
            while (rs3.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs3.getInt("id"));
                vehicle.setDriverId(rs3.getInt("driverId"));
                vehicle.setRegistration(rs3.getString("registration"));
                vehicle.setModel(rs3.getString("model"));
                vehicle.setColor(rs3.getString("color"));

                vehicles.add(vehicle);
            }

            ResultSet rs4 = ps.executeQuery("select * from roles order by id desc limit 1");
            List<String> roles = new ArrayList<>();
            while (rs4.next()) {
                roles.add(rs4.getString("nameRole") + " " + rs4.getString("id"));
            }

            Platform.runLater(()->{
                usersTable.setItems(FXCollections.observableList(users));
                bookingsTable.setItems(FXCollections.observableList(bookings));
                rolesTable.setItems(FXCollections.observableList(roles));
                vehiclesTable.setItems(FXCollections.observableList(vehicles));
            });

        } catch (Exception e) {
            Platform.runLater(()->{DialogAlert.error(e.getMessage());});

        }
    }
}



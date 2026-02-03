package controllers;

import database.MyConnection;
import database.ReadObject;
import database.SaveObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.entity.JSON.JsonFiles;
import model.entity.User;
import model.entity.Vehicle;
import model.entity.interfaces.Driver;
import utils.DialogAlert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class VozacInfoController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField ime;
    @FXML
    private TextField skrivenaLozinka;
    @FXML
    private TextField prikazLozinka;
    @FXML
    private CheckBox vidljivo;
    @FXML
    private CheckBox prijaviMe;

    private ObservableList<Vehicle> listVehicle;

    public void prikaziLozinku(){
        if(vidljivo.isSelected()){
            prikazLozinka.setText(skrivenaLozinka.getText());
        }else{
            prikazLozinka.setText("");
        }
    }

    public void prijaviMe(){
        if(prijaviMe.isSelected()&&!(ime.getText().isBlank())){
            try {
                List<User> lista = ReadObject.getDrivers();
                User novi = new Driver();
                novi.setName(ime.getText());
                novi.setPassword(skrivenaLozinka.getText());

                lista.add(novi);

                SaveObject.executeQueryUsersNoLocation(novi.getName(), novi.getPassword());

                DialogAlert.infoZapis();

            }catch (Exception e){
                DialogAlert.error("Greska prilikom spajanja na bazu");
            }

        }else{
            DialogAlert.error("Nema prikaza za novog vozaca");
            prijaviMe.setSelected(false);
        }
    }

    public void novoVozilo(){

         try{
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/novoVozilo-view.fxml"));
             Scene scene = new Scene(loader.load(),400,300);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.setTitle("Novo vozilo");
             stage.showAndWait();

             List<User> lista = ReadObject.getDrivers();
             int id=lista.stream().map(User::getId).toList().getLast();

             List<Vehicle> list=ReadObject.getVehiclesId(id);
             listVehicle=FXCollections.observableArrayList(list);
             ListView<Vehicle> vozila=new ListView<>(listVehicle);

            vozila.setCellFactory(vehicleListView -> new ListCell<Vehicle>(){
                @Override
                protected void updateItem(Vehicle item, boolean empty){
                    super.updateItem(item, empty);
                    if(item==null||empty){
                        setText(null);
                    }else {
                        setText(item.toString());
                    }
                }

            });

            vozila.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount()==2){

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Obrisi vozilo");
                    alert.setContentText("Zelite obrisati vozilo "+ vozila.getSelectionModel().getSelectedItem());

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        try(Connection con= MyConnection.getConnection()){

                            PreparedStatement ps=con.prepareStatement("delete from vehicles where id=?");
                            ps.setInt(1, vozila.getSelectionModel().getSelectedItem().getId());
                            ps.executeUpdate();

                            DialogAlert.infoZapisDelete();

                            listVehicle.remove(vozila.getSelectionModel().getSelectedItem());

                        }catch(Exception e){
                            DialogAlert.error("Greska prilikom spanjanja baze");
                        }
                    } else {

                    }

                }
            });

            borderPane.setRight(vozila);

         }catch (Exception e){
             DialogAlert.error("Greska kod unosa vozila");
         }
    }



}

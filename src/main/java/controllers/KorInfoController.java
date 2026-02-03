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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.entity.Booking;
import model.entity.JSON.JsonFiles;
import model.entity.Passanger;
import model.entity.User;
import utils.DialogAlert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KorInfoController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField imeKorisnika;
    @FXML
    private TextField pocLokacija;
    @FXML
    private PasswordField skrivenaLozinka;
    @FXML
    private CheckBox vidljivo;
    @FXML
    private TextField prikazLozinka;
    @FXML
    private CheckBox prijaviMe;

    @FXML
    private Button novaVoznja;

    private ObservableList<Booking> listaBookinga;


    public void initialize(){
    }

    public void save() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/novaVoznja-view.fxml"));
            Scene scene = new Scene(loader.load(), 450, 300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Nova voznja");
            stage.showAndWait();

            List<Booking> rezervacije = ReadObject.getAllBookings();
            int id = rezervacije.stream().map(Booking::getUserId).toList().getLast();
            listaBookinga = FXCollections.observableArrayList(ReadObject.getBookingsId(id));

           ListView<Booking> voznje=new ListView<>(listaBookinga);

            voznje.setCellFactory((listValue -> new ListCell<Booking>() {

                protected void updateItem(Booking booking, boolean empty) {
                            super.updateItem(booking, empty);
                            if (empty || booking == null) {
                                setText(null);

                            } else {
                              setText(booking.toString());
                            }

                        }
                    })
            );

            voznje.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Obrisi rezervaciju");
                    alert.setContentText(" Zelite obrisati rezervaciju "+ voznje.getSelectionModel().getSelectedItem());

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        try(Connection con= MyConnection.getConnection()){

                            PreparedStatement ps=con.prepareStatement("delete from bookings where id=?");
                            ps.setInt(1, voznje.getSelectionModel().getSelectedItem().getId());
                            ps.executeUpdate();

                            DialogAlert.infoZapisDelete();

                           listaBookinga.removeAll(voznje.getSelectionModel().getSelectedItem());

                        }catch(Exception e){
                            DialogAlert.error("Greska prilikom spanjanja baze");
                        }
                    } else {

                    }
                }
            });

            borderPane.setRight(voznje);


        } catch (Exception exception) {
            DialogAlert.error("Nemoguce otvoriti unos nove voznje");
        }

    }


    public void prikaziLozinku(){
        if(vidljivo.isSelected()){
            prikazLozinka.setText(skrivenaLozinka.getText());
        }else{
            prikazLozinka.setText("");
        }

    }

    public void spremi(){
        if(prijaviMe.isSelected()&&!(imeKorisnika.getText().isBlank())&&!(pocLokacija.getText().isBlank())){
            User novi=new Passanger();
            novi.setName(imeKorisnika.getText());
            novi.setLocation(pocLokacija.getText());
            novi.setPassword(skrivenaLozinka.getText());

            try {

                List<User> lista = ReadObject.getPassangers();
                int id = lista.stream().map(User::getId).max(Integer::compareTo).get();
                novi.setId(id + 1);

            }catch (Exception e){
                DialogAlert.error("Greska prilikom rada s bazom");
            }
            try {
                SaveObject.executeQueryUsers(novi.getName(), novi.getPassword(), novi.getLocation());
                DialogAlert.infoZapis();
            }catch (Exception e){
                DialogAlert.error("Nemoguce nastaviti");
            }
        }else{
            DialogAlert.error("Nije unesen novi korisnik!");
            prijaviMe.setSelected(false);
        }
    }
}


package controllers;

import app.UberApp;
import database.MyConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import utils.DialogAlert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.concurrent.Executors;

public class MenuContorller {

    @FXML
    private MenuItem vozilaItem;
    @FXML
    private MenuItem infoVozacItem;
    @FXML
    private MenuItem rezervacijeItem;
    @FXML
    private MenuItem infoKorisnikItem;
    @FXML
    private MenuItem reload;
    @FXML
    private MenuItem upload;
    @FXML
    private Menu tables;

    public void showVozila() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/vozila-view.fxml")
            );
            Scene scene = new Scene(loader.load(), 600, 400);
            UberApp.getMainStage().setTitle("Vozila");
            UberApp.getMainStage().setScene(scene);
            UberApp.getMainStage().show();
        } catch (Exception e) {
            DialogAlert.error("Greska prilikom otvaranja prozora");
            e.printStackTrace();
        }
    }

    public void showKorisnikInfo() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/korisnikInfo-view.fxml")
            );
            Scene scene = new Scene(loader.load(), 600, 400);
            UberApp.getMainStage().setTitle("Korisnicke informacije");
            UberApp.getMainStage().setScene(scene);
            UberApp.getMainStage().show();
        } catch (Exception e) {
            DialogAlert.error("Greska prilikom otvaranja prozora");
        }
    }

    public void showVozacInfo() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/vozacInfo-view.fxml")
            );
            Scene scene = new Scene(loader.load(), 600, 400);
            UberApp.getMainStage().setTitle("Informacije vozaca");
            UberApp.getMainStage().setScene(scene);
            UberApp.getMainStage().show();
        } catch (Exception e) {
            DialogAlert.error("Greska prilikom otvaranja prozora");
        }
    }

    public void showRezervacije() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/rezervacije-view.fxml")
            );
            Scene scene = new Scene(loader.load(), 600, 400);
            UberApp.getMainStage().setTitle("Rezervacije");
            UberApp.getMainStage().setScene(scene);
            UberApp.getMainStage().show();
        } catch (Exception e) {
            DialogAlert.error("Greska prilikom otvaranja prozora");
        }
    }


    public void showMain(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/main-view.fxml")
            );
            Scene scene = new Scene(loader.load(), 600, 400);
            UberApp.getMainStage().setTitle("FakeUber");
            UberApp.getMainStage().setScene(scene);
            UberApp.getMainStage().show();
        } catch (Exception e) {
            DialogAlert.error("Greska prilikom otvaranja prozora");
        }
    }

    public void backupTablice(ActionEvent event) {

        MenuItem item = (MenuItem) event.getSource();
        String tableName = item.getText().toLowerCase();

            Thread.startVirtualThread(() -> {
                try (Connection con = MyConnection.getConnection()) {

                    try(PreparedStatement databaseCreate=con.prepareStatement("create database if not exists backupFakeUberGui");) {
                        databaseCreate.execute();
                    }

                    PreparedStatement databaseChange=con.prepareStatement("use backupFakeUberGui");
                    PreparedStatement drop=con.prepareStatement("drop table if exists "+tableName+"_BACKUP");
                    PreparedStatement pst = con.prepareStatement("create table "+tableName+"_BACKUP as select*from fakeubergui."+tableName+"");

                    databaseChange.execute();
                    drop.execute();
                    pst.execute();


                    Thread.sleep(5000);//da se vidi razlika

                    Platform.runLater(() -> {
                        File file=new File("src/main/resources/backupFakeUberGui.properties");
                        File propertiesFile=new File("src/main/resources/database.properties");

                        try (FileWriter fw = new FileWriter(file)) {
                            Properties prop = new Properties();
                            prop.load(new FileReader(propertiesFile));

                            String url=prop.getProperty("database");
                            String user=prop.getProperty("user");
                            String password=prop.getProperty("pass");

                            url=url.replace("fakeUberGui","backupFakeUberGui");

                            String upisi="database="+url+"\nuser="+user+"\npass="+password;

                            fw.write(upisi);

                        } catch (Exception e) {
                            DialogAlert.error("Greska prilikom zapisivanja novih podataka za bazu\n"+e.getMessage());
                        }

                        DialogAlert.infoZapisBackup();
                    });

                } catch (Exception e) {
                    Platform.runLater(() -> {
                        DialogAlert.error(e.getMessage());});

                }
            });

    }

}

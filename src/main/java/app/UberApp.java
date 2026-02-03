package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DialogAlert;
import java.io.IOException;

public class UberApp extends Application {

    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/main-view.fxml")
            );

            Scene scene = new Scene(loader.load(), 600, 400);
            stage.setTitle("FakeUber");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            DialogAlert.error("Greska prilikom kreiranja programa");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

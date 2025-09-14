package teemo.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import teemo.Teemo;

/**
 * A GUI for Teemo using FXML
 */
public class Main extends Application {
    private Teemo teemo = new Teemo("data/teemo.txt");

    @Override
    public void start(Stage stage) {
        // load FXML and set up the scene
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setTeemo(teemo);     // inject the Teemo instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

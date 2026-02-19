package zwee;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Zwee using FXML with responsive design.
 */
public class Main extends Application {

    private Zwee zwee = new Zwee("data/zwee.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            
            // Configure stage for resizable window
            stage.setTitle("Zwee - Task Manager");
            stage.setMinWidth(400.0);
            stage.setMinHeight(500.0);
            stage.setWidth(500.0);
            stage.setHeight(600.0);
            
            fxmlLoader.<MainWindow>getController().setZwee(zwee);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


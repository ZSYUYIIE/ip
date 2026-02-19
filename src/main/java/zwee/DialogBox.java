package zwee;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents a dialog box with text and avatar for asymmetric conversation display.
 * Supports different styling for user messages, bot messages, and error messages.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Applies user message styling: right-aligned with blue background.
     */
    private void styleAsUser() {
        setAlignment(Pos.TOP_RIGHT);
        this.getStyleClass().add("dialog-user");
        dialog.getStyleClass().add("dialog-text");
        // Move image to the right by reversing children order
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
    }

    /**
     * Applies bot message styling: left-aligned with light gray background.
     */
    private void styleAsBot() {
        setAlignment(Pos.TOP_LEFT);
        this.getStyleClass().add("dialog-bot");
        dialog.getStyleClass().add("dialog-text");
    }

    /**
     * Applies error message styling: left-aligned with red/pink background.
     */
    private void styleAsError() {
        setAlignment(Pos.TOP_LEFT);
        this.getStyleClass().add("dialog-error");
        dialog.getStyleClass().add("dialog-text");
    }

    /**
     * Creates a user dialog box (blue, right-aligned).
     */
    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.styleAsUser();
        return db;
    }

    /**
     * Creates a bot dialog box (gray, left-aligned).
     */
    public static DialogBox getZweeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.styleAsBot();
        return db;
    }

    /**
     * Creates an error dialog box (red/pink, left-aligned).
     */
    public static DialogBox getErrorDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.styleAsError();
        return db;
    }
}

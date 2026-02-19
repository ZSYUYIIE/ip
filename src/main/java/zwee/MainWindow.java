package zwee;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import zwee.ZweeException;

/**
 * Controller for the main GUI with improved UX.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Zwee zwee;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/ZeUser.png"));
    private Image zweeeImage = new Image(this.getClass().getResourceAsStream("/images/ZeZwee.png"));

    @FXML
    public void initialize() {
        // Auto-scroll to bottom as new messages arrive
        dialogContainer.heightProperty().addListener((observable) -> 
            scrollPane.setVvalue(1.0)
        );
        // Focus on input field for better UX
        userInput.requestFocus();
    }

    /** Injects the Zwee instance */
    public void setZwee(Zwee z) {
        this.zwee = z;
        String startupMessage = zwee.showStartup();
        addBotDialog(startupMessage);
    }

    /**
     * Creates two dialog boxes, one for user input and one for bot response.
     * Handles command execution and error styling.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isEmpty()) {
            return;
        }
        
        addUserDialog(input);
        
        try {
            String response = zwee.getResponse(input);
            addBotDialog(response);
        } catch (ZweeException e) {
            addErrorDialog(e.getMessage());
        } catch (Exception e) {
            addErrorDialog("An unexpected error occurred: " + e.getMessage());
        }
        
        userInput.clear();
        userInput.requestFocus();
    }

    /**
     * Adds a user message dialog box (right-aligned, blue background).
     */
    private void addUserDialog(String text) {
        HBox userBox = DialogBox.getUserDialog(text, userImage);
        dialogContainer.getChildren().add(userBox);
    }

    /**
     * Adds a bot message dialog box (left-aligned, light gray background).
     */
    private void addBotDialog(String text) {
        HBox botBox = DialogBox.getZweeDialog(text, zweeeImage);
        dialogContainer.getChildren().add(botBox);
    }

    /**
     * Adds an error message dialog box with distinct error styling.
     */
    private void addErrorDialog(String text) {
        HBox errorBox = DialogBox.getErrorDialog(text, zweeeImage);
        dialogContainer.getChildren().add(errorBox);
    }
}


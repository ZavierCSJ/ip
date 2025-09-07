package teemo.gui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import teemo.Teemo;

/**
 * Controller for the main GUI.
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

    private Teemo teemo;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/minionPNG.jpg"));
    private Image teemoImage = new Image(this.getClass().getResourceAsStream("/images/teemoPNG.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Show welcome message
        String welcomeMessage = "Hello! I'm Teemo\nWhat can I do for you?";
        dialogContainer.getChildren().add(
                DialogBox.getTeemoDialog(welcomeMessage, teemoImage)
        );
    }

    /** Injects the Teemo instance */
    public void setTeemo(Teemo t) {
        teemo = t;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = teemo.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getTeemoDialog(response, teemoImage)
        );
        userInput.clear();

        // Exit application if user says bye
        if (input.trim().equals("bye")) {
            javafx.application.Platform.exit();
        }
    }
}

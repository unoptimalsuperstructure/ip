package sanyueqi.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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

    private SanYueQi syq;

    private Image userImage =
            new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));

    private Image syqImage =
            new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Syq instance */
    public void setSyq(SanYueQi s) {
        syq = s;
    }

    public void makeInitResponseStatusDialog() {
        String initResponseStatus = syq.getInitResponseStatus();
        dialogContainer.getChildren().add(
                DialogBox.getSyqDialog(initResponseStatus, syqImage));
    }

    public void makeFinalResponseStatusDialog() {
        String finalResponseStatus = syq.getFinalResponseStatus();
        dialogContainer.getChildren().add(
                DialogBox.getSyqDialog(finalResponseStatus, syqImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other
     * containing Syq's reply, then appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws InterruptedException {
        String input = userInput.getText();
        String response = syq.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSyqDialog(response, syqImage)
        );

        if (response.equals("Bye. Hope to see you again soon!")) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> System.exit(0));
            pause.play();
        }

        userInput.clear();
    }
}
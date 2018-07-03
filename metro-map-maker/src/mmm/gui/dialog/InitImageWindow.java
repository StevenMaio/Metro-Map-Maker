package mmm.gui.dialog;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import static djf.settings.AppStartupConstants.PATH_WORK;
import java.io.File;
import javafx.stage.Stage;

public class InitImageWindow extends Stage {

    // The single instance
    private static InitImageWindow singleton;
    private static final String OK = "OK";
    private static final String CANCEL = "Cancel";
    private static final String ELLIPSIS = "...";
    private static final String CHOOSE_IMAGE = "Choose Image";

    // 
    private Label messageLabel;
    private boolean ready;
    private TextField textField;

    private InitImageWindow() {
    }

    /**
     * Returns the single instance of this class
     *
     * @return The single instance of this class
     */
    public static InitImageWindow getSingleton() {
        if (singleton == null) {
            singleton = new InitImageWindow();
        }

        return singleton;
    }

    public void init(Stage primaryStage) {
        // Set window mode to Modal
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);

        textField = new TextField();
        textField.setEditable(false);   // Make teh text uneditable
        ready = false;
        messageLabel = new Label();

        // Containers
        BorderPane mainContainer = new BorderPane();
        VBox centerPane = new VBox();
        HBox bottomPane = new HBox();

        // Init the main container
        mainContainer.setCenter(centerPane);
        mainContainer.setBottom(bottomPane);

        // center pane stuff
        HBox centerHBox = new HBox();
        Button chooseFileButton = new Button(ELLIPSIS);
        centerHBox.getChildren().addAll(textField, chooseFileButton);
        centerPane.getChildren().addAll(messageLabel, centerHBox);

        // Buttons and such
        Button okButton = new Button(OK);
        Button cancelButton = new Button(CANCEL);
        bottomPane.getChildren().addAll(okButton, cancelButton);

        // set style properties
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setSpacing(10);

        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setSpacing(10);

        centerHBox.setAlignment(Pos.CENTER);

        // Set the event handlers
        okButton.setOnAction(e -> {
            ready = true;
            hide();
        });
        cancelButton.setOnAction(e -> {
            hide();
        });
        chooseFileButton.setOnAction(e -> {
            processChooseFile(primaryStage);
        });

        Scene scene = new Scene(mainContainer, 300, 200);
        setScene(scene);
    }

    public void show(String messageTitle, String labelText) {
        ready = false;

        setTitle(messageTitle);
        messageLabel.setText(labelText);
        textField.setText("");
        showAndWait();
    }

    /**
     * Returns the text in the TextField
     *
     * @return The Text element of the TextField
     */
    public String getText() {
        return textField.getText();
    }

    private void processChooseFile(Stage primaryStage) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
        fc.setTitle(CHOOSE_IMAGE);
        File selectedFile = fc.showOpenDialog(primaryStage);

        String filePath = selectedFile.getPath();

        // Set the Text field in the window
        InitImageWindow.singleton.textField.setText(filePath);
    }

    //////////////////////////////
    // Accessor/Mutator Methods //
    //////////////////////////////
    public boolean isReady() {
        return ready;
    }
}

package mmm.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static mmm.css.MMMStyle.CLASS_WINDOW_PANE;

public class InitImageWindow extends Stage {
    // The single instance
    private static InitImageWindow singleton;
    private static final String OK = "OK";
    private static final String CANCEL = "Cancel";
    private static final String ELLIPSIS = "...";
    
    // 
    private Label messageLabel;
    private boolean ready;
    private TextField textField;
    
    private InitImageWindow() {};
    
    /**
     * Returns the single instance of this class
     * @return The single instance of this class
     */
    public static InitImageWindow getSingleton() {
        if (singleton == null)
            singleton = new InitImageWindow();
        
        return singleton;
    }
    
    public void init(Stage primaryStage) {
        // Set window mode to Modal
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        textField = new TextField();
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
            processChooseFile();
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
     * @return The Text element of the TextField
     */
    public String getText() {
        return textField.getText();
    }
    
    private void processChooseFile() {}
    
    //////////////////////////////
    // Accessor/Mutator Methods //
    //////////////////////////////

    public boolean isReady() {
        return ready;
    }
}

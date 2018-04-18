/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mmm.data.MetroStation;
import mmm.data.Path;

/**
 *
 * @author steve
 */
public class BorderedMessageDialogSingleton extends Stage {
    // Singleton
    private static BorderedMessageDialogSingleton singleton;
    public static final String DESTINATIONS = "destinations";
    private static final String ORIGIN = "Origin: ";
    private static final String DESTINATION = "Destination: ";
    private static final String ROUTE_FROM = "Route from ";
    private static final String TO = "to ";
    private static final String ROUTE = "Route:\n";
    private static final String PATH_FIND_TITLE = "Route Finder";
    
    // instance variables
    private Label messageTitleLabel;
    private TextArea messageBodyTextArea;
    
    /**
     * This method returns the singleton of BorderedMessageDialogSingleton
     * @return 
     *      The singleton of this class
     */
    public static BorderedMessageDialogSingleton getSingleton() {
        if (singleton == null)
            singleton = new BorderedMessageDialogSingleton();
        
        return singleton;
    }
    
    private BorderedMessageDialogSingleton() {}
    
    /**
     * This method initializes the current instance of this class and all of its
     * content.
     * @param primaryStage 
     *      The owner of this instance
     */
    public void init(Stage primaryStage) {
        // make trhe window modal
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        // Initialize variables and properties
        messageTitleLabel = new Label();
        messageBodyTextArea = new TextArea();
        messageBodyTextArea.setEditable(false); // make the textArea uneditable
        
        // Pane settings
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));
        
        // Pane content
        pane.setTop(messageTitleLabel);
        pane.setCenter(messageBodyTextArea);
        Button okButton = new Button("OK"); // LANGUAGE PROPERTIES CRAP MAYBE
        pane.setBottom(okButton);
        
        okButton.setOnAction(e -> {
            hide();
        });
        
        // Create and a scene and put it in the window
        Scene scene = new Scene(pane);
        setScene(scene);
    }
    
    /**
     * This method shows the 
     * @param messageTitle
     * @param messageLabel
     * @param messageBody 
     */
    public void show(String messageTitle, String messageLabel, 
            String messageBody) {
        
        // set the Title of the window, Label text and TextArea text
        setTitle(messageTitle);
        messageTitleLabel.setText(messageLabel);
        messageBodyTextArea.setText(messageBody);
        
        showAndWait();
    }
    
    public void show(Path path){
        String startStation = path.getStartStation().getName();
        String endStation = path.getEndStation().getName();
        
        String label = ROUTE_FROM + startStation + TO + endStation;
        
        messageTitleLabel.setText(label);
        
        String bodyMessage = "";
        
        bodyMessage += String.format("%s%s\n",ORIGIN, startStation);
        bodyMessage += String.format("%s%s\n", DESTINATION, endStation);
        bodyMessage += "\n" + ROUTE + path.getPaths();
        
        messageBodyTextArea.setText(bodyMessage);
        setTitle(PATH_FIND_TITLE);
        
        showAndWait();
    }
}
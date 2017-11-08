/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import javafx.geometry.Insets;
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

/**
 *
 * @author steve
 */
public class EnterTextDialogSingleton extends Stage {
    // static singleton
    private static EnterTextDialogSingleton singleton;
    
    private Label messageLabel;
    private TextField textField;
    private boolean ready;
    
    private EnterTextDialogSingleton() {}
    
    /**
     * This method returns the singleton
     * 
     * @return 
     *      singleton
     */
    public static EnterTextDialogSingleton getSingleton() {
        if (singleton == null)
            singleton = new EnterTextDialogSingleton();
        
        return singleton;
    }
    
    /**
     * This method initializes the window and all of it's features.
     * 
     * @param primaryStage
     *      The owner of an instance of EnterTextDialogSingleton
     */
    public void init(Stage primaryStage) {
        // Set window mode to Modal
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        // Initialize all the instance variables
        messageLabel = new Label();
        textField = new TextField();
        ready = false;
        
        // Containers
        BorderPane mainContainer = new BorderPane();
        VBox centerPane = new VBox();
        HBox bottomPane = new HBox();
        
        // Main container content and settings
        mainContainer.setPadding(new Insets(10));
        mainContainer.setCenter(centerPane);
        mainContainer.setBottom(bottomPane);
        
        // centerPane content and settings
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setSpacing(10);
        centerPane.setPadding(new Insets(10));
        centerPane.getChildren().addAll(messageLabel, textField);
        
        // bottomPane content and settings
        // CHANGE THESE LATER TO LOAD FROM LANGUAGE PROPERTIES
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setSpacing(10);
        bottomPane.getChildren().addAll(okButton, cancelButton);
        
        // Controllers
        okButton.setOnAction(e -> {
            ready = true;
            hide();
        });
        
        cancelButton.setOnAction(e -> {
            hide();
        });
        
        // Set the scene and put this in it
        Scene scene = new Scene(mainContainer);
        setScene(scene);
    }
    
    /**
     * This method displays the window with the contents set to the parameters
     * @param messageTitle
     *      The value of this.Title
     * @param labelText
     *      The value of messageLabel
     */
    public void show(String messageTitle, String labelText) {
        ready = false;
        
        setTitle(messageTitle);
        messageLabel.setText(labelText);
        showAndWait();
    }
    
    /**
     * This method returns the value of the textField variable/
     * @return 
     *      The value of the textField variable
     */
    public String getText() {
        return textField.getText();
    }
    
    //////////////////////////////
    // Accessor/Mutator Methods //
    //////////////////////////////

    public boolean isReady() {
        return ready;
    }
}

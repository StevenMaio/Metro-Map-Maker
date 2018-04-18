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
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mmm.data.MetroLine;

/**
 *
 * @author steve
 */
public class MetroLineSettingsDialogSingleton extends Stage {
    private static MetroLineSettingsDialogSingleton singleton;
    private static final double PREFERRED_WIDTH = 266;
    private static final double PREFERRED_HEIGHT = 106;
    
    private Label nameLabel;
    private TextField nameTextField;
    private ColorPicker lineColorPicker;
    private boolean ready;
    
    private MetroLineSettingsDialogSingleton() {}
    
    /**
     * Returns the singleton of MetroLineSettingsDialogSingleton. Also initializes
     * singleton if it hasn't been initialized already
     * @return 
     *      The singleton of this MetroLineSettingsDialogSingleton
     */
    public static MetroLineSettingsDialogSingleton getSingleton() {
        if (singleton == null) 
            singleton = new MetroLineSettingsDialogSingleton();
        
        return singleton;
    }
    
    /**
     * This method initializes an instance of this class
     * @param primaryStage 
     *      The owner of this class
     */
    public void init(Stage primaryStage) {
        // Make it modal
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        // Initialize variables
        nameLabel = new Label("Metro Line");    // LANGUAGE PROPERTIES
        nameTextField = new TextField();
        lineColorPicker = new ColorPicker();
        ready = false;
        
        // Containers
        BorderPane pane = new BorderPane();
        HBox centerPane = new HBox();
        HBox bottomPane = new HBox();
        
        // pane settings and content
        pane.setPadding(new Insets(10));
        pane.setPrefHeight(PREFERRED_HEIGHT);
        pane.setPrefWidth(PREFERRED_WIDTH);
        pane.setCenter(centerPane);
        pane.setBottom(bottomPane);
        
        // centerPane and contents
        centerPane.setSpacing(20);
        VBox centerPaneVBox = new VBox();
        centerPaneVBox.getChildren().addAll(nameLabel, nameTextField);
        centerPane.getChildren().addAll(centerPaneVBox, lineColorPicker);
        HBox.setMargin(lineColorPicker, new Insets(16, 0, 0, 0));
        
        // bottomPane settings and content
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setSpacing(10);
        Button okButton = new Button("Ok");
        Button cancelButton = new Button("Cancel");
        bottomPane.getChildren().addAll(okButton, cancelButton);
        
        // Event handlers
        okButton.setOnAction(e -> {
            ready = true;
            hide();
        });
        
        cancelButton.setOnAction(e -> {
            hide();
        });
        
        // Put it in a window
        Scene scene = new Scene(pane);
        setScene(scene);
    }
    
    /**
     * This method pops open an instance of MetroLineSettingsDialogSingleton and loads
     * the values of the MetroLine into the dialog window
     * @param metroLine 
     *      The Metro LIne whose values are loaded.
     * @param messageTitle
     *      The title of the window
     */
    public void show(MetroLine metroLine, String messageTitle) {
        ready = false;
        setTitle(messageTitle);
        
        // Load MetroLine settings
        nameTextField.setText(metroLine.getName());
        lineColorPicker.setValue(metroLine.getFill());
        
        showAndWait();
    }
    
    /**
     * Returns the value of the nameTextField.text
     * 
     * @return 
     *      The value of nameTextField
     */
    public String getText() {
        return nameTextField.getText();
    }
    
    /**
     * Returns the value of the ColorPicker
     * 
     * @return
     *      The value of lineColorPicker
     */
    public Color getColor() {
        return lineColorPicker.getValue();
    }
    
    //////////////////////////////
    // Accessor/Mutator Methods //
    //////////////////////////////

    public boolean isReady() {
        return ready;
    }
}

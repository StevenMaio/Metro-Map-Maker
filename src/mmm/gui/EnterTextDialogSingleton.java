/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private boolean initialize;
    // The purpose of these two things is for Language Support (maybe)
    private Button okButton;
    private Button cancelButton;
    
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
    public void init(Stage primaryStage) {}
    
    /**
     * This method displays the window with the contents set to the parameters
     * @param messageTitle
     *      The value of this.Title
     * @param messageContent 
     *      The value of messageLabel
     */
    public void show(String messageTitle, String messageContent) {
        initialize = false;
        
        setTitle(messageTitle);
        messageLabel.setText(messageContent);
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

    public boolean isInitialize() {
        return initialize;
    }
}

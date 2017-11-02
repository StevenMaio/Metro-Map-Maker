/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author steve
 */
public class BorderedMessageDialogSingleton extends Stage {
    // Singleton
    private static BorderedMessageDialogSingleton singleton;
    
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
        messageTitleLabel = new Label();
        messageBodyTextArea = new TextArea();
        
        messageBodyTextArea.setEditable(false); // make the textArea uneditable
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
}

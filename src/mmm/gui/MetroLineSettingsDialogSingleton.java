/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mmm.data.MetroLine;

/**
 *
 * @author steve
 */
public class MetroLineSettingsDialogSingleton extends Stage {
    private static MetroLineSettingsDialogSingleton singleton;
    
    private TextField nameTextField;
    private ColorPicker lineColorPicker;
    private boolean continueAction;
    private Button okButton;
    private Button cancelButton;
    
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
    public void init(Stage primaryStage) {}
    
    /**
     * This method pops open an instance of MetroLineSettingsDialogSingleton and loads
     * the values of the MetroLine into the dialog window
     * @param metroLine 
     *      The Metro LIne whose values are loaded.
     * @param messageTitle
     *      The title of the window
     */
    public void show(MetroLine metroLine, String messageTitle) {
        continueAction = false;
        setTitle(messageTitle);
        
        // Load MetroLine settings
        nameTextField.setText(metroLine.getName());
        lineColorPicker.setValue(metroLine.getColor());
        
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

    public boolean getContinueAction() {
        return continueAction;
    }
}

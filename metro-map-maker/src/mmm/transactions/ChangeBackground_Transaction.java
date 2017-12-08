/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static mmm.data.MMMData.DEFAULT_BACKGROUND_COLOR;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import jtps.jTPS_Transaction;
import mmm.data.MMMData;
import mmm.gui.MMMWorkspace;

/**
 *
 * @author steve
 */
public class ChangeBackground_Transaction implements jTPS_Transaction {
    private Background previousBackground;
    private Background newBackground;
    private MMMData data;
    private Pane canvas;
    private boolean newIsImageBackground;
    private boolean oldIsImageBackground;
    private String newImageFilePath;
    private String oldImageFilePath;
    private Color oldColorFill;
    private Color newColorFill;
    
    /**
     * This is the constructor for image backgrounds
     * @param data
     * @param imageFilepath 
     */
    public ChangeBackground_Transaction(MMMData data, String imageFilepath) {
        this.canvas = ((MMMWorkspace)data.getApp().getWorkspaceComponent()).getCanvas();
        this.data = data;
        this.previousBackground = canvas.getBackground();
        this.oldIsImageBackground = data.isImageBackground();
        this.newIsImageBackground = true;
        
        BackgroundImage image = new BackgroundImage(new Image(FILE_PROTOCOL + imageFilepath), 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        
        Background background = new Background(image);
        
        this.newBackground = background;
        
        this.newImageFilePath = imageFilepath;
        this.oldImageFilePath = data.getImageFilepath();
        this.newColorFill = DEFAULT_BACKGROUND_COLOR;
        this.oldColorFill = data.getBackgroundColor();
    }
    
    public ChangeBackground_Transaction(MMMData data, Color newFillColor) {
        this.canvas = ((MMMWorkspace)data.getApp().getWorkspaceComponent()).getCanvas();
        this.data = data;
        this.previousBackground = canvas.getBackground();
        this.oldIsImageBackground = data.isImageBackground();
        this.newIsImageBackground = false;
        
        BackgroundFill fill = new BackgroundFill(newFillColor, null, null);
        
        Background background = new Background(fill);
        
        this.newBackground = background;
        
        this.newImageFilePath = null;
        this.oldImageFilePath = data.getImageFilepath();
        this.newColorFill = newFillColor;
        this.oldColorFill = data.getBackgroundColor();
    }
    @Override
    public void doTransaction() {
        canvas.setBackground(newBackground);
        
        // Set the properties
        data.setImageBackground(newIsImageBackground);
        data.setBackgroundColor(newColorFill);
        data.setImageFilepath(newImageFilePath);
    }

    @Override
    public void undoTransaction() {
        canvas.setBackground(previousBackground);
        
        // Set the properties
        data.setImageBackground(oldIsImageBackground);
        data.setBackgroundColor(oldColorFill);
        data.setImageFilepath(oldImageFilePath);
    }
}
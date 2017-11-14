/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import mmm.data.MMMData;

/**
 *
 * @author steve
 */
public class ChangeBackgroundColor_Transaction implements jTPS_Transaction {
    private Color previousColor;
    private Color newColor;
    private MMMData data;
    private String imageFilepath;
    
    public ChangeBackgroundColor_Transaction(MMMData data, Color newColor) {
        this.data = data;
        this.newColor = newColor;
        this.previousColor = data.getBackgroundColor();
        this.imageFilepath = data.getImageFilepath();
    }
    
    @Override
    public void doTransaction() {
        data.setBackgroundColor(newColor);
        data.setImageFilepath(null);    // This makes sure that the background color is set
        data.refreshBackground();
    }

    @Override
    public void undoTransaction() {
        data.setBackgroundColor(previousColor);
        data.setImageFilepath(imageFilepath);
        data.refreshBackground();
    }
}
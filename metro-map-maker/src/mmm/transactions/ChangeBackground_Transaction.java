/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    
    public ChangeBackground_Transaction(MMMData data, Background newBackground, boolean newIsImageBackground) {
        this.canvas = ((MMMWorkspace)data.getApp().getWorkspaceComponent()).getCanvas();
        this.data = data;
        this.newBackground = newBackground;
        this.previousBackground = canvas.getBackground();
        this.newIsImageBackground = newIsImageBackground;
        this.oldIsImageBackground = data.isImageBackground();
    }
    
    @Override
    public void doTransaction() {
        canvas.setBackground(newBackground);
        data.setImageBackground(newIsImageBackground);
    }

    @Override
    public void undoTransaction() {
        canvas.setBackground(previousBackground);
        data.setImageBackground(oldIsImageBackground);
    }
}
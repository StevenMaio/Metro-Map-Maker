/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import jtps.jTPS_Transaction;
import mmm.data.MMMData;

/**
 *
 * @author steve
 */
public class ChangeFill_Transaction implements jTPS_Transaction {
    private MMMData data;
    private Shape shape;
    private Color oldColor;
    private Color newColor;
    
    public ChangeFill_Transaction(MMMData data, Shape shape, Color newColor) {
        this.data = data;
        this.shape = shape;
        this.oldColor = (Color) shape.getFill();
        this.newColor = newColor;
    }

    @Override
    public void doTransaction() {
        shape.setFill(newColor);
    }

    @Override
    public void undoTransaction() {
        shape.setFill(oldColor);
    }
}

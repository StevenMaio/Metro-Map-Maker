/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import mmm.gui.draggable.DraggableLabel;
import mmm.data.MMMData;

/**
 *
 * @author steve
 */
public class SetFontFill_Transaction implements jTPS_Transaction {
    private MMMData data;
    private DraggableLabel[] labels;
    private Color newColor;
    private Color oldColor;
    
    public SetFontFill_Transaction(MMMData data, Color color, DraggableLabel... labels) {
        this.data = data;
        this.newColor = color;
        this.labels = labels;
        this.oldColor = (Color) labels[0].getFill();
    }

    @Override
    public void doTransaction() {
        for (DraggableLabel label: labels) {
            label.setFill(newColor);
        }
    }

    @Override
    public void undoTransaction() {
        for (DraggableLabel label: labels) {
            label.setFill(oldColor);
        }
    }
}

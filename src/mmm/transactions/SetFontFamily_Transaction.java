/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.gui.draggable.DraggableLabel;
import mmm.data.MMMData;

/**
 *
 * @author steve
 */
public class SetFontFamily_Transaction implements jTPS_Transaction {
    private MMMData data;
    private DraggableLabel[] labels;
    private String oldFontFamily;
    private String newFontFamily;
    
    public SetFontFamily_Transaction(MMMData data, String newFontFamily, 
            DraggableLabel... labels) {
        this.data = data;
        this.labels = labels;
        this.newFontFamily = newFontFamily;
        this.oldFontFamily = labels[0].getFontFamily();
    }

    @Override
    public void doTransaction() {
        for (DraggableLabel label: labels) {
            label.setFontFamily(newFontFamily);
            label.resetStyle();
        }
    }

    @Override
    public void undoTransaction() {
        for (DraggableLabel label: labels) {
            label.setFontFamily(oldFontFamily);
            label.resetStyle();
        }
    }
}

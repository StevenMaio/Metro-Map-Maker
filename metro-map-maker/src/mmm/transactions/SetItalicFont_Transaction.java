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
public class SetItalicFont_Transaction implements jTPS_Transaction {
    private MMMData data;
    private DraggableLabel[] labels;
    private boolean newItalic;
    
    public SetItalicFont_Transaction(MMMData data, boolean newItalic, DraggableLabel... labels) {
        this.data = data;
        this.newItalic = newItalic;
        this.labels = labels;
    }

    @Override
    public void doTransaction() {
        for (DraggableLabel label : labels) {
            label.setItalicized(newItalic);
            label.resetStyle();
        }
    }

    @Override
    public void undoTransaction() {
        for (DraggableLabel label : labels) {
            label.setItalicized(! newItalic);
            label.resetStyle();
        }
    }
}

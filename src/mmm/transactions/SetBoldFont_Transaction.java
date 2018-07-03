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
public class SetBoldFont_Transaction implements jTPS_Transaction {
    private MMMData data;
    private DraggableLabel[] labels;
    boolean newBold;
    
    public SetBoldFont_Transaction(MMMData data, boolean newBold, DraggableLabel... labels) {
        this.labels = labels;
        this.data = data;
        this.newBold = newBold;
    }

    @Override
    public void doTransaction() {
        for (DraggableLabel label: labels) {
            label.setBold(newBold);
            label.resetStyle();
        }
    }

    @Override
    public void undoTransaction() {
        for (DraggableLabel label: labels) {
            label.setBold(! newBold);
            label.resetStyle();
        }
    }
}

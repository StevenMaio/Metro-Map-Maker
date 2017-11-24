package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableLabel;
import mmm.data.MMMData;

/**
 *
 * @author steve
 */
public class SetFontSize_Transaction implements jTPS_Transaction {
    private MMMData data;
    private DraggableLabel[] labels;
    private int oldFontSize;
    private int newFontSize;
    
    public SetFontSize_Transaction(MMMData data, int newFontSize, 
            DraggableLabel... labels) {
        this.data = data;
        this.newFontSize = newFontSize;
        this.oldFontSize = labels[0].getFontSize();
        this.labels = labels;
    }

    @Override
    public void doTransaction() {
        for (DraggableLabel label : labels) {
            label.setFontSize(newFontSize);
            label.resetStyle();
        }
    }

    @Override
    public void undoTransaction() {
        for (DraggableLabel label : labels) {
            label.setFontSize(oldFontSize);
            label.resetStyle();
        }
    }
}

package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableLabel;
import mmm.data.MMMData;

/**
 * Transaction for adding a label to a metro map.
 *
 * @author Steven Maio
 */
public class AddLabel_Transaction implements jTPS_Transaction {
    private MMMData data;
    private DraggableLabel label;
    
    public AddLabel_Transaction(MMMData data, DraggableLabel label) {
        this.data = data;
        this.label = label;
    }

    @Override
    public void doTransaction() {
        data.getShapes().add(label);
    }

    @Override
    public void undoTransaction() {
        data.getShapes().remove(label);
    }
}

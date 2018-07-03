package mmm.transactions;

import javafx.scene.shape.Shape;
import jtps.jTPS_Transaction;
import mmm.data.MMMData;

/**
 *
 * @author steve
 */
public class RemoveItem_Trasnaction implements jTPS_Transaction {
    private MMMData data;
    private Shape item;
    
    public RemoveItem_Trasnaction(MMMData data, Shape item) {
        this.data = data;
        this.item = item;
    }

    @Override
    public void doTransaction() {
        data.getShapes().remove(item);
    }

    @Override
    public void undoTransaction() {
        data.getShapes().add(item);
    }
}

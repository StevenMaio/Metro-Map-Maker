package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableImage;
import mmm.data.MMMData;

/**
 * Transaction for adding an image for a metro map.
 *
 * @author Steven Maio
 */
public class AddImage_Transaction implements jTPS_Transaction {
    private MMMData data;
    private DraggableImage image;
    
    public AddImage_Transaction(MMMData data, DraggableImage image) {
        this.data = data;
        this.image = image;
    }

    @Override
    public void doTransaction() {
        data.getShapes().add(image);
    }

    @Override
    public void undoTransaction() {
        data.getShapes().remove(image);
    }
}

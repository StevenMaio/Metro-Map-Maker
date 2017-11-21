package mmm.transactions;

import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import mmm.data.MMMData;
import mmm.data.MetroLine;

/**
 *
 * @author steve
 */
public class EditMetroLineSettings_Transaction implements jTPS_Transaction {
    private MMMData data;
    private MetroLine metroLine;
    private String oldName;
    private Color oldColor;
    private String newName;
    private Color newColor;
    
    public EditMetroLineSettings_Transaction(MMMData data, MetroLine metroLine,
            String newName, Color newColor) {
        this.data = data;
        this.metroLine = metroLine;
        this.newColor = newColor;
        this.newName = newName;
        this.oldName = metroLine.getName();
        this.oldColor = metroLine.getColor();
    }

    @Override
    public void doTransaction() {
        metroLine.setName(newName);
        metroLine.setColor(newColor);
        metroLine.refreshLineStyle();
        metroLine.getEndLabel().setText(newName);
        metroLine.getStartLabel().setText(newName);
    }

    @Override
    public void undoTransaction() {
        metroLine.setName(oldName);
        metroLine.setColor(oldColor);
        metroLine.refreshLineStyle();
        metroLine.getEndLabel().setText(oldName);
        metroLine.getStartLabel().setText(oldName);
    }
}

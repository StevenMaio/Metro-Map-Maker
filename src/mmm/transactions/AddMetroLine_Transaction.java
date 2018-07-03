/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.MMMData;
import mmm.data.MetroLine;

/**
 *
 * @author steve
 */
public class AddMetroLine_Transaction implements jTPS_Transaction {
    private MMMData data;
    private MetroLine metroLine;
    
    public AddMetroLine_Transaction(MMMData data, MetroLine metroLine) {
        this.data = data;
        this.metroLine = metroLine;
    }

    @Override
    public void doTransaction() {
        data.getMetroLines().add(metroLine);
        data.getShapes().addAll(metroLine.getEndLabel(), 
                metroLine.getStartLabel());
        data.getShapes().addAll(metroLine.getLines());
    }

    @Override
    public void undoTransaction() {
        data.getMetroLines().remove(metroLine);
        data.getShapes().removeAll(metroLine.getEndLabel(), 
                metroLine.getStartLabel());
        data.getShapes().removeAll(metroLine.getLines());
    }
}

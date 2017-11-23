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
public class EditLineThickness_Transaction implements jTPS_Transaction {
    private MMMData data;
    private MetroLine metroLine;
    private double oldThickness;
    private double newThickness;
    
    public EditLineThickness_Transaction(MMMData data, MetroLine metroLine, double newThickness) {
        this.data = data;
        this.metroLine = metroLine;
        this.oldThickness = metroLine.getLineThickness();
        this.newThickness = newThickness;
    }

    @Override
    public void doTransaction() {
        metroLine.setLineThickness(newThickness);
        metroLine.refreshLineStyle();
    }

    @Override
    public void undoTransaction() {
        metroLine.setLineThickness(oldThickness);
        metroLine.refreshLineStyle();
    }
}

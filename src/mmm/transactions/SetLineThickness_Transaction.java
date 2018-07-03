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
public class SetLineThickness_Transaction implements jTPS_Transaction {
    private MMMData data;
    private MetroLine metroLine;
    private double oldThickness;
    private double newThickness;
    
    public SetLineThickness_Transaction(MMMData data, MetroLine metroLine, double thickness) {
        this.data = data;
        this.metroLine = metroLine;
        this.oldThickness = metroLine.getThickness();
        this.newThickness = thickness;
    }

    @Override
    public void doTransaction() {
        metroLine.setThickness(newThickness);
        metroLine.refreshLineStyle();
    }

    @Override
    public void undoTransaction() {
        metroLine.setThickness(oldThickness);
        metroLine.refreshLineStyle();
    }
}

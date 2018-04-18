/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableCircle;
import mmm.data.MMMData;

/**
 *
 * @author steve
 */
public class SetRadius_Transaction implements jTPS_Transaction {
    private MMMData data;
    private DraggableCircle circle;
    private double oldRadius;
    private double newRadius;
    
    public SetRadius_Transaction(MMMData data, DraggableCircle circle,
            double newRadius) {
        this.data = data;
        this.circle = circle;
        this.oldRadius = circle.getRadius();
        this.newRadius = newRadius;
    }

    @Override
    public void doTransaction() {
        circle.setRadius(newRadius);
    }

    @Override
    public void undoTransaction() {
        circle.setRadius(oldRadius);
    }
}

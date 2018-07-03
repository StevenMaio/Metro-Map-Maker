/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.gui.draggable.Draggable;
import mmm.data.MMMData;

/**
 *
 * @author steve
 */
public class MoveShape_Transaction implements jTPS_Transaction {
    private Draggable shape;
    private MMMData data;
    private int originalX;
    private int originalY;
    private int newX;
    private int newY;
    
    public MoveShape_Transaction(MMMData data, Draggable shape) {
        this.data = data;
        this.shape = shape;
        newX = (int) shape.getX();
        newY = (int) shape.getY();
        originalX = (int) data.getStartingPoint().getX();
        originalY = (int) data.getStartingPoint().getY();
    }

    @Override
    public void doTransaction() {
        shape.setX(newX);
        shape.setY(newY);
    }

    @Override
    public void undoTransaction() {
        shape.setX(originalX);
        shape.setY(originalY);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.MMMData;
import mmm.data.MetroStation;

/**
 *
 * @author steve
 */
public class RotateStationLabel_Transaction implements jTPS_Transaction {
    private MMMData data;
    private MetroStation metroStation;
    
    public RotateStationLabel_Transaction(MMMData data, MetroStation metroStation) {
        this.data = data;
        this.metroStation = metroStation;
    }

    @Override
    public void doTransaction() {
        metroStation.rotateLabelCounterClockwise();
    }

    @Override
    public void undoTransaction() {
        metroStation.rotateLabelClockwise();
    }
}

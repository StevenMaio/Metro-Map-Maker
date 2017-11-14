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
public class MoveStationLabel_Transaction implements jTPS_Transaction {
    private MetroStation metroStation;
    private MMMData dataManager;
    
    public MoveStationLabel_Transaction(MMMData dataManager, MetroStation metroStation) {
        this.metroStation = metroStation;
        this.dataManager = dataManager;
    }

    @Override
    public void doTransaction() {
        metroStation.moveLabelLocationClockwise();
        metroStation.bindLabelToCircle();
    }

    @Override
    public void undoTransaction() {
        metroStation.moveLabelLocationCounterClockwise();
        metroStation.bindLabelToCircle();
    }
}

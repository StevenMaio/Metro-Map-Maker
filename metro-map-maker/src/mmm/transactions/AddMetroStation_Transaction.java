/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableCircle;
import mmm.data.DraggableLabel;
import mmm.data.MMMData;
import mmm.data.MetroStation;

/**
 *
 * @author steve
 */
public class AddMetroStation_Transaction implements jTPS_Transaction {
    private MetroStation metroStation;
    private DraggableCircle stationCircle;
    private DraggableLabel stationLabel;
    private MMMData dataManager;
    
    public AddMetroStation_Transaction(MMMData dataManager, MetroStation metroStation) {
        this.metroStation = metroStation;
        this.stationCircle = metroStation.getCircle();
        this.stationLabel = metroStation.getLabel();
        this.dataManager = dataManager;
    }

    @Override
    public void doTransaction() {
        dataManager.getMetroStations().add(metroStation);
        dataManager.getShapes().addAll(stationCircle, stationLabel);
    }

    @Override
    public void undoTransaction() {
        dataManager.getMetroStations().remove(metroStation);
        dataManager.getShapes().removeAll(stationCircle, stationLabel);
    }
}

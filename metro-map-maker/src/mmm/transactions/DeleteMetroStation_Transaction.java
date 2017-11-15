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
public class DeleteMetroStation_Transaction implements jTPS_Transaction {
    private MetroStation metroStation;
    private DraggableCircle stationCircle;
    private DraggableLabel stationLabel;
    private MMMData data;

    public DeleteMetroStation_Transaction(MMMData data, MetroStation metroStation) {
        this.metroStation = metroStation;
        this.stationCircle = metroStation.getStationCircle();
        this.stationLabel = metroStation.getStationLabel();
        this.data = data;
    }
    
    @Override
    public void doTransaction() {
        data.getMetroStations().remove(metroStation);
        data.getShapes().removeAll(stationCircle, stationLabel);
    }

    @Override
    public void undoTransaction() {
        data.getMetroStations().add(metroStation);
        data.getShapes().addAll(stationCircle, stationLabel);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.MMMData;
import mmm.data.MetroLine;
import mmm.data.MetroStation;

/**
 *
 * @author steve
 */
public class AddStationToLine_Transaction implements jTPS_Transaction {
    private MMMData data;
    private MetroLine metroLine;
    private MetroStation metroStation;
    
    public AddStationToLine_Transaction(MMMData data, MetroLine metroLine, MetroStation metroStation) {
        this.data = data;
        this.metroLine = metroLine;
        this.metroStation = metroStation;
    }

    @Override
    public void doTransaction() {
        metroLine.addMetroStation(metroStation);
        metroLine.resetLine(data);
        
        data.getShapes().addAll(metroLine.getLines());
    }

    @Override
    public void undoTransaction() {
        metroLine.remove(metroStation);
        metroLine.resetLine(data);
        
        data.getShapes().addAll(metroLine.getLines());
    }
}
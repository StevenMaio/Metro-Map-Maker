/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import java.util.ArrayList;
import jtps.jTPS_Transaction;
import mmm.data.MMMData;
import mmm.data.MetroLine;
import mmm.data.MetroStation;
import sun.text.normalizer.Trie;

/**
 *
 * @author steve
 */
public class DeleteMetroStation_Transaction implements jTPS_Transaction {
    private MetroStation metroStation;
    private MMMData data;
    private ArrayList<MetroLine> metroLines;

    public DeleteMetroStation_Transaction(MMMData data, MetroStation metroStation) {
        this.metroStation = metroStation;
        this.data = data;
        this.metroLines = metroStation.getMetroLines();
    }
    
    @Override
    public void doTransaction() {
        data.getMetroStations().remove(metroStation);
        data.getShapes().removeAll(metroStation.getStationCircle(), 
                metroStation.getStationLabel());
        
        for (MetroLine e: metroLines) {
            e.remove(metroStation);
            e.resetLine(data);
            data.getShapes().addAll(e.getLines());
        }
    }

    @Override
    public void undoTransaction() {
        data.getMetroStations().add(metroStation);
        data.getShapes().addAll(metroStation.getStationCircle(), 
                metroStation.getStationLabel());
        
        for (MetroLine e: metroLines) {
            e.addMetroStation(metroStation);
            e.resetLine(data);
            data.getShapes().addAll(e.getLines());
        }
    }
}

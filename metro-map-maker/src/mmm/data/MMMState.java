/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;

/**
 * This enum contains all the different states that the application may be in.
 * 
 * @author Steven Maio
 */
public enum MMMState {
    SELECTING_SHAPE,
    DRAGGING_SHAPE,
    ADD_STATIONS_MODE,
    REMOVE_STATIONS_MODE,
    METRO_STATION_SELECTED,
    METRO_LINE_SELECTED,
    CREATING_METRO_STATION,
    CREATING_METRO_LINE_START_POINT,
    CREATING_METRO_LINE_END_POINT,
    SELECTED_METRO_STATION,
    SELECTED_METRO_LINE,
}
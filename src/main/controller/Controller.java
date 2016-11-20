package main.controller;

import main.interfaces.Observer;
import main.model.Broker;
import main.view.Drawing;
import main.interfaces.Unloadable;
import main.interfaces.Unloader;

import java.util.ArrayList;

public class Controller implements Observer{
    private Broker broker;
    private Drawing drawer;
    ArrayList<Unloadable> unloadableArrived;
    ArrayList<Unloader> unloadersList;


    public Controller (Broker broker, Drawing drawer){
        this.broker = broker;
        this.drawer = drawer;
        this.broker.setObserverController(this);
        this.broker.notifyObservers();
    }

    public void starSimulation(){
        drawer.startDrawing();
    }

    private void updateDataFromBroker(){
        unloadableArrived = broker.getUnloadableArrived();
        unloadersList = broker.getUnloadersList();
    }

    @Override
    public void currentDayChanged(int day) {
        updateDataFromBroker();

    }
}

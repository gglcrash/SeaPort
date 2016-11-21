package com.netcracker.seaport.controller;

import com.netcracker.seaport.Unloadable;
import com.netcracker.seaport.Unloader;
import com.netcracker.seaport.Observer;
import com.netcracker.seaport.logger.UnloadableLog;
import com.netcracker.seaport.model.Broker;
import com.netcracker.seaport.view.Drawing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Controller implements Observer{
    private Broker broker;
    private Drawing drawer;
    private ArrayList<Unloadable> unloadableArrived;
    private ArrayList<Unloader> unloadersList;
    private Set<Unloadable> unloadableAtUnloaders;
    private Collection<UnloadableLog> logs;


    public Controller (Broker broker, Drawing drawer){
        this.broker = broker;
        this.drawer = drawer;
        this.broker.setObserverController(this);
        this.broker.notifyObservers();
    }

    public void starSimulation(){
        drawer.draw(unloadableArrived,unloadersList,unloadableAtUnloaders);
    }

    private void updateDataFromBroker(){
        unloadableArrived = broker.getUnloadableArrived();
        unloadersList = broker.getUnloadersList();
        unloadableAtUnloaders = broker.getUnloadablesAtUnloaders();
    }

    @Override
    public void currentDayChanged(int day) {
        updateDataFromBroker();
        drawer.setDay(day);
        drawer.draw(unloadableArrived,unloadersList,unloadableAtUnloaders);
    }

    public void addUnloadable(Unloadable unloadable, int day){
        broker.addUnloadableInList(unloadable,day);
    }

    public void pause(){
        broker.pause();
    }

    public void resume(){
        broker.resume();
    }

    private void getLogs(){
        logs = broker.getUnloadableLogs();

    }
}

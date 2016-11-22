package com.netcracker.seaport.controller;

import com.netcracker.seaport.Observer;
import com.netcracker.seaport.Unloadable;
import com.netcracker.seaport.Unloader;
import com.netcracker.seaport.logger.UnloadableLog;
import com.netcracker.seaport.model.Broker;
import com.netcracker.seaport.view.Drawing;
import com.netcracker.seaport.view.UserInteraction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Controller implements Observer {
    private Broker broker;
    private Drawing drawer;
    private UserInteraction ui;
    private ArrayList<Unloadable> unloadableArrived;
    private ArrayList<Unloader> unloadersList;
    private Set<Unloadable> unloadableAtUnloaders;
    private Collection<UnloadableLog> logs;

    public Controller (UserInteraction ui) {
        this.ui = ui;
        ui.setController(this);
    }

    public Controller (Broker broker, Drawing drawer) {
        setBroker(broker);
        setDrawer(drawer);
    }

    public Controller setBroker (Broker broker) {
        this.broker = broker;
        this.broker.setObserverController(this);
        this.broker.notifyObservers();
        return this;
    }

    public Controller setDrawer (Drawing drawer) {
        this.drawer = drawer;
        return this;
    }

    public void starSimulation () {
        drawer.draw(unloadableArrived, unloadersList, unloadableAtUnloaders);
    }

    @Override
    public void currentDayChanged (int day) {
        updateDataFromBroker();
        drawer.setDay(day);
        drawer.draw(unloadableArrived, unloadersList, unloadableAtUnloaders);
    }

    private void updateDataFromBroker () {
        unloadableArrived = broker.getUnloadableArrived();
        unloadersList = broker.getUnloadersList();
        unloadableAtUnloaders = broker.getUnloadablesAtUnloaders();
    }

    public void addUnloadable (Unloadable unloadable, int day) {
        broker.addUnloadableInList(unloadable, day);
    }

    public void pauseSimulation () {
        broker.pause();
    }

    public void resume () {
        broker.resume();
    }

    private void getLogs () {
        logs = broker.getUnloadableLogs();

    }

    //TODO: Реализовать
    //Первый элемент в settings: "ship", "ships=число", "fine=число" или
    // "cranes". Соответствует типу возможных настроек
    private Controller tuneSimulation (String[] settings) {
        return null;
    }

    //TODO: Реализовать
    private Controller stopSimulation () {
        return null;
    }
}

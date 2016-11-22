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
    private boolean isActive;
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
        isActive = false;
    }

    public Controller setBroker (Broker broker) {
        this.broker = broker;
        this.broker.setObserverController(this);
        return this;
    }

    public Controller setDrawer (Drawing drawer) {
        this.drawer = drawer;
        return this;
    }

    public void starSimulation () {
        if (!isActive) {
            broker.start();
            isActive = true;
        }
    }

    @Override
    public void currentDayChanged (int day) {
        updateDataFromBroker();
        drawer.setDay(day);
        drawer.draw(unloadableArrived, unloadersList, unloadableAtUnloaders);
        if (day == 31) {
            drawer.drawStatistics(broker.getUnloadedCount(),
                broker.getFineSum(), broker.getAverageDelay());
        }
    }

    public void addUnloadable (Unloadable unloadable, int day) {
        broker.addUnloadableInList(unloadable, day);
    }

    public void addUnloader (Unloader unloader) {
        broker.addUnloaderInList(unloader);
    }

    public void pauseSimulation () {
        broker.pause();
    }

    private void updateDataFromBroker () {
        unloadableArrived = broker.getUnloadableArrived();
        unloadersList = broker.getUnloadersList();
        unloadableAtUnloaders = broker.getUnloadablesAtUnloaders();
    }

    public void pause () {
        broker.pause();
    }

    public void resume () {
        broker.start();
    }

    private void getLogs () {
        logs = broker.getUnloadableLogs();
    }

    //Первый элемент в settings: "ship", "ships=число", "fine=число" или
    // "cranes". Соответствует типу возможных настроек
    public Controller tuneSimulation (String[] settings) {
        String det = getDetermineSubstring(settings[0]);

        switch (det) {
            case "ship.":
                addUnloadable(Parser.getUnloadable(settings), 10);
                break;
            case "ships":
                Parser.getShipList(settings).forEach(
                    i -> addUnloadable(i, 10));
                break;
            case "fine=":
                //ToDo: Нужно этот штраф как-то устанавливать.
                Parser.getFine(settings);
                break;
            case "crane":
                Parser.getCraneList(settings).forEach(this::addUnloader);
                break;
        }

        return this;
    }

    private String getDetermineSubstring (String s) {
        // "ship" короче пяти, а различать с "ships" как-то надо...
        return (s + "..").substring(0, 5);
    }

    //TODO: Реализовать
    public Controller stopSimulation () {
        return null;
    }

}

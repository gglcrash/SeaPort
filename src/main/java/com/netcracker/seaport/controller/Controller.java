package com.netcracker.seaport.controller;

import com.netcracker.seaport.Observer;
import com.netcracker.seaport.Unloadable;
import com.netcracker.seaport.Unloader;
import com.netcracker.seaport.logger.UnloadableLog;
import com.netcracker.seaport.model.Broker;
import com.netcracker.seaport.model.crane.ContainerCrane;
import com.netcracker.seaport.model.crane.DryCargoCrane;
import com.netcracker.seaport.model.crane.TankerCrane;
import com.netcracker.seaport.model.ship.ContainerShip;
import com.netcracker.seaport.model.ship.DryCargoShip;
import com.netcracker.seaport.model.ship.TankerShip;
import com.netcracker.seaport.view.Drawer;
import com.netcracker.seaport.view.UserInteraction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Controller implements Observer {
    private boolean isActive;
    private Broker broker;
    private Drawer drawer;
    private UserInteraction ui;
    private ArrayList<Unloadable> unloadableArrived;
    private ArrayList<Unloader> unloadersList;
    private Set<Unloadable> unloadableAtUnloaders;
    private Collection<UnloadableLog> logs;
    private boolean isDefault = true;

    public Controller () {
        this(new Broker(), new Drawer());
    }

    public Controller (Broker broker, Drawer drawer) {
        this(broker, drawer, new UserInteraction(drawer));
    }

    public Controller (Broker broker, Drawer drawer, UserInteraction ui) {
        setBroker(broker);
        setDrawer(drawer);
        setUi(ui);
        isActive = false;
    }

    public Controller setBroker (Broker broker) {
        this.broker = broker;
        this.broker.setObserverController(this);
        return this;
    }

    public Controller setDrawer (Drawer drawer) {
        this.drawer = drawer;
        return this;
    }

    private Controller setUi (UserInteraction ui) {
        this.ui = ui;
        ui.setController(this);
        return this;
    }

    public Controller (UserInteraction ui) {
        this(new Broker(), ui.getDrawer(), ui);
    }

    private void addUnloader (Unloader unloader) {
        broker.addUnloaderInList(unloader);
    }

    public Controller begin () {
        ui.greetUser();
        ui.start();

        return this;
    }

    public void startSimulation () {
        if (isDefault) {
            setDefaultSettings();
        }
        if (!isActive) {
            broker.start();
            isActive = true;
        }
    }

    private Controller setDefaultSettings () {
        broker.addUnloadableInList(new ContainerShip("nova", 50), 1);
        broker.addUnloadableInList(new ContainerShip("hulio", 40), 2);
        broker.addUnloadableInList(new DryCargoShip("average", 70), 3);
        broker.addUnloadableInList(new TankerShip("smvetlo", 60), 4);
        broker.addUnloadableInList(new TankerShip("korob", 55), 5);
        broker.addUnloadableInList(new ContainerShip("hren", 59), 6);
        broker.addUnloadableInList(new ContainerShip("rediska", 80), 7);
        broker.addUnloadableInList(new DryCargoShip("korabl", 43), 8);
        broker.addUnloadableInList(new DryCargoShip("kniga", 73), 8);
        broker.addUnloadableInList(new DryCargoShip("polet", 65), 10);
        broker.addUnloadableInList(new TankerShip("dratuti", 90), 1);
        broker.addUnloadableInList(new ContainerShip("plavatel", 50), 3);
        broker.addUnloadableInList(new ContainerShip("spasatel", 40), 4);
        broker.addUnloadableInList(new DryCargoShip("lustra", 30), 5);
        broker.addUnloadableInList(new TankerShip("sobaka", 90), 6);
        broker.addUnloadableInList(new TankerShip("koshka", 35), 7);

        broker.addUnloaderInList(new ContainerCrane());
        broker.addUnloaderInList(new DryCargoCrane());
        broker.addUnloaderInList(new TankerCrane());

        return this;
    }

    @Override
    public void currentDayChanged (int day) {
        updateDataFromBroker();
        drawer.setDay(day);
        drawer.setToDraw(unloadableArrived, unloadersList,
            unloadableAtUnloaders);
        if (day == 31) {
            drawer.drawStatistics(broker.getUnloadedCount(),
                broker.getFineSum(), broker.getAverageDelay());
        }
    }

    private void updateDataFromBroker () {
        unloadableArrived = broker.getUnloadableArrived();
        unloadersList = broker.getUnloadersList();
        unloadableAtUnloaders = broker.getUnloadablesAtUnloaders();
    }

    public void resume () {
        broker.start();
        isActive = true;
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
                addUnloadable(Parser.getShip(settings), 10);
                break;
            case "ships":
                Parser.getShipList(settings).forEach(i -> addUnloadable(i, 10));
                break;
            case "fine=":
                broker.setFineSum(Parser.getFine(settings));
                break;
            case "crane":
                Parser.getCraneList(settings).forEach(this::addUnloader);
                break;
        }

        isDefault = false;
        return this;
    }

    private String getDetermineSubstring (String s) {
        // "ship" короче пяти, а различать с "ships" как-то надо...
        return (s + "..").substring(0, 5);
    }

    private void addUnloadable (Unloadable unloadable, int day) {
        broker.addUnloadableInList(unloadable, day);
    }

    public Controller stopSimulation () {
        pauseSimulation();
        broker = new Broker();
        isDefault = true;

        return this;
    }

    public void pauseSimulation () {
        broker.pause();
        isActive = false;
    }

}

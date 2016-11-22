package com.netcracker.seaport.model;

import com.netcracker.seaport.Observable;
import com.netcracker.seaport.Observer;
import com.netcracker.seaport.Unloadable;
import com.netcracker.seaport.Unloader;
import com.netcracker.seaport.logger.UnloadableLog;

import java.util.*;

public class Broker implements Observable {
    private Map<Unloadable, Integer> unloadableInSchedule = new HashMap<>();
    private ArrayList<Unloadable> unloadableArrived = new ArrayList<>();
    private Map<Unloadable, Unloader> unloadablesAtUnloaders = new HashMap<>();
    private ArrayList<Unloader> unloadersList = new ArrayList<>();
    private ArrayList<Observer> observersList = new ArrayList<>();
    private Map<Unloadable, UnloadableLog> logsOfUnloadables = new HashMap<>();
    private ArrayList<Integer> delayList = new ArrayList<>();
    private Observer controllerObserver;
    private Timer timer;
    private int fineSum;
    private int finishedUnloadCount = 0;
    private int currentDay;
    private int totalArrivedCount = 0;
    private boolean isActive;

    public Broker(Map<Unloadable, Integer> unloadableMap, ArrayList<Unloader> unloaderList) {
        unloadableInSchedule = unloadableMap;
        unloadersList = unloaderList;
        setObserversList();
        setCranesCoordinates(unloaderList);
        isActive = false;
    }

    //region Lists
    public ArrayList<Unloadable> getUnloadableArrived() {
        return unloadableArrived;
    }

    public ArrayList<Unloader> getUnloadersList() {
        return unloadersList;
    }

    public Set<Unloadable> getUnloadablesAtUnloaders() {
        return unloadablesAtUnloaders.keySet();
    }

    public Collection<UnloadableLog> getUnloadableLogs() {
        return logsOfUnloadables.values();
    }

    public void addUnloadableInList(Unloadable newUnloadable, int expectedDay) {
        unloadableInSchedule.put(newUnloadable, expectedDay);
        observersList.add(newUnloadable);
    }

    public Broker addUnloaderInList (Unloader unloader) {
        unloadersList.add(unloader);
        return this;
    }

    public int getUnloadedCount(){
        return finishedUnloadCount;
    }

    public int getFineSum(){
        return fineSum;
    }

    public int getAverageDelay(){
        int sumDelay = 0;
        for (int delay : delayList){
            sumDelay+=delay;
        }
        return sumDelay/delayList.size();
    }

    public boolean getIsActive(){
        return isActive;
    }
    //endregion

    //region Timer Logic
    public void pause() {
        timer.cancel();
        isActive = false;
    }

    public void start() {
        isActive = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextDay();
            }
        }, 0, 1000);
    }

    private void nextDay() {

        currentDay++;
        checkArrivingUnloadables();
        checkEndOfUnloading();
        concatUnloadableWithUnloader();

        notifyObservers();

        if (currentDay == 31) {
            isActive=false;
            pause();
        }
    }
    //endregion

    //region Work with Unloadable/Unloader
    private void checkArrivingUnloadables() {
        List<Unloadable> synchronizedSceduleList =
                Collections.synchronizedList(new ArrayList<>(unloadableInSchedule.keySet()));


        for (Unloadable unloadable : synchronizedSceduleList) {
            if (unloadableInSchedule.get(unloadable) == currentDay) {

                logUnloadableArrived(unloadable);

                unloadable.setIsArrived(true);
                unloadableArrived.add(unloadable);
                unloadableInSchedule.remove(unloadable);

                totalArrivedCount++;
                calculateCoordinatesForUnloadable(unloadable);
            }
        }
    }

    private void concatUnloadableWithUnloader() {
        List<Unloadable> synchronizedArrivedList =
                Collections.synchronizedList(new ArrayList<>(unloadableArrived));
        List<Unloader> synchronizedUnloadersList =
                Collections.synchronizedList(new ArrayList<>(unloadersList));

        for (Unloader unloader : synchronizedUnloadersList) {
            if (unloader.getAvailability()) {
                for (Unloadable unloadable : synchronizedArrivedList) {

                    if (unloadable.getType() == unloader.getType()) {
                        logUnloadableToUnloader(unloadable);

                        int randomDelay = calculateDelayForUnloadable(unloadable);
                        int daysForUnload = calculateDaysForUnload(unloadable, unloader) + randomDelay;
                        //unloader.startUnloading(daysForUnload + currentDay);
                        unloader.setUnloadable(unloadable);

                        unloadable.setX(unloader.getX());
                        unloadable.setY(unloader.getY() - 1);
                        unloadablesAtUnloaders.put(unloadable, unloader);
                        unloadableArrived.remove(unloadable);

                        delayList.add(getUnloadDelay(unloadable));
                        break;
                    }
                }
            }
        }
    }

    private void checkEndOfUnloading() {
        List<Unloadable> synchronizedUnloadableAtUnloaderList =
                Collections.synchronizedList(new ArrayList<>(unloadablesAtUnloaders.keySet()));
        for (Unloadable unloadable : synchronizedUnloadableAtUnloaderList) {
            if (unloadablesAtUnloaders.get(unloadable).getAvailability()) {
                logUnloadableDeparture(unloadable);
                unloadablesAtUnloaders.remove(unloadable);
                observersList.remove(unloadable);
                finishedUnloadCount++;
            }
        }
    }
    //endregion

    //region Calculations
    private int getUnloadDelay(Unloadable unloadable){
        UnloadableLog log = logsOfUnloadables.get(unloadable);
        int delay =  log.getUnloadStart()-log.getArrival();
        fineSum += calculateFine(unloadable.getType(), delay);
        return delay;
    }

    private void setCranesCoordinates(ArrayList<Unloader> unloaders) {
        for (int i = 0; i < unloaders.size(); i++) {
            unloaders.get(i).setY(20);
            unloaders.get(i).setX(10 + 20 * i);
        }
    }

    private void calculateCoordinatesForUnloadable(Unloadable unloadable) {
        int row = totalArrivedCount / 4;
        int column = totalArrivedCount % 4;
        unloadable.setX(40 - 10 * column);
        unloadable.setY(4 + row);
    }

    private int calculateDaysForUnload(Unloadable unloadable, Unloader unloader) {
        int weight = unloadable.getWeight();
        int complexity = unloader.getPerfomance();
        int variable = 1;
        if (weight > 45) {
            variable = 2;
            if (weight > 75) {
                variable = 3;
            }
        }
        return variable * complexity;
    }

    private int calculateDelayForUnloadable(Unloadable unloadable) {
        Random rand = new Random();
        return rand.nextInt(3);
    }

    private int calculateFine(CargoType type, int delay) {
        int value = 0;
        switch (type){
            case DRYCARGO:
                value = 2000;
                break;
            case TANKER:
                value = 1000;
                break;
            case CONTAINER:
                value = 1500;
                break;
        }
        return value*delay;
    }
    //endregion

    //region Logs
    private void logUnloadableArrived(Unloadable unloadable) {
        UnloadableLog log = new UnloadableLog();
        log.setName(unloadable.getName());
        log.setArrival(currentDay);
        logsOfUnloadables.put(unloadable, log);
    }

    private void logUnloadableToUnloader(Unloadable unloadable) {
        UnloadableLog log = logsOfUnloadables.get(unloadable);
        log.setWaiting(currentDay - log.getArrival());
        log.setUnloadStart(currentDay);
    }

    private void logUnloadableDeparture(Unloadable unloadable) {
        UnloadableLog log = logsOfUnloadables.get(unloadable);
        log.setUnloadDuration(currentDay - log.getUnloadStart());
        log.setDeparture(currentDay);
    }
    //endregion

    //region Observers
    private void setObserversList() {
        observersList.addAll(unloadableInSchedule.keySet());
        observersList.addAll(unloadersList);
    }

    public void setObserverController(Observer cont) {
        controllerObserver = cont;
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : observersList) {
            obs.currentDayChanged(currentDay);
        }
        if (controllerObserver != null) {
            controllerObserver.currentDayChanged(currentDay);
        }
    }
    //endregion
}

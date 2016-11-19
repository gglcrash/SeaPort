package main.model;

import com.sun.istack.internal.Nullable;
import main.interfaces.Observable;
import main.interfaces.Observer;
import main.interfaces.Unloadable;
import main.interfaces.Unloader;

import java.util.*;

public class Broker implements Observable{
    Map<Unloadable,Integer> unloadableInSchedule = new HashMap<>();
    ArrayList<Unloadable> unloadableArrived = new ArrayList<>();
    ArrayList<Unloader> unloadersList = new ArrayList<>();
    ArrayList<Observer> observersList = new ArrayList<>();
    Observer controllerObserver;
    private int fineSum;
    private int finishedUnloadCount;
    private int currentDay;

    public Broker(Map<Unloadable, Integer> unloadableList, ArrayList<Unloader> unloaderList){
        unloadableInSchedule = unloadableList;
        unloadersList = unloaderList;
        setObserversList();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextDay();
            }
        }, 0,1000);
    }

    private void setObserversList(){
        observersList.addAll(unloadableInSchedule.keySet());
        observersList.addAll(unloadersList);

    }

    public void setObserverController(Observer cont){
        controllerObserver = cont;
    }

    public ArrayList<Unloadable> getUnloadableArrived(){
        return unloadableArrived;
    }

    public ArrayList<Unloader> getUnloadersList(){
        return unloadersList;
    }

    public void startSimulation(){

    }

    public void addUnloadableInList(Unloadable newUnloadable){

    }

    private void checkUnloadersAvailability(){

    }

    private void startUnloading(){

    }

    private int calculateDelayForUnloadable(Unloadable unloadable){
        return 0;
    }

    private int calculateAverageDelay(){
        return 0;
    }

    private int calculateFine(Unloadable unloadable){
        return 0;
    }

    private void nextDay(){
        currentDay++;
        List<Unloadable> synchronizedSceduleList =
                Collections.synchronizedList(new ArrayList<>(unloadableInSchedule.keySet()));

        for(Unloadable unload : synchronizedSceduleList){
            if(unloadableInSchedule.get(unload)==currentDay){
                unloadableArrived.add(unload);
                unloadableInSchedule.remove(unload);
            }
        }
        if(currentDay==6){
            int x =0;
        }
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : observersList){
            obs.currentDayChanged(currentDay);
        }
        if(controllerObserver!=null) {
            controllerObserver.currentDayChanged(currentDay);
        }
    }
}

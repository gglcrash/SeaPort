package main.model;

import main.interfaces.Observable;
import main.interfaces.Observer;
import main.interfaces.Unloadable;
import main.interfaces.Unloader;

import java.util.*;

public class Broker implements Observable{
    private Map<Unloadable,Integer> unloadableInSchedule = new HashMap<>();
    private ArrayList<Unloadable> unloadableArrived = new ArrayList<>();
    private Map<Unloadable,Unloader> unloadablesAtUnloaders = new HashMap<>();
    private ArrayList<Unloader> unloadersList = new ArrayList<>();
    private ArrayList<Observer> observersList = new ArrayList<>();
    private Observer controllerObserver;
    private int fineSum;
    private int finishedUnloadCount;
    private int currentDay;

    public Broker(Map<Unloadable, Integer> unloadableMap, ArrayList<Unloader> unloaderList){
        unloadableInSchedule = unloadableMap;
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

    public void addUnloadableInList(Unloadable newUnloadable,int expectedDay){
        unloadableInSchedule.put(newUnloadable,expectedDay);
        observersList.add(newUnloadable);
    }

    private void checkUnloadersAvailability(){

    }

    private int calculateDaysForUnload(Unloadable unloadable, Unloader unloader){
        int weight = unloadable.getWeight();
        int complexity = unloader.getComplexity();
        //бла бла бла
        return 2;
    }

    private int calculateDelayForUnloadable(Unloadable unloadable){
        return 0;
    }

    private int calculateAverageDelay(){
        return 0;
    }

    private int calculateFine(CargoType type, int delay){
        return 0;
    }

    private void nextDay(){
        currentDay++;
        checkArrivingUnloadables();
        checkEndOfUnloading();
        concatUnloadableWithUnloader();

        notifyObservers();

    }

    private void checkArrivingUnloadables(){
        List<Unloadable> synchronizedSceduleList =
                Collections.synchronizedList(new ArrayList<>(unloadableInSchedule.keySet()));

        for(Unloadable unload : synchronizedSceduleList){
            if(unloadableInSchedule.get(unload)==currentDay){
                unload.setIsArrived(true);
                unloadableArrived.add(unload);
                unloadableInSchedule.remove(unload);
            }
        }
    }

    private void concatUnloadableWithUnloader(){
        List<Unloadable> synchronizedArrivedList =
                Collections.synchronizedList(new ArrayList<>(unloadableArrived));
        List<Unloader> synchronizedUnloadersList =
                Collections.synchronizedList(new ArrayList<>(unloadersList));

        for(Unloader unloader : synchronizedUnloadersList){
            if(unloader.getAvailability()){
                for(Unloadable unloadable:synchronizedArrivedList){
                    if(unloadable.getType()==unloader.getType()){
                        int delay = calculateDelayForUnloadable(unloadable);
                        fineSum += calculateFine(unloadable.getType(),delay);
                        int daysForUnload = calculateDaysForUnload(unloadable,unloader)+ delay;
                        unloader.startUnloading(daysForUnload+currentDay);
                        unloadablesAtUnloaders.put(unloadable,unloader);
                        unloadableArrived.remove(unloadable);
                    }
                }
            }
        }

    }

    private void checkEndOfUnloading(){
        List<Unloadable> synchronizedUnloadableAtUnloaderList =
                Collections.synchronizedList(new ArrayList<>(unloadablesAtUnloaders.keySet()));
        for(Unloadable unloadable : synchronizedUnloadableAtUnloaderList){
            if (unloadablesAtUnloaders.get(unloadable).getAvailability()){
                setLogs();
                unloadablesAtUnloaders.remove(unloadable);
                observersList.remove(unloadable);
            }
        }
    }

    private void setLogs(){

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

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
    private Timer timer;
    private int fineSum;
    private int finishedUnloadCount=0;
    private int currentDay;
    private int totalArrivedCount = 0;

    public Broker(Map<Unloadable, Integer> unloadableMap, ArrayList<Unloader> unloaderList){
        unloadableInSchedule = unloadableMap;
        unloadersList = unloaderList;
        setObserversList();
        setCranesCoordinates(unloaderList);

        timer = new Timer();
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

    private void setCranesCoordinates(ArrayList<Unloader> unloaders){
        for(int i = 0;i<unloaders.size();i++){
            unloaders.get(i).setY(20);
            unloaders.get(i).setX(10+20*i);
        }

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

    public Set<Unloadable> getUnloadablesAtUnloaders(){
        return unloadablesAtUnloaders.keySet();
    }

    public void addUnloadableInList(Unloadable newUnloadable,int expectedDay){
        unloadableInSchedule.put(newUnloadable,expectedDay);
        observersList.add(newUnloadable);
    }

    public void pause(){
        timer.cancel();
    }

    public void resume(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextDay();
            }
        }, 0,1000);
    }

    private int calculateDaysForUnload(Unloadable unloadable, Unloader unloader){
        int weight = unloadable.getWeight();
        int complexity = unloader.getComplexity();
        int variable = 1;
        if(weight>45){
            variable=2;
            if(weight>75){
                variable=3;
            }
        }
        return variable*complexity;
    }

    private int calculateDelayForUnloadable(Unloadable unloadable){
        Random rand = new Random();
        return rand.nextInt(3);
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

                totalArrivedCount++;
                calculateCoordinatesForUnloadable(unload);
            }
        }
    }

    private void calculateCoordinatesForUnloadable(Unloadable unloadable){
        int row = totalArrivedCount /4;
        int column = totalArrivedCount %4;
        unloadable.setX(40-10*column);
        unloadable.setY(4+row);
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
                        int daysForUnload = calculateDaysForUnload(unloadable,unloader)+ delay;
                        fineSum += calculateFine(unloadable.getType(),delay);
                        unloader.startUnloading(daysForUnload+currentDay);

                        unloadable.setX(unloader.getX());
                        unloadable.setY(unloader.getY()-1);
                        unloadablesAtUnloaders.put(unloadable,unloader);
                        unloadableArrived.remove(unloadable);
                        break;
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
                finishedUnloadCount++;
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
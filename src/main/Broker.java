package main;

import java.util.ArrayList;

public class Broker {
    ArrayList<Unloadable> unloadableInSchedule;
    ArrayList<Unloadable> unloadableArrived;
    ArrayList<Unloader> unloadersList;
    private int fineSum;
    private int finishedUnloadCount;

    public Broker(ArrayList<Unloadable> unloadableList, ArrayList<Unloader> unloaderList){

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
}

package com.netcracker.seaport.model.crane;

import com.netcracker.seaport.Unloadable;
import com.netcracker.seaport.Unloader;
import com.netcracker.seaport.model.CargoType;

class Crane implements Unloader {
    private CargoType type;
    private int performance;
    private boolean isAvailable;
    private int endUnloadingDay;
    private int x, y;
    private Unloadable unloadable;

    protected Crane (int performance, CargoType type) {
        this.type = type;
        this.performance = performance;
        setAvailability(true);
        setX(0);
        setY(3);
    }

    @Override
    public void setUnloadable(Unloadable unloadable){
        this.unloadable = unloadable;
        setAvailability(false);
        unloadPerDay();
    }

    private void unloadPerDay(){
        int tmp = unloadable.getWeight();
        int val = tmp - performance;
        unloadable.setWeight(val);
    }

    @Override
    public CargoType getType() {
        return type;
    }

    public int getPerformance () {
        return performance;
    }

    @Override
    public void setAvailability(boolean availability) {
        this.isAvailable = availability;
    }

    @Override
    public boolean isAvailable () {
        return isAvailable;
    }

    @Override
    public void currentDayChanged(int day) {
       /* if (endUnloadingDay == day) {
            setAvailability(true);
        }*/
       if(unloadable!=null) {
           unloadPerDay();

           if (unloadable.getWeight() == 0) {
               setAvailability(true);
               unloadable = null;
           }
       }
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getY() {
        return y;
    }
}

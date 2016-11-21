package main.com.netcracker.seaport.java.model.crane;

import main.com.netcracker.seaport.java.model.CargoType;
import main.com.netcracker.seaport.java.Unloader;

class Crane implements Unloader{
    private CargoType type;
    private int complexity;
    private boolean isAvailable;
    private int endUnloadingDay;
    private int x,y;

    // логика создания рандомного крана
    protected  Crane(){

    }

    protected Crane(int complexity, CargoType type){
        this.type = type;
        this.complexity = complexity;
        setAvailability(true);
        setX(0);
        setY(0);
    }

    @Override
    public CargoType getType() {
        return type;
    }

    @Override
    public int getComplexity() {
        return complexity;
    }

    @Override
    public void setAvailability(boolean availability) {
        this.isAvailable = availability;
    }

    @Override
    public boolean getAvailability() {
        return isAvailable;
    }

    @Override
    public void startUnloading(int day){
        setAvailability(false);
        endUnloadingDay = day;
    }

    @Override
    public void currentDayChanged(int day) {
        if(endUnloadingDay == day){
            setAvailability(true);
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

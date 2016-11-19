package crane;

import main.interfaces.Unloadable;
import main.model.CargoType;
import main.interfaces.Unloader;

class Crane implements Unloader {
    private CargoType type;
    private int complexity;
    private boolean isAvailable;

    private Unloadable currentShip;

    // логика создания рандомного крана
    protected  Crane(){

    }

    protected Crane(int complexity, CargoType type){
        this.type = type;
        this.complexity = complexity;
        setAvailability(true);
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
    public void setUnloadable(Unloadable ship) {
        this.currentShip = ship;
    }

    @Override
    public Unloadable getUnloadable() {
        return currentShip;
    }

    @Override
    public void currentDayChanged(int day) {

    }
}

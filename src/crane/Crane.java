package crane;

import main.model.CargoType;
import main.interfaces.Unloader;

class Crane implements Unloader {
    private CargoType type;
    private int complexity;
    private boolean isAvailable;
    private int endUnloadingDay;

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
    public void startUnloading(int day){
        endUnloadingDay = day;
    }

    @Override
    public void currentDayChanged(int day) {
        if(endUnloadingDay == day){
            setAvailability(true);
        }
    }
}

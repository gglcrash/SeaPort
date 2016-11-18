package crane;

import main.CargoType;
import main.Unloader;

class Crane implements Unloader {
    private CargoType type;
    private int complexity;
    private boolean isAvailable;

    // логика создания рандомного крана
    protected  Crane(){

    }

    protected Crane(int complexity, CargoType type){
        this.type = type;
        this.complexity = complexity;
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
}

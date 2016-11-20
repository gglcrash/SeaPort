package main.interfaces;

import main.model.CargoType;

public interface Unloader extends Observer{
    CargoType getType();
    int getComplexity();
    void setAvailability(boolean availability);
    boolean getAvailability();
    void startUnloading(int days);
}

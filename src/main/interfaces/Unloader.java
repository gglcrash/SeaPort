package main.interfaces;

import main.model.CargoType;

public interface Unloader extends Observer{
    CargoType getType();
    int getComplexity();
    void setAvailability(boolean availability);
    boolean getAvailability();
    void setUnloadable(Unloadable unloadable);
    Unloadable getUnloadable();
}

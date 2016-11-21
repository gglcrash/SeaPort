package main.com.netcracker.seaport.java;

import main.com.netcracker.seaport.java.model.CargoType;

public interface Unloader extends Drawable{
    CargoType getType();
    int getComplexity();
    void setAvailability(boolean availability);
    boolean getAvailability();
    void startUnloading(int days);
}

package com.netcracker.seaport;

import com.netcracker.seaport.model.CargoType;

public interface Unloader extends Drawable{
    CargoType getType();
    int getComplexity();
    void setAvailability(boolean availability);
    boolean getAvailability();
    void startUnloading(int days);
}

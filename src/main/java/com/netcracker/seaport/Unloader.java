package com.netcracker.seaport;

import com.netcracker.seaport.model.CargoType;

public interface Unloader extends Drawable {

    void setUnloadable(Unloadable unloadable);

    CargoType getType();

    int getPerformance ();

    void setAvailability(boolean availability);

    boolean isAvailable ();

}

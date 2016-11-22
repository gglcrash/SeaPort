package com.netcracker.seaport.model.crane;

import com.netcracker.seaport.model.CargoType;

public class DryCargoCrane extends Crane {
    public DryCargoCrane() {
        super(3, CargoType.DRYCARGO);   //3 - complexity
    }
}

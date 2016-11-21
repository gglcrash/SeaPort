package main.com.netcracker.seaport.java.model.crane;

import main.com.netcracker.seaport.java.model.CargoType;

public class DryCargoCrane extends Crane{
    public DryCargoCrane(){
        super(3, CargoType.DRYCARGO);   //3 - complexity
    }
}

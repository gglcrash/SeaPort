package main.java.com.netcracker.seaport.model.crane;

import main.java.com.netcracker.seaport.model.CargoType;

public class TankerCrane extends Crane{
    public TankerCrane(){
        super(1, CargoType.TANKER);   //1 - complexity
    }
}
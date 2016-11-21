package main.java.com.netcracker.seaport.model.ship;

import main.java.com.netcracker.seaport.model.CargoType;

public class TankerShip extends Ship{
    public TankerShip(String name, int weight){
        super(name,weight, CargoType.TANKER);
    }
}

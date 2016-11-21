package main.com.netcracker.seaport.java.model.ship;

import main.com.netcracker.seaport.java.model.CargoType;

public class TankerShip extends Ship{
    public TankerShip(String name, int weight){
        super(name,weight, CargoType.TANKER);
    }
}

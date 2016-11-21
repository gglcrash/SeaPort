package main.com.netcracker.seaport.model.ship;

import main.com.netcracker.seaport.model.CargoType;

public class DryCargoShip extends Ship{
    public DryCargoShip(String name, int weight){
        super(name,weight, CargoType.DRYCARGO);
    }
}

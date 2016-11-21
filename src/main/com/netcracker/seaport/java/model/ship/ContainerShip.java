package main.com.netcracker.seaport.java.model.ship;

import main.com.netcracker.seaport.java.model.CargoType;

public class ContainerShip extends Ship{

    public ContainerShip(String name, int weight){
        super(name,weight, CargoType.CONTAINER);
    }
}

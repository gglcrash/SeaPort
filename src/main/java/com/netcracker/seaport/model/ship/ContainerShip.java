package main.java.com.netcracker.seaport.model.ship;

import main.java.com.netcracker.seaport.model.CargoType;

public class ContainerShip extends Ship{

    public ContainerShip(String name, int weight){
        super(name,weight, CargoType.CONTAINER);
    }
}

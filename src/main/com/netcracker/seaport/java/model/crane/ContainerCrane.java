package main.com.netcracker.seaport.java.model.crane;

import main.com.netcracker.seaport.java.model.CargoType;

public class ContainerCrane extends Crane {
    public ContainerCrane(){
        super(2, CargoType.CONTAINER);   //2 - complexity
    }
}

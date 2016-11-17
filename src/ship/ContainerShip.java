package ship;

import main.CargoType;

public class ContainerShip extends Ship{

    public ContainerShip(String name, int weight){
        super(name,weight, CargoType.CONTAINER);
    }
}

package ship;

import main.model.CargoType;

public class ContainerShip extends Ship{

    public ContainerShip(String name, int weight){
        super(name,weight, CargoType.CONTAINER);
    }
}

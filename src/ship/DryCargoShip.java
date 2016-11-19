package ship;

import main.model.CargoType;

public class DryCargoShip extends Ship{
    public DryCargoShip(String name, int weight){
        super(name,weight, CargoType.DRYCARGO);
    }
}

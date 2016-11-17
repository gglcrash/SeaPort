package ship;

import main.CargoType;

public class DryCargoShip extends Ship{
    public DryCargoShip(String name, int weight){
        super(name,weight, CargoType.DRYCARGO);
    }
}

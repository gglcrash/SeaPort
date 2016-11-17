package ship;

import main.CargoType;

public class TankerShip extends Ship{
    public TankerShip(String name, int weight){
        super(name,weight, CargoType.TANKER);
    }
}

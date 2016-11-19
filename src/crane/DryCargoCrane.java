package crane;

import main.model.CargoType;

public class DryCargoCrane extends Crane{
    public DryCargoCrane(){
        super(2, CargoType.DRYCARGO);   //2 - complexity
    }
}

package crane;

import main.model.CargoType;

public class TankerCrane extends Crane{
    public TankerCrane(){
        super(3, CargoType.TANKER);   //3 - complexity
    }
}

package crane;

import main.model.CargoType;

public class TankerCrane extends Crane{
    public TankerCrane(){
        super(1, CargoType.TANKER);   //1 - complexity
    }
}

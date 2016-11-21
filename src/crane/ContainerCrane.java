package crane;

import main.model.CargoType;

public class ContainerCrane extends Crane {
    public ContainerCrane(){
        super(2, CargoType.CONTAINER);   //2 - complexity
    }
}

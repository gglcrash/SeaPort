package crane;

import main.CargoType;

public class ContainerCrane extends Crane {
    public ContainerCrane(){
        super(1, CargoType.CONTAINER);   //1 - complexity
    }
}

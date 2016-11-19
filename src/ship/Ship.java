package ship;

import main.model.CargoType;
import main.interfaces.Unloadable;

class Ship implements Unloadable{
    private String name;
    private int weight;
    private CargoType type;

    //логика создания рандомного корабля
    public Ship(){

    }

    Ship(String name, int weight, CargoType type){
        this.name = name;
        this.weight = weight;
        this.type = type;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CargoType getType() {
        return type;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void currentDayChanged(int day) {

    }
}

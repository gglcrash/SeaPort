package main.com.netcracker.seaport.java;

import main.com.netcracker.seaport.java.model.CargoType;

public interface Unloadable extends Drawable{
    void setName(String name);
    String getName();
    CargoType getType();
    void setWeight(int weight);
    int getWeight();
    int getDaysAfterArrive();
    void setIsArrived(boolean arrived);
}

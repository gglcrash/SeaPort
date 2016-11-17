package main;

public interface Unloadable {
    void setName(String name);
    String getName();
    CargoType getType();
    void setWeight(int weight);
    int getWeight();
}

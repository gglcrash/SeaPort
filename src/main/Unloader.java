package main;

public interface Unloader {
    CargoType getType();
    int getComplexity();
    void setAvailability(boolean availability);
    boolean getAvailability();
}

package com.netcracker.seaport.model.ship;

import com.netcracker.seaport.model.CargoType;
import com.netcracker.seaport.Unloadable;

class Ship implements Unloadable {
    private String name;
    private int weight;
    private CargoType type;
    private int daysInPort;
    private boolean isInPort;
    private int x, y;


    //логика создания рандомного корабля
    public Ship() {

    }

    Ship(String name, int weight, CargoType type) {
        this.name = name;
        this.weight = weight;
        this.type = type;
        daysInPort = 0;
        isInPort = false;
        setX(0);
        setY(0);
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
        if (weight > 100) {
            this.weight = 100;
        } else if (weight < 0) {
            this.weight = 0;
        } else {
            this.weight = weight;
        }
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getDaysAfterArrive() {
        return daysInPort;
    }

    @Override
    public void setIsArrived(boolean arrived) {
        this.isInPort = arrived;
    }

    @Override
    public void currentDayChanged(int day) {
        if (isInPort) {
            daysInPort++;
        }
    }


    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getY() {
        return y;
    }

}

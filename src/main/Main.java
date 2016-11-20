package main;

import crane.ContainerCrane;
import crane.DryCargoCrane;
import crane.TankerCrane;
import main.controller.Controller;
import main.interfaces.Unloadable;
import main.interfaces.Unloader;
import main.model.Broker;
import main.view.Drawing;
import ship.ContainerShip;
import ship.DryCargoShip;
import ship.TankerShip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // где-то тут формируется результат пользовательского ввода и по этим данным создается брокер
        Map<Unloadable, Integer> shipsInSchedule = new HashMap<>();  // <разгружаемый,ожидаемый день прибытия>
        ArrayList<Unloader> cranesInPort = new ArrayList<>();

        shipsInSchedule.put(new ContainerShip("nova",50),1);
        shipsInSchedule.put(new ContainerShip("hulio",40),2);
        shipsInSchedule.put(new DryCargoShip("average",20),3);
        shipsInSchedule.put(new TankerShip("smvetlo",10),4);
        shipsInSchedule.put(new TankerShip("korob",15),5);
        shipsInSchedule.put(new ContainerShip("hren",50),6);
        shipsInSchedule.put(new ContainerShip("rediska",40),7);
        shipsInSchedule.put(new DryCargoShip("korabl",20),8);
        shipsInSchedule.put(new TankerShip("dratuti",10),1);
        shipsInSchedule.put(new TankerShip("privet",15),2);
        shipsInSchedule.put(new ContainerShip("plavatel",50),3);
        shipsInSchedule.put(new ContainerShip("spasatel",40),4);
        shipsInSchedule.put(new DryCargoShip("lustra",20),5);
        shipsInSchedule.put(new TankerShip("sobaka",10),6);
        shipsInSchedule.put(new TankerShip("koshka",15),7);

        cranesInPort.add(new ContainerCrane());
        cranesInPort.add(new DryCargoCrane());
        cranesInPort.add(new TankerCrane());

        Broker broker = new Broker(shipsInSchedule,cranesInPort);
        Drawing drawer = new Drawing();
        Controller myCont = new Controller(broker,drawer);
        myCont.starSimulation();

    }

}

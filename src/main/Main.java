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

        shipsInSchedule.put(new ContainerShip("nova",50),4);
        shipsInSchedule.put(new ContainerShip("nova2",40),1);
        shipsInSchedule.put(new DryCargoShip("average",20),5);
        shipsInSchedule.put(new TankerShip("neutron",10),10);
        shipsInSchedule.put(new TankerShip("neutron2",15),7);

        cranesInPort.add(new ContainerCrane());
        cranesInPort.add(new DryCargoCrane());
        cranesInPort.add(new TankerCrane());

        Broker broker = new Broker(shipsInSchedule,cranesInPort);
        Drawing drawer = new Drawing();
        Controller myCont = new Controller(broker,drawer);
        myCont.starSimulation();

    }

}

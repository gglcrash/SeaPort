package main.com.netcracker.seaport.java;

import main.com.netcracker.seaport.java.model.crane.ContainerCrane;
import main.com.netcracker.seaport.java.model.crane.DryCargoCrane;
import main.com.netcracker.seaport.java.model.crane.TankerCrane;
import main.com.netcracker.seaport.java.controller.Controller;
import main.com.netcracker.seaport.java.model.Broker;
import main.com.netcracker.seaport.java.view.Drawing;
import main.com.netcracker.seaport.java.model.ship.ContainerShip;
import main.com.netcracker.seaport.java.model.ship.DryCargoShip;
import main.com.netcracker.seaport.java.model.ship.TankerShip;

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
        shipsInSchedule.put(new DryCargoShip("average",70),3);
        shipsInSchedule.put(new TankerShip("smvetlo",60),4);
        shipsInSchedule.put(new TankerShip("korob",55),5);
        shipsInSchedule.put(new ContainerShip("hren",59),6);
        shipsInSchedule.put(new ContainerShip("rediska",80),7);
        shipsInSchedule.put(new DryCargoShip("korabl",23),8);
        shipsInSchedule.put(new TankerShip("dratuti",90),1);
        shipsInSchedule.put(new TankerShip("privet",60),2);
        shipsInSchedule.put(new ContainerShip("plavatel",50),3);
        shipsInSchedule.put(new ContainerShip("spasatel",40),4);
        shipsInSchedule.put(new DryCargoShip("lustra",30),5);
        shipsInSchedule.put(new TankerShip("sobaka",90),6);
        shipsInSchedule.put(new TankerShip("koshka",35),7);

        cranesInPort.add(new ContainerCrane());
        cranesInPort.add(new DryCargoCrane());
        cranesInPort.add(new TankerCrane());

        Broker broker = new Broker(shipsInSchedule,cranesInPort);
        Drawing drawer = new Drawing();
        Controller myCont = new Controller(broker,drawer);
        myCont.starSimulation();

    }

}

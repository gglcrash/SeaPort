package com.netcracker.seaport;

import com.netcracker.seaport.controller.Controller;
import com.netcracker.seaport.model.Broker;
import com.netcracker.seaport.model.crane.ContainerCrane;
import com.netcracker.seaport.model.crane.DryCargoCrane;
import com.netcracker.seaport.model.crane.TankerCrane;
import com.netcracker.seaport.model.ship.ContainerShip;
import com.netcracker.seaport.model.ship.DryCargoShip;
import com.netcracker.seaport.model.ship.TankerShip;
import com.netcracker.seaport.view.Drawing;
import com.netcracker.seaport.view.UserInteraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main (String[] args) {

        // где-то тут формируется результат пользовательского ввода и по этим
        // данным создается брокер
        Drawing drawer = new Drawing();
        UserInteraction userInteraction = new UserInteraction(drawer);
        Controller controller = new Controller(userInteraction);

        userInteraction.greetUser();
        userInteraction.run();

        Map<Unloadable, Integer> shipsInSchedule =
            new HashMap<>();  // <разгружаемый,ожидаемый день прибытия>
        ArrayList<Unloader> cranesInPort = new ArrayList<>();

        shipsInSchedule.put(new ContainerShip("nova", 50), 1);
        shipsInSchedule.put(new ContainerShip("hulio", 40), 2);
        shipsInSchedule.put(new DryCargoShip("average", 70), 3);
        shipsInSchedule.put(new TankerShip("smvetlo", 60), 4);
        shipsInSchedule.put(new TankerShip("korob", 55), 5);
        shipsInSchedule.put(new ContainerShip("hren", 59), 6);
        shipsInSchedule.put(new ContainerShip("rediska", 80), 7);
        shipsInSchedule.put(new DryCargoShip("korabl", 43), 8);
        shipsInSchedule.put(new DryCargoShip("kniga", 73), 8);
        shipsInSchedule.put(new DryCargoShip("polet", 65), 10);
        shipsInSchedule.put(new TankerShip("dratuti", 90), 1);
        shipsInSchedule.put(new ContainerShip("plavatel", 50), 3);
        shipsInSchedule.put(new ContainerShip("spasatel", 40), 4);
        shipsInSchedule.put(new DryCargoShip("lustra", 30), 5);
        shipsInSchedule.put(new TankerShip("sobaka", 90), 6);
        shipsInSchedule.put(new TankerShip("koshka", 35), 7);

        cranesInPort.add(new ContainerCrane());
        cranesInPort.add(new DryCargoCrane());
        cranesInPort.add(new TankerCrane());

        Broker broker = new Broker(shipsInSchedule, cranesInPort);
        //myCont.starSimulation();
        controller.setBroker(broker).setDrawer(drawer).starSimulation();

    }

}

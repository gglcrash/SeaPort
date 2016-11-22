package com.netcracker.seaport.controller;

import com.netcracker.seaport.Unloadable;
import com.netcracker.seaport.Unloader;
import com.netcracker.seaport.model.crane.ContainerCrane;
import com.netcracker.seaport.model.crane.DryCargoCrane;
import com.netcracker.seaport.model.crane.TankerCrane;
import com.netcracker.seaport.model.ship.ContainerShip;
import com.netcracker.seaport.model.ship.DryCargoShip;
import com.netcracker.seaport.model.ship.TankerShip;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static int unnamedShipsCount = 0;
    private static int defaultWeight = 30;

    public static Unloadable getUnloadable (String[] settings) {
        String name = settings[1].substring(5);
        int weight = Integer.parseInt(settings[3].substring(7));
        switch (settings[2]) {
            case "dry":
                return new DryCargoShip(name, weight);
            case "container":
                return new ContainerShip(name, weight);
            case "tanker":
                return new TankerShip(name, weight);
            default:
                return null;
        }
    }

    public static List<Unloadable> getShipList (String[] settings) {
        int number = Integer.parseInt(settings[0].substring(6));
        List<Unloadable> retValue = new ArrayList<>(number);
        for (int i = 0; i < number; ++i) {
            retValue.add(new ContainerShip("Annie-" + unnamedShipsCount++,
                defaultWeight));
        }

        return retValue;
    }

    public static int getFine (String[] settings) {
        return Integer.parseInt(settings[0].substring(5));
    }

    public static List<Unloader> getCraneList (String[] settings) {
        List<Unloader> retValue = new ArrayList<>();
        int[] craneNumbersByType = new int[3];
        for (int i = 0; i < craneNumbersByType.length; ++i) {
            craneNumbersByType[i] = Integer.parseInt(
                settings[i].substring(settings[i].indexOf('=') + 1));
        }

        for (int i = 0; i < craneNumbersByType[0]; ++i) {
            retValue.add(new ContainerCrane());
        }
        for (int i = 0; i < craneNumbersByType[1]; ++i) {
            retValue.add(new DryCargoCrane());
        }
        for (int i = 0; i < craneNumbersByType[2]; ++i) {
            retValue.add(new TankerCrane());
        }

        return retValue;
    }
}

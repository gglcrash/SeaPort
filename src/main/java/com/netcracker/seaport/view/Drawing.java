package com.netcracker.seaport.view;


import com.netcracker.seaport.Unloader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.netcracker.seaport.Unloadable;
import com.netcracker.seaport.Unloader;
import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Drawing {
    private static ConsoleSystemInterface csi;

    //TODO: Выбрать правильные координаты!
    private final Coordinates USER_TEXT_COORDINATES = new Coordinates();

    private int day = 0;

    public Drawing () {
        csi = getCsi();
    }

    static ConsoleSystemInterface getCsi () {
        if (csi == null) {
            csi = new WSwingConsoleInterface("Sea Port", true);
        }
        return csi;
    }

    public void setDay (int day) {
        this.day = day;
    }

    public void draw(ArrayList<Unloadable> unloadableArrived, ArrayList<Unloader> unloadersList,
                     Set<Unloadable> unloadableAtUnloaders) {

        csi.cls();
        csi.print(60, 11, "Current day: " + day, CSIColor.WHITE);
        csi.print(60, 12, "Ships in queue: " + unloadableArrived.size(), CSIColor.WHITE);
        csi.print(60, 8, "TANKER", CSIColor.GREEN);
        csi.print(60, 9, "CONTAINER", CSIColor.RED);
        csi.print(60, 10, "DRYCARGO", CSIColor.BLUE);

        drawUnloadables(unloadableArrived);
        drawUnloadables(unloadableAtUnloaders);

        for (Unloader unloader : unloadersList) {
            CSIColor color = CSIColor.WHITE;
            switch (unloader.getType()) {
                case CONTAINER: {
                    color = CSIColor.RED;
                    break;
                }
                case TANKER: {
                    color = CSIColor.GREEN;
                    break;
                }
                case DRYCARGO: {
                    color = CSIColor.BLUE;
                    break;
                }
            }
            csi.print(unloader.getX(), unloader.getY(), "CRANE", color);
        }
        csi.refresh();

    }

    private void drawUnloadables (Collection<Unloadable> unloadables) {
        for (Unloadable unloadable : unloadables) {

            CSIColor color = CSIColor.WHITE;
            switch (unloadable.getType()) {
                case CONTAINER: {
                    color = CSIColor.RED;
                    break;
                }
                case TANKER: {
                    color = CSIColor.GREEN;
                    break;
                }
                case DRYCARGO: {
                    color = CSIColor.BLUE;
                    break;
                }
            }

            csi.print(unloadable.getX(), unloadable.getY(), unloadable.getName(), color);
        }
    }

    public Drawing printForUser (String text) {
        csi.print(USER_TEXT_COORDINATES.x, USER_TEXT_COORDINATES.y, text);

        return this;
    }
}

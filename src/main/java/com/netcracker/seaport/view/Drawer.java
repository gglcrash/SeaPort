package com.netcracker.seaport.view;


import com.netcracker.seaport.Unloadable;
import com.netcracker.seaport.Unloader;
import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;

import java.util.*;

public class Drawer {
    //TODO: Выбрать правильные координаты!
    private final Coordinates USER_TEXT_COORDINATES = new Coordinates(1, 15);
    private static ConsoleSystemInterface csi;
    private int day = 0;
    private String textForUser = "";

    private ArrayList<Unloadable> unloadableArrived;
    private ArrayList<Unloader> unloadersList;
    private Set<Unloadable> unloadableAtUnloaders;

    public Drawer () {
        setToDraw(new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        csi = getCsi();
    }

    public Drawer setToDraw (ArrayList<Unloadable> unloadableArrived,
        ArrayList<Unloader> unloadersList,
        Set<Unloadable> unloadableAtUnloaders) {

        this.setUnloadableArrived(unloadableArrived);
        this.setUnloadersList(unloadersList);
        this.setUnloadableAtUnloaders(unloadableAtUnloaders);

        return this;
    }

    static ConsoleSystemInterface getCsi () {
        if (csi == null) {
            csi = new WSwingConsoleInterface("Sea Port", true);
        }
        return csi;
    }

    private Drawer setUnloadableArrived (
        ArrayList<Unloadable> unloadableArrived) {
        this.unloadableArrived = unloadableArrived;
        return this;
    }

    private Drawer setUnloadersList (ArrayList<Unloader> unloadersList) {
        this.unloadersList = unloadersList;
        return this;
    }

    private Drawer setUnloadableAtUnloaders (
        Set<Unloadable> unloadableAtUnloaders) {
        this.unloadableAtUnloaders = unloadableAtUnloaders;
        return this;
    }

    public void setDay (int day) {
        this.day = day;
    }

    void draw () {
        //noinspection InfiniteLoopStatement
        while (true) {
            csi.cls();
            csi.print(60, 5, "TANKER", CSIColor.GREEN);
            csi.print(60, 6, "CONTAINER", CSIColor.RED);
            csi.print(60, 7, "DRYCARGO", CSIColor.BLUE);
            csi.print(60, 8, "Current day: " + day, CSIColor.WHITE);
            csi.print(60, 9, "Ships in queue: " + unloadableArrived.size(),
                CSIColor.WHITE);

            drawUnloadables(unloadableArrived, false);
            drawUnloadables(unloadableAtUnloaders, true);
            drawUnloaders(unloadersList);

            printForUser();

            csi.refresh();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawUnloadables (Collection<Unloadable> unloadables,
        boolean drawWeight) {
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

            if (drawWeight) {
                csi.print(unloadable.getX(), unloadable.getY() - 1,
                    unloadable.getWeight() + "", CSIColor.WHITE);
            }
            csi.print(unloadable.getX(), unloadable.getY(),
                unloadable.getName(), color);
        }
    }

    private void drawUnloaders (ArrayList<Unloader> unloadersList) {
        unloadersList.forEach(
            i -> csi.print(i.getX(), i.getY(), "CRANE", getCsiColor(i)));
    }

    private void printForUser () {
        List<String> textLines = toLines(this.textForUser, 78);
        int i = 0;
        for (String line : textLines) {
            csi.print(USER_TEXT_COORDINATES.x, USER_TEXT_COORDINATES.y + i++,
                line);
        }
    }

    private CSIColor getCsiColor (Unloader unloader) {
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
        return color;
    }

    private List<String> toLines (String text, int lineLength) {
        List<String> lines = new LinkedList<>();
        for (int i = 0; i < text.length(); i += lineLength) {
            int j = i + lineLength;
            String substring =
                text.substring(i, j < text.length() ? j : text.length() - 1);
            lines.add(substring);
        }

        return lines;
    }

    Drawer setTextForUser (String text) {
        this.textForUser = text;
        return this;
    }

    public void drawStatistics (int unloadedCount, int fineSum,
        int averageDelay) {
        csi.print(60, 12, "Unloaded count: " + unloadedCount, CSIColor.WHITE);
        csi.print(60, 13, "Fine sum: " + fineSum, CSIColor.WHITE);
        csi.print(60, 14, "AVG delay: " + averageDelay, CSIColor.WHITE);
    }
}

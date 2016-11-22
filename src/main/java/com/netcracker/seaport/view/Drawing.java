package com.netcracker.seaport.view;

import com.netcracker.seaport.Unloader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.netcracker.seaport.Unloadable;

import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;

public class Drawing {
    private static ConsoleSystemInterface csi;
    private boolean stop;
    private int x, y;
    private int day = 0;

    public Drawing() {
        csi = new WSwingConsoleInterface("Sea Port", true);
    }


 /*   public void startDrawing(){
        csi = new WSwingConsoleInterface("RogueLike", true);
        run();
    }*/

    public void setDay(int day) {
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

    private void drawUnloadables(Collection<Unloadable> unloadables) {
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
/*
    private void run() {
        stop = false;
        x = 0;
        y = 0;
        while (!stop) {
            csi.cls();
            csi.print(x, y, 'A', CSIColor.WHITE); // отрисовка игрока

            csi.refresh();
            handleKeys(); // обработка клавиатуры

        }
        System.exit(0);
    }

    private void handleKeys() {
        CharKey dir = csi.inkey();
        if (dir.isUpArrow() && (y - 1 >= 0)) {
            y--;
        }
        if (dir.isDownArrow() && (y + 1 < 25)) {
            y++;
        }
        if (dir.isLeftArrow() && (x - 1 >= 0)) {
            x--;
        }
        if (dir.isRightArrow() && (x + 1 < 80)) {
            x++;
        }
        if (dir.code == CharKey.Q) {
            stop = true;
        }
    }*/
}

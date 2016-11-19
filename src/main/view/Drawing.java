package main.view;

import java.util.ArrayList;

import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;

public class Drawing {
    private static ConsoleSystemInterface csi;
    private boolean stop;
    private int x, y;

    public Drawing(){

    }


    public void startDrawing(){
        csi = new WSwingConsoleInterface("RogueLike", true);
        run();
    }

    public void run() {
        stop = false;
        x = 0;
        y = 0;
        while (!stop) {
            csi.cls();
            csi.print(x, y, '@', CSIColor.WHITE); // отрисовка игрока
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
    }
}

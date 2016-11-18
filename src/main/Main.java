package main;

import java.util.ArrayList;
import java.util.Properties;


public class Main {


    public static void main(String[] args) {

        // где-то тут формируется результат пользовательского ввода и по этим данным создается брокер

        Broker broker = new Broker(new ArrayList<Unloadable>(),new ArrayList<Unloader>());
        Drawing myDraw = new Drawing(broker);
        myDraw.startDrawing();
    }

}

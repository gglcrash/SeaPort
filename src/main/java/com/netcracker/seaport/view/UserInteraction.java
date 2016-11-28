package com.netcracker.seaport.view;

import com.netcracker.seaport.controller.Controller;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;

public class UserInteraction extends Thread {

    private Drawer drawer;
    private Thread drawThread;
    private Controller controller;
    private ConsoleSystemInterface csi = Drawer.getCsi();

    public UserInteraction (Drawer drawer) {
        setDrawer(drawer);
    }

    public UserInteraction setController (Controller controller) {
        if (controller != null) {
            this.controller = controller;
        }
        return this;
    }

    public Drawer getDrawer () {
        return drawer;
    }

    private UserInteraction setDrawer (Drawer drawer) {
        this.drawer = drawer;
        this.drawThread = new Thread(drawer::draw);
        return this;
    }

    @Override
    public void run () {
        drawThread.start();
        boolean end = false;
        do {
            CharKey key = csi.inkey();
            switch (key.toString()) {
                case "h":
                    showHelp();
                    break;
                case "s":
                    startSimulation();
                    break;
                case "d":
                    startTuning();
                    break;
                case "q":
                    quitApplication();
                    end = true;
                    break;
                case "p":
                    pauseSimulation();
                    break;
                case "r":
                    resumeSimulation();
                    break;
                default:
                    showUnknownCommandMessage();
            }
        } while (!end);
    }

    private void showHelp () {
        drawer.setTextForUser(UserInteractionStrings.HELP_TEXT);
    }

    private void startSimulation () {
        controller.startSimulation();
    }

    private void startTuning () {
        for (String input = csi.input(); !input.matches("^ *finish *$");
            input = csi.input()) {

            if (!isValid(input)) {
                showUnknownCommandMessage();
            } else {
                controller.tuneSimulation(input.split(" +"));
            }
        }
    }

    private void quitApplication () {
        controller.stopSimulation();
    }

    private void pauseSimulation () {
        controller.pauseSimulation();
    }

    private void resumeSimulation () {
        controller.resume();
    }

    private void showUnknownCommandMessage () {
        drawer.setTextForUser(UserInteractionStrings.UNKNOWN_COMMAND);
    }

    private boolean isValid (String s) {
        String craneRegex = "^ *cranes +container=[0-9]+ +drycargo=[0-9]+ "
            + "+tanker=[0-9]+ *$";
        String shipRegex = "^ *ship +name=[a-zA-Z]+ +type=(container|dry|"
            + "tanker) +weight=[0-9]+ *$";
        String shipAmountRegex = "^ *ships=[0-9] *$";
        String fineRegex = "^ *fine=[0-9] *$";

        return s.matches(craneRegex) || s.matches(shipRegex) || s.matches(
            shipAmountRegex) || s.matches(fineRegex);
    }

    public void greetUser () {
        drawer.setTextForUser(UserInteractionStrings.USER_GREETING);
    }

    private static final class UserInteractionStrings {
        private static final String USER_GREETING =
            "Hi! Welcome to sea port simulator! \n"
                + "Type \"-h\" to get help\n"
                + "Type \"-s\" to start simulation with default parameters\n"
                + "Type \"-d\" to define some parameters of simulation\n"
                + "If you fed up with the simulator type \"-q\" to quit.\n";

        private static final String HELP_TEXT =
            "" + "Type \"-s\" to start simulation with default parameters\n"
                + "You will be able to print \"-p\" to pauseSimulation "
                + "simulation.\n\n"
                + "You can type \"-d\" to define some parameters of simulation "
                + "before you start \n"
                + "To define amount of cranes you should type such string:\n"
                + "  cranes container=x drycargo=y tanker=z\n"
                + "Where x, y and z are integers\n"
                + "To define amount of ships you should type:\n" + "  ships=x\n"
                + "Where x is number of ships with random cargo weight and "
                + "type.\n"
                + "You can also add ship with specified cargo type and weight"
                + " \n"
                + "(during the simulation you will also be able to add ships "
                + "\n " + "in queue the same way)\n" + "Type:\n"
                + "  ship name=s type=t weight=x\n"
                + "Where s is any string, t is \"container\", \"dry\" or "
                + "\"tanker\" and x is integer\n"
                + "To define the fine for standing by type:\n" + "  fine=x\n"
                + "To finish tuning type \"finish\"" + "Where x is integer\n"
                + "Type \"-r\" to resume simulation"
                + "After simulation is done you can restart it with the "
                + "command " + "\"-r\" \n"
                + "If you fed up with the simulator type \"-q\" to quit.\n";

        private static final String UNKNOWN_COMMAND =
            "The command is unknown.\nType \"-h\" to get help\n";
    }
}

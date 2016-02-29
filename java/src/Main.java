/**
 * Created by Janis and Samu on 03.02.2016.
 */

//TODO add feature to "help" friends
//TODO move code into functions
//TODO lower cpu load  --> DONE: lol just bought a better pc
//TODO move map around to see all buildings
//TODO check doCheckForRunningGame, compiler says, wrong logic expression  --> DONE: compiler lies


import java.awt.*;
import java.lang.*;

public class Main {

    public static void main(String[] args) {
        try {

            Log log = Log.getInstance(args);
            log.print("5s until start", Log.LOGLEVEL.INFO);

            Thread.sleep(5000);

            ClickObjects.getInstance();
            //bot for taking screenshots and clicking
            Robot bot = new Robot();
            Screen scr = Screen.getInstance();
            Worker worker = new Worker();

            log.print("get clickArea", Log.LOGLEVEL.DEBUG);
            Polygon clickArea = scr.getClickArea();
            if (clickArea == null) {
                log.print("exit, no click area found", Log.LOGLEVEL.FAIL);
                System.exit(1);
            }

            log.print("Enter Main-Loop", Log.LOGLEVEL.DEBUG);
            while (true) {

                Thread.sleep(500); //sleep a little to lower cpu load
                worker.handleMoon();
                Thread.sleep(1000);
                worker.handleMoon();

                worker.treasureHunt();
                worker.useForgePoint();

                worker.collectThings();



                worker.doClose();

                worker.refresh();

                Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                bot.mouseMove(mouseLocation.x+10,mouseLocation.y+10);
            }

            //catch all exceptions and print them...
        } catch (AWTException ex) {
            Log.getInstance().print("AWTException:" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
        } catch (InterruptedException ex) {
            Log.getInstance().print("InterruptedException" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
        }
        //failerfall
        System.exit(1);
    }
}



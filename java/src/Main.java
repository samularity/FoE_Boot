/**
 * Created by Janis and Samu on 03.02.2016.
 */

//TODO lower cpu load  --> DONE: lol just bought a better pc
//TODO move map around to see all buildings

import java.awt.AWTException;
import java.lang.*;

public class Main {

    public static void main(String[] args) {
        try {

            Log log = Log.getInstance(args);

            log.print("5s until start", Log.LOGLEVEL.INFO);
            Thread.sleep(5000);

            Screen scr = Screen.getInstance();
            Worker worker = new Worker();

            if (scr.getClickArea() == null) {
                log.print("exit, no click area found", Log.LOGLEVEL.FAIL);
                System.exit(1);
            }

            log.print("Enter Main-Loop", Log.LOGLEVEL.DEBUG);
            while (true) {
                Thread.sleep(1500); //sleep a little to lower cpu load
                worker.handleMoon();
                worker.treasureHunt();
                worker.useForgePoint();
                Thread.sleep(1500);
                worker.handleMoon();
                worker.collectThings();
                worker.refresh();
                worker.doClose();
            }

        //catch all exceptions and print them...
        } catch (AWTException ex) {
            Log.getInstance().print("AWTException:" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
        } catch (InterruptedException ex) {
            Log.getInstance().print("InterruptedException" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
        }
        System.exit(1);        //failerfall
    }
}



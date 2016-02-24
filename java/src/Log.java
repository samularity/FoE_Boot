/**
 * Created by sam on 10/02/16.
 */

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Log {


    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd HH:mm:ss :");

    /*
    NONE:No logs are written/printed
    FAIL: Only Critical errors are logged
    INFO: All click actions are logged
    DEBUG: All actions are logged
    */
    public enum LOGLEVEL {
        NONE(0), FAIL(1), INFO(2), DEBUG(3);
        private final int value;   //as number
        LOGLEVEL(int value) {
            this.value = value;
        }
    }


    //class variables
    private static LOGLEVEL loglevel;
    private static JFrame statusWindow;

    //constructor
    public Log (LOGLEVEL loglevel) {
        Log.loglevel = loglevel;
        Log.statusWindow = new JFrame("started");
        Log.statusWindow.setSize(10,10);
        Log.statusWindow.setLocation(1,1);
        Log.statusWindow.setVisible(true);
    }


    //prints date, time and the given text to console and log file
    public void print(String text, LOGLEVEL priority)
    {
        if (priority.value <= loglevel.value) {
            statusWindow.setTitle(text);
            String temp = formatter.format(new Date()) + "\t" + priority.toString() + "\t"+ text;
            System.out.println(temp);
            write(temp);
        }
    }

    //add text to the log file
    public static void write (String text)
    {
        //a try with resources https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("LOG.txt", true)))) {
            out.println(text);
        } catch (IOException e) {
            System.out.println("File Write Failed");
        }
    }

    public void dispose(){
        print("killing log", LOGLEVEL.FAIL);
        statusWindow.setVisible(false);
        statusWindow.dispose();
    }


}

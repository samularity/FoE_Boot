package de.foebot; /**
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

    /*enum LOGLEVEL:

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
    private static Log instance;
    private static LOGLEVEL loglevel;
    private static JFrame statusWindow;
    private static final SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd HH:mm:ss :");

    //constructor
    private Log (LOGLEVEL loglevel) {
        Log.loglevel = loglevel;
        Log.statusWindow = new JFrame("started");
        Log.statusWindow.setSize(10,10);
        Log.statusWindow.setLocation(1,1);
        Log.statusWindow.setVisible(true);
        print("Starting Log with loglevel:" + loglevel.toString() , LOGLEVEL.NONE);
    }

    //pseudo constructors
    synchronized public static Log getInstance(LOGLEVEL logLevel) {
        if (instance == null) {
            instance = new Log(logLevel);
        }
        return instance;
    }

    synchronized public static Log getInstance(String[] args) {
        return getInstance(getLoglevel(args));
    }

    synchronized public static Log getInstance() {
        return getInstance(LOGLEVEL.FAIL);
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

    private static LOGLEVEL getLoglevel (String[] args){
        if (args.length  > 0){
            switch (args[0]){
                case "-debug":
                case "-d":
                    return (Log.LOGLEVEL.DEBUG);
                case "-info":
                case "-i" :
                    return(Log.LOGLEVEL.INFO);
                case "-fail":
                case "-f":
                    return(Log.LOGLEVEL.FAIL);
                case "-none":
                case "-n":
                    return(Log.LOGLEVEL.NONE);
                default:
                    return(Log.LOGLEVEL.DEBUG);
            }
        }
        return(Log.LOGLEVEL.DEBUG);
    }

    //add text to the log file
    private void write (String text)
    {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("LOG.txt", true)))) {
            out.println(text);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("File Write Failed");
        }
    }
}

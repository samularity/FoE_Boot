/**
 * Created by sam on 10/02/16.
 */

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
    CRITICAL: Only Critical errors are logged
    INFO: All click actions are logged
    DEBUG: All actions are logged
    */
    public enum LOGLEVEL {
        NONE(0), CRITICAL(1), INFO(2), DEBUG(3);
        private final int value;   //as number
        LOGLEVEL(int value) {
            this.value = value;
        }
    };


    //class variables
    public static LOGLEVEL loglevel;


    //constructor
    public Log (LOGLEVEL loglevel) {
        this.loglevel = loglevel;
    }


    //prints date, time and the given text to console and log file
    public void print(String text, LOGLEVEL priority)
    {
        if (priority.value <= loglevel.value) {
            String temp = formatter.format(new Date()) + "\t" + text;
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



}

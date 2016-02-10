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
  NONE: No logs are written/printed
  INFO: Only Critical errors are logged
  CRITICAL: All click actions are logged
  DEBUG: All actions are logged
  */
    public enum LOGLEVEL {
        NONE(0), CRITICAL(1), INFO(2), DEBUG(3);
        private final int value;   //as number
        LOGLEVEL(int value) {
            this.value = value;
        }
    };

    public static LOGLEVEL loglevel = LOGLEVEL.NONE;


    public Log (LOGLEVEL loglevel) {
        this.loglevel = loglevel;
    }


    public void print(String text, LOGLEVEL priority)
    {
        if (priority.value <= loglevel.value) { //console and log file
            String temp = formatter.format(new Date()) + "\t" + text;
            System.out.println(temp);
            write(temp);
        }
    }

public static void write (String text)
{
    try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("LOG.txt", true)))) {
        out.println(text);
    }catch (IOException e) {
        System.out.println("File Write Failed");
    }
}



}

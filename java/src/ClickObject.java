import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Janis on 04.02.2016.
 */

//just a struct..
public class ClickObject {

    //image of a element to click
    public BufferedImage img;

    //name of the element for the log
    public String filepath;

    //click offset
    public int xOffset;
    public int yOffset;
    public int maxClickTolerance;

    //constructor
    ClickObject(String filepath, int xOffset, int yOffset, int maxClickTolerance) throws IOException{
        Log.getInstance().print("load: " + filepath, Log.LOGLEVEL.DEBUG);
        this.img = ImageIO.read(new File(filepath));
        this.filepath = filepath;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.maxClickTolerance = maxClickTolerance;
        if (maxClickTolerance <= 0) {
            this.maxClickTolerance = 1;
        }
    }
}

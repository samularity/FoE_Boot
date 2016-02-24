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

    //is it allowed to click a second of this element directly after the first
    public boolean multipleAllowed;

    //click offset
    public int xOffset;
    public int yOffset;
    public int maxClickTolerance;

    //constructor
    ClickObject(String filepath, boolean multipleAllowed, int xOffset, int yOffset, int maxClickTolerance) throws IOException{
        Log.getInstance().print("load: " + filepath, Log.LOGLEVEL.DEBUG);
        this.img = ImageIO.read(new File(filepath));
        this.filepath = filepath;
        this.multipleAllowed = multipleAllowed;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.maxClickTolerance = maxClickTolerance;
        if (maxClickTolerance <= 0) {
            this.maxClickTolerance = 1;
        }
    }
}

import java.awt.image.BufferedImage;

/**
 * Created by Janis on 04.02.2016.
 */

//just a struct..
public class ClickObject {

    //image of a element to click
    public BufferedImage img;

    //name of the element for the log
    public String imgName;

    //is it allowed to click a second of this element directly after the first
    public boolean multipleAllowed;

    //click offset
    public int xOffset;
    public int yOffset;

    //constructor
    ClickObject(BufferedImage img, String imgName, boolean multipleAllowed, int xOffset, int yOffset){
        this.img = img;
        this.imgName = imgName;
        this.multipleAllowed = multipleAllowed;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}

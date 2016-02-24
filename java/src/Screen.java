import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Last Edit by sam on 23/02/16.
 */
public class Screen {

    private Polygon clickArea;
    private Rectangle screenSize;
    private Robot bot;

    public Screen() {
        try {
            bot = new Robot();
        }
        catch (AWTException ex) {
            Log.getInstance().print("AWTException:" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
        }
        screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        clickArea = detectClickArea(screenSize);
    }

    public Polygon getClickArea()
    {
        return clickArea;
    }

    private Polygon detectClickArea(Rectangle screenSize) {

        ClickObject muted;
        ClickObject logoff;
        try{
             muted = new ClickObject("images/muted.png", false, 0, 0, 0);
             logoff = new ClickObject("images/logoff.png", false, 0, 0, 0);
        }
        catch (IOException ex) {
            Log.getInstance().print("IOException:" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
            return null;
        }

        BufferedImage screen = bot.createScreenCapture(screenSize);

        //list of position(s) to click
        java.util.List<Position> loc_muted = find(screen, muted);
        java.util.List<Position> loc_logoff = find(screen, logoff);

        if ((!loc_muted.isEmpty()) && (!loc_logoff.isEmpty())) {

            Log.getInstance().print("upper right:" + loc_muted.get(0).x + "," + loc_muted.get(0).y, Log.LOGLEVEL.DEBUG);
            Log.getInstance().print("lower left:" +loc_logoff.get(0).x + "," + loc_logoff.get(0).y, Log.LOGLEVEL.DEBUG);

            /*
            *
            *   Points: every X is a corner of the polygon
            *   Start top left, go clockwise
            *
            *   X---------------------X
            *   |                     |
            *   |                     |
            *   |                     |
            *   X--X  X---X           |
            *      X--X   |           |
            *             X-----------X
            *
            */


            Polygon ClickArea = new Polygon();

            ClickArea.addPoint(loc_muted.get(0).x, loc_logoff.get(0).y - 5); //upper left
            ClickArea.addPoint(loc_logoff.get(0).x + 5, loc_logoff.get(0).y- 5); //upper right
            ClickArea.addPoint(loc_logoff.get(0).x + 5, loc_muted.get(0).y); //lower right
            //crop menu block
            ClickArea.addPoint(loc_muted.get(0).x + 210, loc_muted.get(0).y); //lower center
            ClickArea.addPoint(loc_muted.get(0).x + 210, loc_muted.get(0).y - 140); //center center
            //forge point
            ClickArea.addPoint(loc_muted.get(0).x + 110, loc_muted.get(0).y - 140); //forge point upper right
            ClickArea.addPoint(loc_muted.get(0).x + 110, loc_muted.get(0).y - 70); //forge point lower right
            ClickArea.addPoint(loc_muted.get(0).x + 60, loc_muted.get(0).y - 70); //forge point lower left
            ClickArea.addPoint(loc_muted.get(0).x + 60, loc_muted.get(0).y - 140); //forge point upper left

            ClickArea.addPoint(loc_muted.get(0).x, loc_muted.get(0).y - 140); //left center

            Log.getInstance().print(ClickArea.toString(), Log.LOGLEVEL.DEBUG);
            return ClickArea;
        }
        return null;
    }

    public BufferedImage getScreen() {
        //get a screenshot
        return bot.createScreenCapture(screenSize);
    }

    //finds images and returns the positions
    public static java.util.List<Position> find (BufferedImage big, ClickObject small) {
        //List which will be returned
        java.util.List<Position> ret = new ArrayList<>();

        //two for loops for addressing every pixel in the big picture
        for (int xBig = 0; xBig < (big.getWidth() - small.img.getWidth()); xBig++) {
            for (int yBig = 0; yBig < (big.getHeight() - small.img.getHeight()); yBig++) {

                //position in small picture
                int xSmall = 0;
                int ySmall = 0;

                //colors of the pixels
                Color cSmall, cBig;

                //difference of r,g,b
                int rDif, gDif, bDif;

                boolean breakLoop = false;
                while (!breakLoop) {
                    cSmall = new Color(small.img.getRGB(xSmall, ySmall));
                    cBig = new Color(big.getRGB(xBig + xSmall, yBig + ySmall));
                    rDif = cBig.getRed() - cSmall.getRed();
                    gDif = cBig.getGreen() - cSmall.getGreen();
                    bDif = cBig.getBlue() - cSmall.getBlue();

                    double difference = (rDif * rDif) + (gDif * gDif) + (bDif * bDif);

                    if (difference > 1200) {
                        breakLoop = true;
                    }
                    xSmall++;
                    if (xSmall >= small.img.getWidth()) {
                        xSmall = 0;
                        ySmall++;
                        if (ySmall >= small.img.getHeight()) {
                            int x = xBig + (small.img.getWidth() / 2);
                            int y = yBig + (small.img.getHeight() / 2);
                            ret.add(new Position(x, y));

                            if (!small.multipleAllowed) {
                                return ret;
                            }
                            breakLoop = true;
                        }
                    }
                }

            }
        }
        Collections.shuffle(ret);
        return ret;
    }

}


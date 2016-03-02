package de.foebot;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Last Edit by sam on 23/02/16.
 */
public class Screen {

    private static Screen instance;

    private final Polygon clickArea;
    private final Rectangle screenSize;
    private Robot bot;

    private Screen() throws AWTException , IOException{
        bot = new Robot();
        screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        clickArea = detectClickArea(screenSize);
    }

    //pseudo constructors
    synchronized public static Screen getInstance() throws AWTException, IOException {
        if (instance == null) {
            instance = new Screen();
        }
        return instance;
    }


    public Polygon getClickArea()
    {
        return clickArea;
    }

    private Polygon detectClickArea(Rectangle screenSize) throws IOException {

        BufferedImage screen = bot.createScreenCapture(screenSize);

        //list of position(s) to click
        java.util.List<Point> loc_muted = find(screen, ClickObjects.getInstance().muted, false);
        java.util.List<Point> loc_logoff = find(screen, ClickObjects.getInstance().logoff, false);

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


            Polygon clickArea = new Polygon();

            clickArea.addPoint(loc_muted.get(0).x, loc_logoff.get(0).y - 5); //upper left
            clickArea.addPoint(loc_logoff.get(0).x + 5, loc_logoff.get(0).y- 5); //upper right
            clickArea.addPoint(loc_logoff.get(0).x + 5, loc_muted.get(0).y); //lower right
            //crop menu block
            clickArea.addPoint(loc_muted.get(0).x + 210, loc_muted.get(0).y); //lower center
            clickArea.addPoint(loc_muted.get(0).x + 210, loc_muted.get(0).y - 140); //center center
            //forge point
            clickArea.addPoint(loc_muted.get(0).x + 110, loc_muted.get(0).y - 140); //forge point upper right
            clickArea.addPoint(loc_muted.get(0).x + 110, loc_muted.get(0).y - 70); //forge point lower right
            clickArea.addPoint(loc_muted.get(0).x + 60, loc_muted.get(0).y - 70); //forge point lower left
            clickArea.addPoint(loc_muted.get(0).x + 60, loc_muted.get(0).y - 140); //forge point upper left

            clickArea.addPoint(loc_muted.get(0).x, loc_muted.get(0).y - 140); //left center

            Log.getInstance().print(clickArea.toString(), Log.LOGLEVEL.DEBUG);
            return clickArea;
        }
        return null;
    }

    public BufferedImage getScreen() {
        //get a screenshot
        return bot.createScreenCapture(screenSize);
    }



    private BufferedImage big;
    //finds images and returns the positions
    public java.util.List<Point> find (BufferedImage big, ClickObject small, boolean multipleAllowed) {
        //List which will be returned
        java.util.List<Point> ret = new ArrayList<>();

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
                            ret.add(new Point(x , y));

                            if (!multipleAllowed) {
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

    //clicks on Points with randomized delay and offset
    public void click(java.util.List<Point> clickPos, ClickObject icon) throws InterruptedException {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        int xClick, yClick;

        for (Point pos : clickPos) {
            xClick = pos.x + icon.xOffset + (rand.nextInt(icon.maxClickTolerance) - (icon.maxClickTolerance / 2));
            yClick = pos.y + icon.yOffset + (rand.nextInt(icon.maxClickTolerance) - (icon.maxClickTolerance / 2));

            if (clickArea.contains(xClick,yClick) || (icon.xOffset == 0 && icon.yOffset == 0)){
                bot.mouseMove(xClick, yClick);
                Thread.sleep(100);
                bot.mousePress(InputEvent.BUTTON1_MASK);
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
                Thread.sleep(100 + rand.nextInt(400));
            }
        }
    }

}


/**
 * Created by Janis on 03.02.2016.
 */

import java.awt.*;
import java.awt.event.InputEvent;
//import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import javax.imageio.*;

public class Main {

    static Log log = new Log(Log.LOGLEVEL.DEBUG);

    public static void main(String[] args) {
        try {
            //bot for taking screenshots and clicking
            Robot bot = new Robot();

            Date currentDate = new Date();
            long lastRefresh = currentDate.getTime() / 1000;

            //object for the screenshot
            BufferedImage screen;

            log.print("Starting", Log.LOGLEVEL.INFO);
            Thread.sleep(4000);

            log.print("Start Reading Images", Log.LOGLEVEL.DEBUG);
            //images
            BufferedImage head = ImageIO.read(new File("images/head.png"));

            //refresh opera or chrome
            //BufferedImage refresh = ImageIO.read(new File("refresh_opera.png"));
            BufferedImage refresh = ImageIO.read(new File("images/refresh_chrome.png"));

            //forge points
            BufferedImage forgePoint = ImageIO.read(new File("images/forgePoints/forgePoint.png"));
            BufferedImage science = ImageIO.read(new File("images/forgePoints/science.png"));
            BufferedImage light = ImageIO.read(new File("images/forgePoints/light.png"));
            BufferedImage useForgePoint = ImageIO.read(new File("images/forgePoints/useForgePoint.png"));
            BufferedImage unlock = ImageIO.read(new File("images/forgePoints/unlock.png"));

            //treasure hunt
            BufferedImage treasureHunt = ImageIO.read(new File("images/treasureHunt/treasureHunt.png"));
            BufferedImage open = ImageIO.read(new File("images/treasureHunt/open.png"));
            BufferedImage ok = ImageIO.read(new File("images/treasureHunt/ok.png"));

            log.print("create list with Click Objects", Log.LOGLEVEL.DEBUG);
            //list of click objects
            java.util.List<ClickObject> icons = new ArrayList<ClickObject>();

            icons.add(new ClickObject(ImageIO.read(new File("images/moon.png")), "moon", false, 0, 50));

            icons.add(new ClickObject(ImageIO.read(new File("images/cole.png")), "cole", false, 0, 0));
            icons.add(new ClickObject(ImageIO.read(new File("images/trophy.png")), "trophy", false, 0, 0));
            icons.add(new ClickObject(ImageIO.read(new File("images/wood.png")), "wood", false, 0, 0));
            icons.add(new ClickObject(ImageIO.read(new File("images/bottle.png")), "bottle", false, 0, 0));
            icons.add(new ClickObject(ImageIO.read(new File("images/marble.png")), "marble", false, 0, 0));
            icons.add(new ClickObject(ImageIO.read(new File("images/horseshoe.png")), "horseshoe", false, 0, 0));

            icons.add(new ClickObject(ImageIO.read(new File("images/recrute.png")), "recrute", false, 0, 0));
            icons.add(new ClickObject(ImageIO.read(new File("images/recrute2.png")), "recrute2", false, 0, 0));

            icons.add(new ClickObject(ImageIO.read(new File("images/close.png")), "close", false, 0, 0));
            icons.add(new ClickObject(ImageIO.read(new File("images/close2.png")), "close2", false, 0, 0));

            icons.add(new ClickObject(ImageIO.read(new File("images/coin.png")), "coin", true, 0, 70));
            icons.add(new ClickObject(ImageIO.read(new File("images/box.png")), "box", true, 0, 70));
            icons.add(new ClickObject(ImageIO.read(new File("images/swords.png")), "swords", true, 0, 50));
            icons.add(new ClickObject(ImageIO.read(new File("images/hammer.png")), "hammer", true, 0, 90));


            java.util.List<Position> clickPos;


            log.print("Enter Main-Loop", Log.LOGLEVEL.INFO);
            //(never ending) main loop
            boolean breakMainLoop = false;
            while (!breakMainLoop) {

                /*
                bot.keyPress(KeyEvent.VK_UP);
                bot.keyRelease(KeyEvent.VK_UP);
                */


                //first get a screenshot
                log.print("start capturing screen", Log.LOGLEVEL.DEBUG);
                screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                log.print("Screen capture done", Log.LOGLEVEL.DEBUG);

                clickPos = getOnePosInImage(screen, treasureHunt);
                if (!clickPos.isEmpty()) {
                    log.print("Treasure-Hunt", Log.LOGLEVEL.INFO);
                    click(bot, clickPos, 0, 0);

                    Thread.sleep(5000);
                    screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    clickPos = getOnePosInImage(screen, open);
                    click(bot, clickPos, 0, 0);

                    Thread.sleep(5000);
                    screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    clickPos = getOnePosInImage(screen, ok);
                    click(bot, clickPos, 0, 0);
                }


                if (!getOnePosInImage(screen, forgePoint).isEmpty()) {
                    log.print("Enter Forge-Point Menu", Log.LOGLEVEL.DEBUG);
                    clickPos = getOnePosInImage(screen, science);
                    click(bot, clickPos, 0, 0);

                    Thread.sleep(5000);
                    screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    clickPos = getOnePosInImage(screen, light);
                    click(bot, clickPos, 0, 0);

                    Thread.sleep(2000);
                    screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    clickPos = getOnePosInImage(screen, useForgePoint);
                    while (!clickPos.isEmpty()) {
                        log.print("Use Forge-Point", Log.LOGLEVEL.INFO);
                        click(bot, clickPos, 0, 0);
                        Thread.sleep(2000);
                        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                        clickPos = getOnePosInImage(screen, useForgePoint);
                    }

                    clickPos = getOnePosInImage(screen, unlock);
                    click(bot, clickPos, 0, 0);
                    Thread.sleep(2000);
                    screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                }

                int count = 0;
                ClickObject icon;
                boolean breakLoop = false;
                while (!breakLoop) {
                    //the icon to find and click from the list
                    icon = icons.get(count);

                    //search for one or for more
                    if (icon.multipleAllowed) {
                        clickPos = getAllPosInImage(screen, icon.img);
                    } else {
                        clickPos = getOnePosInImage(screen, icon.img);
                    }

                    //click it
                    click(bot, clickPos, icon.xOffset, icon.yOffset);

                    //wait for a popup and make a new screenshot
                    if ((!icon.multipleAllowed) && (clickPos.size() > 0)) {
                        Thread.sleep(200);
                        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    }

                    //stop if game isn't at the screen
                    if (getOnePosInImage(screen, head).isEmpty()) {
                        breakLoop = true;
                        breakMainLoop = true;
                        log.print("No game found!", Log.LOGLEVEL.CRITICAL);
                    }


                    currentDate = new Date();

                    if (((currentDate.getTime() / 1000) - lastRefresh) > 1800) {
                        clickPos = getOnePosInImage(screen, refresh);
                        click(bot, clickPos, 0, 0);
                        lastRefresh = currentDate.getTime() / 1000;
                        System.out.println(lastRefresh);
                    }

                    //count up
                    count++;
                    if (count >= icons.size()) {
                        breakLoop = true;
                    }
                }
                bot.mouseMove(200, 0);

            }

            //catch all exceptions and print them...
        } catch (AWTException ex) {
            log.print("AWTException"+ ex.getMessage(), Log.LOGLEVEL.CRITICAL);
        } catch (IOException ex) {
            log.print("IOException" + ex.getMessage(), Log.LOGLEVEL.CRITICAL);
        } catch (InterruptedException ex) {
            log.print("InterruptedException" + ex.getMessage(), Log.LOGLEVEL.CRITICAL);
        }
    }

    //clicks on Points with randomized delay and offset
    public static void click(Robot bot, java.util.List<Position> clickPos, int xOff, int yOff) throws InterruptedException {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis() / 1000L);
        int xClick, yClick;

        for (Position pos : clickPos) {
            xClick = pos.x + xOff + (rand.nextInt(10) - 5);
            yClick = pos.y + yOff + (rand.nextInt(10) - 5);
            bot.mouseMove(xClick, yClick);
            bot.mousePress(InputEvent.BUTTON1_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(100 + rand.nextInt(400));
        }
    }

    //finds images and returns the positions
    public static java.util.List<Position> getAllPosInImage(BufferedImage big, BufferedImage small) {
        //List which will be returned
        java.util.List<Position> ret = new ArrayList<Position>();

        //two for loops for addressing every pixel in the big picture
        for (int xBig = 0; xBig < (big.getWidth() - small.getWidth()); xBig++) {
            for (int yBig = 0; yBig < (big.getHeight() - small.getHeight()); yBig++) {

                //position in smal picture
                int xSmall = 0;
                int ySmall = 0;

                //colors of the pixels
                Color cSmall, cBig;

                //difference of r,g,b
                int rDif, gDif, bDif;

                boolean breakLoop = false;
                while (!breakLoop) {
                    cSmall = new Color(small.getRGB(xSmall, ySmall));
                    cBig = new Color(big.getRGB(xBig + xSmall, yBig + ySmall));
                    rDif = cBig.getRed() - cSmall.getRed();
                    gDif = cBig.getGreen() - cSmall.getGreen();
                    bDif = cBig.getBlue() - cSmall.getBlue();


                    if ((rDif != 0) || (gDif != 0) || (bDif != 0)) {
                        breakLoop = true;
                    }

                    xSmall++;
                    if (xSmall >= small.getWidth()) {
                        xSmall = 0;
                        ySmall++;
                        if (ySmall >= small.getHeight()) {
                            int x = xBig + (small.getWidth() / 2);
                            int y = yBig + (small.getHeight() / 2);
                            ret.add(new Position(x, y));
                            breakLoop = true;
                        }
                    }
                }

            }
        }

        return ret;
    }

    //nearly the same as getPosInImage just faster and returns just one pos...
    public static java.util.List<Position> getOnePosInImage(BufferedImage big, BufferedImage small) {
        //List which will be returned
        java.util.List<Position> ret = new ArrayList<Position>();

        //two for loops for addressing every pixel in the big picture
        for (int xBig = 0; xBig < (big.getWidth() - small.getWidth()); xBig++) {
            for (int yBig = 0; yBig < (big.getHeight() - small.getHeight()); yBig++) {

                //position in smal picture
                int xSmall = 0;
                int ySmall = 0;

                //colors of the pixels
                Color cSmall, cBig;

                //difference of r,g,b
                int rDif, gDif, bDif;

                boolean breakLoop = false;
                while (!breakLoop) {
                    cSmall = new Color(small.getRGB(xSmall, ySmall));
                    cBig = new Color(big.getRGB(xBig + xSmall, yBig + ySmall));
                    rDif = cBig.getRed() - cSmall.getRed();
                    gDif = cBig.getGreen() - cSmall.getGreen();
                    bDif = cBig.getBlue() - cSmall.getBlue();

                    if ((rDif != 0) || (gDif != 0) || (bDif != 0)) {
                        breakLoop = true;
                    }

                    xSmall++;
                    if (xSmall >= small.getWidth()) {
                        xSmall = 0;
                        ySmall++;
                        if (ySmall >= small.getHeight()) {
                            int x = xBig + (small.getWidth() / 2);
                            int y = yBig + (small.getHeight() / 2);
                            ret.add(new Position(x, y));
                            return ret;
                        }
                    }
                }

            }
        }

        return ret;
    }
}


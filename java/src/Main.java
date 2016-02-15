/**
 * Created by Janis on 03.02.2016.
 */

//TODO add border to limit the clickable area
//TODO add feature to "help" friends
//TODO move code in functions
//TODO lower cpu load
//TODO check doCheckForRunningGame, compiler say, wrong logic expression

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import javax.imageio.*;
import javax.swing.*;

public class Main {

    static JFrame mainWindow = new JFrame("started");

    //change the log level here
    static Log log = new Log(Log.LOGLEVEL.DEBUG);


    public static void main(String[] args) {
        try {
            mainWindow.setSize(10,10);
            mainWindow.setLocation(1,1);
            mainWindow.setVisible(true);

            //bot for taking screenshots and clicking
            Robot bot = new Robot();

            //save the current time for refreshing periodically
            long lastRefresh = new Date().getTime() / 1000;

            //object for the screenshot
            BufferedImage screen;

            //list of position(s) to click
            java.util.List<Position> clickPos;


            log.print("Starting", Log.LOGLEVEL.INFO);
            mainWindow.setTitle("Starting");
            Thread.sleep(2000);


            //images
            log.print("Start Reading Images", Log.LOGLEVEL.DEBUG);
            mainWindow.setTitle("Reading Images");

            //if this isn't on screen the program will stop
            BufferedImage head = ImageIO.read(new File("images/head.png"));

            //refresh button from opera or chrome
            BufferedImage refresh = ImageIO.read(new File("images/refresh_opera.png"));
            //BufferedImage refresh = ImageIO.read(new File("images/refresh_chrome.png"));

            //close
            BufferedImage close = ImageIO.read(new File("images/close.png"));
            BufferedImage close2 = ImageIO.read(new File("images/close2.png"));
            BufferedImage close3 = ImageIO.read(new File("images/close3.png"));
            BufferedImage close4 = ImageIO.read(new File("images/close4.png"));

            //forge points
            BufferedImage forgePoint = ImageIO.read(new File("images/forgePoints/forgePoint.png"));
            BufferedImage science = ImageIO.read(new File("images/forgePoints/science.png"));
            BufferedImage light = ImageIO.read(new File("images/forgePoints/light.png"));
            BufferedImage useForgePoint = ImageIO.read(new File("images/forgePoints/useForgePoint.png"));
            BufferedImage unlock = ImageIO.read(new File("images/forgePoints/unlock.png"));

            //treasure hunt
            BufferedImage treasureHunt = ImageIO.read(new File("images/treasureHunt/treasureHunt.png"));
            BufferedImage open = ImageIO.read(new File("images/treasureHunt/open.png"));
            BufferedImage open2 = ImageIO.read(new File("images/treasureHunt/open2.png"));
            BufferedImage ok = ImageIO.read(new File("images/treasureHunt/ok.png"));

            //handleMoon
            BufferedImage moon = ImageIO.read(new File("images/moon.png"));
            java.util.List<ClickObject> moonList = new ArrayList<>();
            moonList.add(new ClickObject(ImageIO.read(new File("images/cole.png")), "cole", false, 0, 0));
            moonList.add(new ClickObject(ImageIO.read(new File("images/trophy.png")), "trophy", false, 0, 0));
            moonList.add(new ClickObject(ImageIO.read(new File("images/wood.png")), "wood", false, 0, 0));
            moonList.add(new ClickObject(ImageIO.read(new File("images/bottle.png")), "bottle", false, 0, 0));
            moonList.add(new ClickObject(ImageIO.read(new File("images/marble.png")), "marble", false, 0, 0));
            moonList.add(new ClickObject(ImageIO.read(new File("images/horseshoe.png")), "horseshoe", false, 0, 0));
            moonList.add(new ClickObject(ImageIO.read(new File("images/recrute.png")), "recrute", false, 0, 0));
            moonList.add(new ClickObject(ImageIO.read(new File("images/recrute2.png")), "recrute2", false, 0, 0));


            //list of click objects
            log.print("create list with Click Objects", Log.LOGLEVEL.DEBUG);
            java.util.List<ClickObject> icons = new ArrayList<>();

            //first object in list will be clicked first!!!
            icons.add(new ClickObject(ImageIO.read(new File("images/coin.png")), "coin", true, 0, 70));
            icons.add(new ClickObject(ImageIO.read(new File("images/box.png")), "box", true, 0, 70));
            icons.add(new ClickObject(ImageIO.read(new File("images/swords.png")), "swords", true, 0, 50));
            icons.add(new ClickObject(ImageIO.read(new File("images/hammer.png")), "hammer", true, 0, 90));
            icons.add(new ClickObject(ImageIO.read(new File("images/thunder.png")), "thunder", true, 0, 70));

            log.print("Enter Main-Loop", Log.LOGLEVEL.DEBUG);
            boolean breakMainLoop = false;
            while (!breakMainLoop) {

                /*
                bot.keyPress(KeyEvent.VK_UP);
                bot.keyRelease(KeyEvent.VK_UP);
                */

                log.print("do Main-Loop", Log.LOGLEVEL.DEBUG);

                doTreasureHunt(bot, treasureHunt, open, open2, ok, close, close2, close3, close4);
                doUseForgePoint(bot, forgePoint, science, light, useForgePoint, unlock, close, close2, close3, close4);
                doHandleMoon(bot, moon, moonList, close, close2, close3, close4);

                //loops through all icons in the list and clicks them
                int iconCount = 0;
                ClickObject icon;

                //get a screenshot
                screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

                boolean breakLoop = false;
                while (!breakLoop) {

                    //the icon to click from the list
                    icon = icons.get(iconCount);

                    mainWindow.setTitle(icon.imgName);

                    //search for one or for more
                    clickPos = getPosInImage(screen, icon.img, icon.multipleAllowed);


                    //click it
                    click(bot, clickPos, icon.xOffset, icon.yOffset);

                    //wait for a popup and make a new screenshot
                    if ((!icon.multipleAllowed) && (clickPos.size() > 0)) {
                        Thread.sleep(1000);
                        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    }

                    //stop if game isn't at the screen
                    if (!doCheckForRunningGame(screen, head, close, close2, close3)) {
                        breakLoop = true;
                        breakMainLoop = true;
                        log.print("No running game on screen!", Log.LOGLEVEL.CRITICAL);
                        mainWindow.setVisible(false);
                        mainWindow.dispose();
                    }

                    //refresh periodically
                    if (((new Date().getTime() / 1000) - lastRefresh) > 1800) {
                        clickPos = getPosInImage(screen, refresh, false);
                        click(bot, clickPos, 0, 0);
                        lastRefresh = new Date().getTime() / 1000;
                        log.print("Refresh the page", Log.LOGLEVEL.INFO);
                        mainWindow.setTitle("refresh");
                        Thread.sleep(60000);
                    }

                    //count up
                    iconCount++;
                    if (iconCount >= icons.size()) {
                        breakLoop = true;
                    }
                }

                doClose(bot, close, close2, close3, close4);

            }

            //catch all exceptions and print them...
        } catch (AWTException ex) {
            log.print("AWTException" + ex.getMessage(), Log.LOGLEVEL.CRITICAL);
        } catch (IOException ex) {
            log.print("IOException" + ex.getMessage(), Log.LOGLEVEL.CRITICAL);
        } catch (InterruptedException ex) {
            log.print("InterruptedException" + ex.getMessage(), Log.LOGLEVEL.CRITICAL);
        }
    }

    public static boolean doCheckForRunningGame(BufferedImage screen, BufferedImage head, BufferedImage close, BufferedImage close2, BufferedImage close3) {
        boolean onScreen = false;
        onScreen |= !getPosInImage(screen, head, false).isEmpty();
        onScreen |= !getPosInImage(screen, close, false).isEmpty();
        onScreen |= !getPosInImage(screen, close2, false).isEmpty();
        onScreen |= !getPosInImage(screen, close3, false).isEmpty();
        return onScreen;
    }

    public static void doUseForgePoint(
            Robot bot,
            BufferedImage forgePoint,
            BufferedImage science,
            BufferedImage light,
            BufferedImage useForgePoint,
            BufferedImage unlock,
            BufferedImage close,
            BufferedImage close2,
            BufferedImage close3,
            BufferedImage close4) throws InterruptedException {

        BufferedImage screen;
        java.util.List<Position> clickPos;

        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        if (!getPosInImage(screen, forgePoint, false).isEmpty()) {
            log.print("Enter Forge-Point Menu", Log.LOGLEVEL.DEBUG);
            mainWindow.setTitle("forge point");
            clickPos = getPosInImage(screen, science, false);
            click(bot, clickPos, 0, 0);

            //light
            Thread.sleep(5000);
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            clickPos = getPosInImage(screen, light, false);
            click(bot, clickPos, 0, 0);

            //use 1 forgepoint
            Thread.sleep(2000);
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            clickPos = getPosInImage(screen, useForgePoint, false);
            log.print("Use Forge-Point", Log.LOGLEVEL.INFO);
            click(bot, clickPos, 0, 0);

            //unlock
            Thread.sleep(2000);
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            clickPos = getPosInImage(screen, unlock, false);
            click(bot, clickPos, 0, 0);


            doClose(bot, close, close2, close3, close4);
        }
    }

    public static void doTreasureHunt(
            Robot bot,
            BufferedImage treasureHunt,
            BufferedImage open,
            BufferedImage open2,
            BufferedImage ok,
            BufferedImage close,
            BufferedImage close2,
            BufferedImage close3,
            BufferedImage close4) throws InterruptedException {

        BufferedImage screen;
        java.util.List<Position> clickPos;

        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        clickPos = getPosInImage(screen, treasureHunt, false);
        if (!clickPos.isEmpty()) {
            log.print("Treasure-Hunt", Log.LOGLEVEL.INFO);
            mainWindow.setTitle("treasure hunt");

            click(bot, clickPos, 0, 0);

            //open
            Thread.sleep(5000);
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            clickPos = getPosInImage(screen, open, false);
            click(bot, clickPos, 50, 15);
            clickPos = getPosInImage(screen, open2, false);
            click(bot, clickPos, 50, 15);

            //ok
            Thread.sleep(5000);
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            clickPos = getPosInImage(screen, ok, false);
            click(bot, clickPos, 0, 0);


            doClose(bot, close, close2, close3, close4);
        }
    }


    public static void doHandleMoon(
            Robot bot,
            BufferedImage moon,
            java.util.List<ClickObject> moonList,
            BufferedImage close,
            BufferedImage close2,
            BufferedImage close3,
            BufferedImage close4) throws InterruptedException {

        BufferedImage screen;
        java.util.List<Position> clickPos;

        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        clickPos = getPosInImage(screen, moon, false);
        if (!clickPos.isEmpty()) {
            log.print("handle Moon", Log.LOGLEVEL.INFO);
            mainWindow.setTitle("handle moon");

            click(bot, clickPos, 0, 50);
            Thread.sleep(2000);

            //loops through all icons in the list and clicks them
            int iconCount = 0;
            ClickObject icon;

            //get a screenshot
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

            boolean breakLoop = false;
            while (!breakLoop) {

                //the icon to click from the list
                icon = moonList.get(iconCount);

                mainWindow.setTitle(icon.imgName);

                //search for one or for more
                clickPos = getPosInImage(screen, icon.img, icon.multipleAllowed);

                //click it
                click(bot, clickPos, icon.xOffset, icon.yOffset);

                if (!clickPos.isEmpty()){
                    breakLoop = true;
                }

                //count up
                iconCount++;
                if (iconCount >= moonList.size()) {
                    breakLoop = true;
                }
            }


            doClose(bot, close, close2, close3, close4);
        }
    }

    //clicks on the close Buttons
    public static void doClose(Robot bot, BufferedImage close, BufferedImage close2, BufferedImage close3, BufferedImage close4) throws InterruptedException {
        BufferedImage screen;
        java.util.List<Position> clickPos;

        //close
        Thread.sleep(2000);
        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        clickPos = getPosInImage(screen, close, false);
        click(bot, clickPos, 0, 0);

        //close3
        clickPos = getPosInImage(screen, close3, false);
        click(bot, clickPos, 0, 0);

        //close4
        clickPos = getPosInImage(screen, close4, false);
        click(bot, clickPos, 0, 0);

        //close2
        Thread.sleep(2000);
        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        clickPos = getPosInImage(screen, close2, false);
        click(bot, clickPos, 0, 0);
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
            Thread.sleep(100);
            bot.mousePress(InputEvent.BUTTON1_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(100 + rand.nextInt(400));
        }
    }

    //finds images and returns the positions
    public static java.util.List<Position> getPosInImage(BufferedImage big, BufferedImage small, boolean multipleAllowed) {
        //List which will be returned
        java.util.List<Position> ret = new ArrayList<>();

        //two for loops for addressing every pixel in the big picture
        for (int xBig = 0; xBig < (big.getWidth() - small.getWidth()); xBig++) {
            for (int yBig = 0; yBig < (big.getHeight() - small.getHeight()); yBig++) {

                //position in small picture
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
}



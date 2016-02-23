/**
 * Created by Janis and Samu on 03.02.2016.
 */

//TODO add feature to "help" friends
//TODO move code into functions
//TODO lower cpu load
//TODO move map areound to see all buildings
//TODO check doCheckForRunningGame, compiler says, wrong logic expression  --> DONE: compiler lies


import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {





    //change the log level here
    static Log log = new Log(Log.LOGLEVEL.DEBUG);


    public static void main(String[] args) {
        try {
            //bot for taking screenshots and clicking
            Robot bot = new Robot();

            //save the current time for refreshing periodically
            long lastRefresh = new Date().getTime() / 1000;

            //object for the screenshot
            BufferedImage screen;

            //list of position(s) to click
            java.util.List<Position> clickPos;

            Polygon clickArea;

            log.print("5s until start", Log.LOGLEVEL.INFO);
            Thread.sleep(5000);


            //images

            //if this isn't on screen the program will stop
            ClickObject head = new ClickObject("images/", "head.png", false, 0, 0, 0, log);
            ClickObject head2 = new ClickObject("images/", "head2.png", false, 0, 0, 0, log);

            //refresh button from opera or chrome
            ClickObject refresh0 = new ClickObject("images/", "refresh_opera.png", false, 0, 0, 0, log);
            ClickObject refresh1 = new ClickObject("images/", "refresh_chrome.png", false, 0, 0, 0, log);

            //close#
            ClickObject close = new ClickObject("images/", "close.png", false, 0, 0, 4, log);
            ClickObject close1 = new ClickObject("images/", "close1.png", false, 0, 0, 4, log);
            ClickObject close2 = new ClickObject("images/", "close2.png", false, 0, 0, 4, log);
            ClickObject close3 = new ClickObject("images/", "close3.png", false, 0, 0, 4, log);

            //forge points
            ClickObject forgePoint = new ClickObject("images/forgePoints/", "forgePoint.png", false, 0, 0, 0, log);
            ClickObject science = new ClickObject("images/forgePoints/", "science.png", false, 0, 0, 10, log);
            ClickObject light = new ClickObject("images/forgePoints/", "light.png", false, 0, 0, 10, log);
            ClickObject useForgePoint = new ClickObject("images/forgePoints/", "useForgePoint.png", false, 0, 0, 10, log);
            ClickObject unlock = new ClickObject("images/forgePoints/", "unlock.png", false, 0, 0, 10, log);

            //treasure hunt
            ClickObject treasureHunt = new ClickObject("images/treasureHunt/", "treasureHunt.png", false, -30, 0, 10, log);
            ClickObject open = new ClickObject("images/treasureHunt/", "open.png", false, 50, 15, 10, log);
            ClickObject ok = new ClickObject("images/treasureHunt/", "ok.png", false, 50, 10, 10, log);
            ClickObject ok2 = new ClickObject("images/treasureHunt/", "ok2.png", false, 50, 10, 10, log);

            //handleMoon
            ClickObject moon = new ClickObject("images/", "moon.png", true, 0, 75, 30, log);
            java.util.List<ClickObject> moonList = new ArrayList<>();
            moonList.add(new ClickObject("images/", "produce.png", false, 50, 10, 10, log));
            moonList.add(new ClickObject("images/", "produce2.png", false, 50, 10, 10, log));
            moonList.add(new ClickObject("images/", "produce3.png", false, 50, 10, 10, log));
            moonList.add(new ClickObject("images/", "recrute.png", false, 50, 10, 10, log));
            moonList.add(new ClickObject("images/", "recrute2.png", false, 50, 10, 10, log));


            //list of click objects
            java.util.List<ClickObject> icons = new ArrayList<>();

            //first object in list will be clicked first!!!

            icons.add(new ClickObject("images/", "coin.png", true, 0, 70, 10, log));
            icons.add(new ClickObject("images/", "box.png", true, 0, 70, 10, log));
            icons.add(new ClickObject("images/", "swords.png", true, 0, 70, 30, log));
            icons.add(new ClickObject("images/", "hammer.png", true, 0, 90, 10, log));
            icons.add(new ClickObject("images/", "thunder.png", true, 0, 70, 30, log));


            ClickObject muted = new ClickObject("images/", "muted.png", false, 0, 0, 0, log);
            ClickObject logoff = new ClickObject("images/", "logoff.png", false, 0, 0, 0, log);

            log.print("get clickArea", Log.LOGLEVEL.DEBUG);
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            clickArea = getClickArea(screen, muted, logoff);
            if (clickArea == null) {
                //no click area found
                log.dispose();
                System.exit(1);
            }

            log.print("Enter Main-Loop", Log.LOGLEVEL.DEBUG);
            while (true) {

                /*
                bot.keyPress(KeyEvent.VK_UP);
                bot.keyRelease(KeyEvent.VK_UP);
                */

                //Thread.sleep(1000); //sleep a little to lower cpu load
                doHandleMoon(bot, moon, moonList, close, close1,  close2, close3, clickArea);
                Thread.sleep(1000);
                doHandleMoon(bot, moon, moonList, close, close1,  close2, close3, clickArea);
                doTreasureHunt(bot, treasureHunt, open, ok, ok2, close, close1, close2, close3, clickArea);
                doUseForgePoint(bot, forgePoint, science, light, useForgePoint, unlock, close, close1, close2, close3, clickArea);

                //loops through all icons in the list and clicks them
                int iconCount = 0;
                ClickObject icon;

                //get a screenshot
                screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

                boolean breakLoop = false;
                while (!breakLoop) {

                    //the icon to click from the list
                    icon = icons.get(iconCount);
                    log.print(icon.imgName, Log.LOGLEVEL.INFO);

                    //search for one or for more
                    clickPos = getPosInImage(screen, icon);


                    //click it
                    click(bot, clickPos, icon, clickArea);

                    //wait for a popup and make a new screenshot
                    if ((!icon.multipleAllowed) && (clickPos.size() > 0)) {
                        Thread.sleep(1000);
                        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    }

                    //stop if game isn't at the screen
                    if (!doCheckForRunningGame(screen, head, head2)) {
                        log.print("No running game on screen!", Log.LOGLEVEL.FAIL);
                        log.dispose();
                        System.exit(0);
                    }

                    //refresh periodically
                    if (((new Date().getTime() / 1000) - lastRefresh) > 1800) {
                        clickPos = getPosInImage(screen, refresh0);
                        click(bot, clickPos, refresh0, clickArea );

                        clickPos = getPosInImage(screen, refresh1);
                        click(bot, clickPos, refresh1, clickArea );
                        lastRefresh = new Date().getTime() / 1000;
                        log.print("Refresh the page", Log.LOGLEVEL.INFO);
                        Thread.sleep(60000);
                    }

                    //count up
                    iconCount++;
                    if (iconCount >= icons.size()) {
                        breakLoop = true;
                    }
                }

                doClose(bot, close, close1, close2, close3, clickArea);
                Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                bot.mouseMove(mouseLocation.x+10,mouseLocation.y+10);
            }

            //catch all exceptions and print them...
        } catch (AWTException ex) {
            log.print("AWTException:" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
        } catch (IOException ex) {
            log.print("IOException:" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
        } catch (InterruptedException ex) {
            log.print("InterruptedException" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
        }
        //failerfall
        log.dispose();
        System.exit(1);
    }

    public static boolean doCheckForRunningGame(BufferedImage screen, ClickObject head, ClickObject head2) {
        boolean onScreen = false;
        onScreen |= !getPosInImage(screen, head).isEmpty();
        onScreen |= !getPosInImage(screen, head2).isEmpty();
        return onScreen;
    }

    public static void doUseForgePoint(
            Robot bot,
            ClickObject forgePoint,
            ClickObject science,
            ClickObject light,
            ClickObject useForgePoint,
            ClickObject unlock,
            ClickObject close,
            ClickObject close1,
            ClickObject close2,
            ClickObject close3,
            Polygon clickArea) throws InterruptedException {

        BufferedImage screen;
        java.util.List<Position> clickPos;

        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        if (!getPosInImage(screen, forgePoint).isEmpty()) {
            log.print("Enter Forge-Point Menu", Log.LOGLEVEL.DEBUG);
            clickPos = getPosInImage(screen, science);
            click(bot, clickPos, science, clickArea);

            //light
            Thread.sleep(5000);
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            clickPos = getPosInImage(screen, light);
            click(bot, clickPos, light, clickArea);

            //use 1 forgepoint
            Thread.sleep(2000);
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            clickPos = getPosInImage(screen, useForgePoint);
            log.print("Use Forge-Point", Log.LOGLEVEL.INFO);
            click(bot, clickPos, forgePoint, clickArea);

            //unlock
            Thread.sleep(2000);
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            clickPos = getPosInImage(screen, unlock);
            click(bot, clickPos, unlock, clickArea);


            doClose(bot, close, close1, close2, close3, clickArea);
        }
    }

    public static void doTreasureHunt(
            Robot bot,
            ClickObject treasureHunt,
            ClickObject open,
            ClickObject ok,
            ClickObject ok2,
            ClickObject close,
            ClickObject close1,
            ClickObject close2,
            ClickObject close3,
            Polygon clickArea) throws InterruptedException {

        BufferedImage screen;
        java.util.List<Position> clickPos;

        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        clickPos = getPosInImage(screen, treasureHunt);
        if (!clickPos.isEmpty()) {
            log.print("Treasure-Hunt", Log.LOGLEVEL.INFO);

            click(bot, clickPos, treasureHunt, clickArea);

            //open
            Thread.sleep(5000);
            log.print("open", Log.LOGLEVEL.DEBUG);
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            clickPos = getPosInImage(screen, open);
            click(bot, clickPos, open, clickArea);

            //ok
            Thread.sleep(5000);
            log.print("ok", Log.LOGLEVEL.DEBUG);
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            clickPos = getPosInImage(screen, ok);
            click(bot, clickPos, ok, clickArea);
            clickPos = getPosInImage(screen, ok2);
            click(bot, clickPos, ok2, clickArea);


            doClose(bot, close, close1, close2, close3, clickArea);
        }
    }


    public static void doHandleMoon(
            Robot bot,
            ClickObject moon,
            java.util.List<ClickObject> moonList,
            ClickObject close,
            ClickObject close1,
            ClickObject close2,
            ClickObject close3,
            Polygon clickArea) throws InterruptedException {

        BufferedImage screen;
        java.util.List<Position> clickPos;
        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        clickPos = getPosInImage(screen, moon);
        if (!clickPos.isEmpty()) {
            log.print("handle Moon", Log.LOGLEVEL.INFO);

            while (clickPos.size() > 1){
                clickPos.remove(1);
            }

            click(bot, clickPos, moon, clickArea);
            bot.mouseMove(200,200);
            Thread.sleep(1000);


            //loops through all icons in the list and clicks them
            int iconCount = 0;
            ClickObject icon;

            //get a screenshot
            screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

            boolean breakLoop = false;
            while (!breakLoop) {

                //the icon to click from the list
                icon = moonList.get(iconCount);
                log.print(icon.imgName, Log.LOGLEVEL.DEBUG);

                //search for one or for more
                clickPos = getPosInImage(screen, icon);

                //click it
                click(bot, clickPos, icon, clickArea);

                if (!clickPos.isEmpty()) {
                    breakLoop = true;
                }

                //count up
                iconCount++;
                if (iconCount >= moonList.size()) {
                    breakLoop = true;
                }
            }


            //doClose(bot, close, close1,close2, close3, clickArea);
        }
    }

    //clicks on the close Buttons
    public static void doClose(
            Robot bot,
            ClickObject close,
            ClickObject close1,
            ClickObject close2,
            ClickObject close3,
            Polygon clickArea) throws InterruptedException {
        BufferedImage screen;
        java.util.List<Position> clickPos;

        //close
        log.print("close", Log.LOGLEVEL.DEBUG);
        screen = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        clickPos = getPosInImage(screen, close);
        click(bot, clickPos, close, clickArea);

        //close1
        clickPos = getPosInImage(screen, close1);
        click(bot, clickPos, close1, clickArea);

        //close3
        clickPos = getPosInImage(screen, close3);
        click(bot, clickPos, close3, clickArea);

        //close2
        clickPos = getPosInImage(screen, close2);
        click(bot, clickPos, close2, clickArea);
        log.print("close done", Log.LOGLEVEL.DEBUG);
    }

    //clicks on Points with randomized delay and offset
    public static void click(Robot bot, java.util.List<Position> clickPos, ClickObject icon, Polygon clickArea) throws InterruptedException {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        int xClick, yClick;

        for (Position pos : clickPos) {
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

    public static Polygon getClickArea(BufferedImage screen, ClickObject muted, ClickObject logoff) {

        //list of position(s) to click
        java.util.List<Position> loc_muted = getPosInImage(screen, muted);
        java.util.List<Position> loc_logoff = getPosInImage(screen, logoff);

        if ((!loc_muted.isEmpty()) && (!loc_logoff.isEmpty())) {
            log.print("upper right:" + loc_muted.get(0).x + "," + loc_muted.get(0).y, Log.LOGLEVEL.DEBUG);
            log.print("lower left:" +loc_logoff.get(0).x + "," + loc_logoff.get(0).y, Log.LOGLEVEL.DEBUG);

            /*
            *
            *   Points: every X is a corner of the polygon
            *   Start top left clockwise
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

            log.print(ClickArea.toString(), Log.LOGLEVEL.DEBUG);
            return ClickArea;
        }
        return null;
    }


    //finds images and returns the positions
    public static java.util.List<Position> getPosInImage(BufferedImage big, ClickObject small) {
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



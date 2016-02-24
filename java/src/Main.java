/**
 * Created by Janis and Samu on 03.02.2016.
 */

//TODO add feature to "help" friends
//TODO move code into functions
//TODO lower cpu load  --> DONE: lol just bought a better pc
//TODO move map around to see all buildings
//TODO check doCheckForRunningGame, compiler says, wrong logic expression  --> DONE: compiler lies


import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.*;
import java.util.*;
import java.lang.*;

public class Main {

    static Screen scr; //TODO make privat


    public static void main(String[] args) {
        try {

            Log log = Log.getInstance(Log.LOGLEVEL.DEBUG);
            log.print("5s until start", Log.LOGLEVEL.INFO);
            Thread.sleep(5000);


            ClickObjects.getInstance();
            //bot for taking screenshots and clicking
            Robot bot = new Robot();
            scr = new Screen();

            //save the current time for refreshing periodically
            long lastRefresh = new Date().getTime() / 1000;

            //object for the screenshot
            BufferedImage screen;

            //list of position(s) to click
            java.util.List<Position> clickPos;

            log.print("get clickArea", Log.LOGLEVEL.DEBUG);
            Polygon clickArea = scr.getClickArea();
            if (clickArea == null) {
                Log.getInstance().closeWindow();
                System.exit(1);
            }

            Log.getInstance().print("Enter Main-Loop", Log.LOGLEVEL.DEBUG);
            while (true) {

                Thread.sleep(500); //sleep a little to lower cpu load
                doHandleMoon(bot, clickArea);
                Thread.sleep(1000);
                doHandleMoon(bot, clickArea);
                doTreasureHunt(bot, clickArea);
                doUseForgePoint(bot, clickArea);

                //loops through all icons in the list and clicks them
                int iconCount = 0;
                ClickObject icon;

                //get a screenshot
                screen = scr.getScreen();

                boolean breakLoop = false;
                while (!breakLoop) {

                    //the icon to click from the list
                    icon = ClickObjects.getInstance().icons.get(iconCount);
                    log.print(icon.filepath, Log.LOGLEVEL.INFO);

                    //search for one or for more
                    clickPos = getPosInImage(screen, icon);


                    //click it
                    click(bot, clickPos, icon, clickArea);

                    //wait for a popup and make a new screenshot
                    if ((!icon.multipleAllowed) && (clickPos.size() > 0)) {
                        Thread.sleep(1000);
                        screen = scr.getScreen();
                    }

                    //stop if game isn't at the screen
                    if (!doCheckForRunningGame(screen)) {
                        log.print("No running game on screen!", Log.LOGLEVEL.FAIL);
                        log.closeWindow();
                        System.exit(0);
                    }

                    //refresh periodically
                    if (((new Date().getTime() / 1000) - lastRefresh) > 1800) {
                        clickPos = getPosInImage(screen, ClickObjects.getInstance().refresh0);
                        click(bot, clickPos, ClickObjects.getInstance().refresh0, clickArea );

                        clickPos = getPosInImage(screen, ClickObjects.getInstance().refresh1);
                        click(bot, clickPos, ClickObjects.getInstance().refresh1, clickArea );
                        lastRefresh = new Date().getTime() / 1000;
                        log.print("Refresh the page", Log.LOGLEVEL.INFO);
                        Thread.sleep(60000);
                    }

                    //count up
                    iconCount++;
                    if (iconCount >= ClickObjects.getInstance().icons.size()) {
                        breakLoop = true;
                    }
                }

                doClose(bot, clickArea);
                Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                bot.mouseMove(mouseLocation.x+10,mouseLocation.y+10);
            }

            //catch all exceptions and print them...
        } catch (AWTException ex) {
            Log.getInstance().print("AWTException:" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
        } catch (InterruptedException ex) {
            Log.getInstance().print("InterruptedException" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
        }
        //failerfall
        Log.getInstance().closeWindow();
        System.exit(1);
    }

    public static boolean doCheckForRunningGame(BufferedImage screen) {
        boolean onScreen = false;
        onScreen |= !getPosInImage(screen, ClickObjects.getInstance().head).isEmpty();
        onScreen |= !getPosInImage(screen, ClickObjects.getInstance().head2).isEmpty();
        return onScreen;
    }

    public static void doUseForgePoint(
            Robot bot,
            Polygon clickArea) throws InterruptedException {

        BufferedImage screen;
        java.util.List<Position> clickPos;

        screen = scr.getScreen();
        if (!getPosInImage(screen, ClickObjects.getInstance().forgePoint).isEmpty()) {
            Log.getInstance().print("Enter Forge-Point Menu", Log.LOGLEVEL.DEBUG);
            clickPos = getPosInImage(screen, ClickObjects.getInstance().science);
            click(bot, clickPos, ClickObjects.getInstance().science, clickArea);

            //light
            Thread.sleep(5000);
            screen = scr.getScreen();
            clickPos = getPosInImage(screen, ClickObjects.getInstance().light);
            click(bot, clickPos, ClickObjects.getInstance().light, clickArea);

            //use 1 forgepoint
            Thread.sleep(2000);
            screen = scr.getScreen();
            clickPos = getPosInImage(screen, ClickObjects.getInstance().useForgePoint);
            Log.getInstance().print("Use Forge-Point", Log.LOGLEVEL.INFO);
            click(bot, clickPos, ClickObjects.getInstance().forgePoint, clickArea);

            //unlock
            Thread.sleep(2000);
            screen = scr.getScreen();
            clickPos = getPosInImage(screen, ClickObjects.getInstance().unlock);
            click(bot, clickPos, ClickObjects.getInstance().unlock, clickArea);


            doClose(bot, clickArea);
        }
    }

    public static void doTreasureHunt(
            Robot bot,
            Polygon clickArea) throws InterruptedException {

        BufferedImage screen;
        java.util.List<Position> clickPos;

        screen = scr.getScreen();
        clickPos = getPosInImage(screen, ClickObjects.getInstance().treasureHunt);
        if (!clickPos.isEmpty()) {
            Log.getInstance().print("Treasure-Hunt", Log.LOGLEVEL.INFO);

            click(bot, clickPos, ClickObjects.getInstance().treasureHunt, clickArea);

            //open
            Thread.sleep(5000);
            Log.getInstance().print("open", Log.LOGLEVEL.DEBUG);
            screen = scr.getScreen();
            clickPos = getPosInImage(screen, ClickObjects.getInstance().open);
            click(bot, clickPos, ClickObjects.getInstance().open, clickArea);

            //ok
            Thread.sleep(5000);
            Log.getInstance().print("ok", Log.LOGLEVEL.DEBUG);
            screen = scr.getScreen();
            clickPos = getPosInImage(screen, ClickObjects.getInstance().ok);
            click(bot, clickPos, ClickObjects.getInstance().ok, clickArea);
            clickPos = getPosInImage(screen, ClickObjects.getInstance().ok2);
            click(bot, clickPos, ClickObjects.getInstance().ok2, clickArea);


            doClose(bot, clickArea);
        }
    }


    public static void doHandleMoon(
            Robot bot,
            Polygon clickArea) throws InterruptedException {

        BufferedImage screen;
        java.util.List<Position> clickPos;
        screen = scr.getScreen();
        clickPos = getPosInImage(screen, ClickObjects.getInstance().moon);
        if (!clickPos.isEmpty()) {
            Log.getInstance().print("handle Moon", Log.LOGLEVEL.INFO);

            while (clickPos.size() > 1){
                clickPos.remove(1);
            }

            click(bot, clickPos, ClickObjects.getInstance().moon, clickArea);
            Random rand = new Random();

            bot.mouseMove(100+ rand.nextInt(100),100+rand.nextInt(100));
            Thread.sleep(1000);


            //loops through all icons in the list and clicks them
            int iconCount = 0;
            ClickObject icon;

            //get a screenshot
            screen = scr.getScreen();

            boolean breakLoop = false;
            while (!breakLoop) {

                //the icon to click from the list
                icon = ClickObjects.getInstance().moonList.get(iconCount);
                Log.getInstance().print(icon.filepath, Log.LOGLEVEL.DEBUG);

                //search for one or for more
                clickPos = getPosInImage(screen, icon);

                //click it
                click(bot, clickPos, icon, clickArea);

                if (!clickPos.isEmpty()) {
                    breakLoop = true;
                }

                //count up
                iconCount++;
                if (iconCount >= ClickObjects.getInstance().moonList.size()) {
                    breakLoop = true;
                }
            }
        }
    }

    //clicks on the close Buttons
    public static void doClose(
            Robot bot,
            Polygon clickArea) throws InterruptedException {
        BufferedImage screen;
        java.util.List<Position> clickPos;

        //close
        Log.getInstance().print("close", Log.LOGLEVEL.DEBUG);
        screen = scr.getScreen();
        clickPos = getPosInImage(screen, ClickObjects.getInstance().close);
        click(bot, clickPos, ClickObjects.getInstance().close, clickArea);

        //close1
        clickPos = getPosInImage(screen, ClickObjects.getInstance().close1);
        click(bot, clickPos, ClickObjects.getInstance().close1, clickArea);

        //close3
        clickPos = getPosInImage(screen, ClickObjects.getInstance().close3);
        click(bot, clickPos, ClickObjects.getInstance().close3, clickArea);

        //close2
        clickPos = getPosInImage(screen, ClickObjects.getInstance().close2);
        click(bot, clickPos, ClickObjects.getInstance().close2, clickArea);
        Log.getInstance().print("close done", Log.LOGLEVEL.DEBUG);
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



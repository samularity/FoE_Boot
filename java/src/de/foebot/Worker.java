package de.foebot;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Janis on 24.02.2016.
 */
public class Worker {
    private Screen scr;
    private ClickObjects clObj;
    private Log log;
    private long lastRefresh;

    public Worker() throws IOException, AWTException {
        scr = Screen.getInstance();
        clObj = ClickObjects.getInstance();
        log = Log.getInstance();
        lastRefresh = System.currentTimeMillis();

    }

    public void useForgePoint() throws InterruptedException {

        java.util.List<Point> clickPos;

        log.print("ForgePoint", Log.LOGLEVEL.DEBUG);

        if (!scr.find(clObj.forgePoint, false).isEmpty()) {
            log.print("found: ForgePoint", Log.LOGLEVEL.INFO);

            clickPos = scr.find(clObj.science, false);
            scr.click(clickPos, clObj.science);

            //light
            Thread.sleep(5000);
            log.print("light", Log.LOGLEVEL.INFO);
            clickPos = scr.find(clObj.light, false);
            scr.click(clickPos, clObj.light);

            //use 1 forge-point
            Thread.sleep(2000);
            log.print("use one", Log.LOGLEVEL.INFO);
            clickPos = scr.find(clObj.useForgePoint, false);
            scr.click(clickPos, clObj.forgePoint);

            //unlock
            Thread.sleep(2000);
            log.print("unlock", Log.LOGLEVEL.INFO);
            clickPos = scr.find(clObj.unlock, false);
            scr.click(clickPos, clObj.unlock);

            doClose();
        }

    }

    public void treasureHunt() throws InterruptedException {

        java.util.List<Point> clickPos;

        log.print("Treasure-Hunt", Log.LOGLEVEL.DEBUG);

        clickPos = scr.find(clObj.treasureHunt, false);
        if (!clickPos.isEmpty()) {
            log.print("found: Treasure-Hunt", Log.LOGLEVEL.INFO);

            scr.click(clickPos, clObj.treasureHunt);

            //open
            Thread.sleep(5000);
            log.print("open", Log.LOGLEVEL.INFO);
            clickPos = scr.find(clObj.open, false);
            scr.click(clickPos, clObj.open);

            //ok
            Thread.sleep(5000);
            log.print("ok", Log.LOGLEVEL.INFO);
            scr.getScreen();
            clickPos = scr.find(clObj.ok, false, false);
            scr.click(clickPos, clObj.ok);
            clickPos = scr.find(clObj.ok2, false, false);
            scr.click(clickPos, clObj.ok2);

            doClose();
        }
    }

    public void doClose() throws InterruptedException {

        java.util.List<Point> clickPos;

        //doClose
        log.print("doClose", Log.LOGLEVEL.INFO);
        scr.getScreen();
        for(ClickObject icon : clObj.closes){
            clickPos = scr.find(icon, false, false);
            scr.click(clickPos, icon);
        }
    }

    public void handleMoon() throws InterruptedException, AWTException {

        java.util.List<Point> clickPos;

        log.print("handle Moon", Log.LOGLEVEL.DEBUG);

        clickPos = scr.find(clObj.moon, true);
        if (!clickPos.isEmpty()) {
            log.print("found: Moon", Log.LOGLEVEL.INFO);

            while (clickPos.size() > 1){
                clickPos.remove(1);
            }

            scr.click(clickPos, clObj.moon);

            Random rand = new Random();
            new Robot().mouseMove(100+ rand.nextInt(100),100+rand.nextInt(100));
            Thread.sleep(1000);

            //loops through all correctable in the list and clicks them
            int iconCount = 0;
            ClickObject icon;

            //get a screenshot
            scr.getScreen();

            boolean breakLoop = false;
            while (!breakLoop) {

                //the icon to click from the list
                icon = clObj.moonList.get(iconCount);

                log.print(icon.filepath, Log.LOGLEVEL.DEBUG);

                clickPos = scr.find(icon, false, false);
                scr.click(clickPos, icon);

                //count up
                iconCount++;
                if ((iconCount >= clObj.moonList.size()) || (!clickPos.isEmpty())) {
                    breakLoop = true;
                }
            }
        }
    }

    public void collectThings() throws InterruptedException {

        java.util.List<Point> clickPos;

        log.print("collect Things", Log.LOGLEVEL.INFO);

        //loops through all correctable in the list and clicks them
        int iconCount = 0;
        ClickObject icon;

        //get a screenshot
        scr.getScreen();

        boolean breakLoop = false;
        while (!breakLoop) {

            //the icon to click from the list
            icon = clObj.correctable.get(iconCount);
            log.print(icon.filepath, Log.LOGLEVEL.DEBUG);

            clickPos = scr.find(icon, true, false);
            scr.click(clickPos, icon);

            //count up
            iconCount++;
            if (iconCount >= clObj.correctable.size()) {
                breakLoop = true;
            }
        }
    }

    public void refresh() throws InterruptedException {

        java.util.List<Point> clickPos;

        log.print("refresh", Log.LOGLEVEL.DEBUG);

        //refresh periodically
        if (((System.currentTimeMillis()) - lastRefresh) > 1800000) { // 1.800.000 ms = 30min
            log.print("time for a refresh", Log.LOGLEVEL.INFO);
            scr.getScreen();
            for(ClickObject icon : clObj.refreshes){
                clickPos = scr.find(icon, false, false);
                scr.click(clickPos, icon);
            }
            lastRefresh = System.currentTimeMillis();
            Thread.sleep(60000);
        }
    }

}

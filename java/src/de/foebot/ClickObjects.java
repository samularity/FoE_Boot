package de.foebot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Janis on 24.02.2016.
 */
public class ClickObjects {
    private static ClickObjects instance;
    //images

    //to get clickable area corners in game
    public final ClickObject muted;
    public final ClickObject logoff;

    //forge points
    public final ClickObject forgePoint;
    public final ClickObject science;
    public final ClickObject light;
    public final ClickObject useForgePoint;
    public final ClickObject unlock;

    //treasure hunt
    public final ClickObject treasureHunt;
    public final ClickObject open;
    public final ClickObject ok;
    public final ClickObject ok2;

    //handleMoon
    public final ClickObject moon;
    public final List<ClickObject> moonList;

    //list of click objects
    public final List<ClickObject> correctable;

    //doClose
    public final List<ClickObject> closes;

    //refresh button from opera or chrome
    public final List<ClickObject> refreshes;


    //constructor
    private ClickObjects() throws IOException {
            ArrayList<ClickObject> temp;

            muted = new ClickObject("de/foebot/images/muted.png", 0, 0, 0);
            logoff = new ClickObject("de/foebot/images/logoff.png", 0, 0, 0);

            forgePoint = new ClickObject("de/foebot/images/forgePoints/forgePoint.png", 0, 0, 0);
            science = new ClickObject("de/foebot/images/forgePoints/science.png", 0, 0, 10);
            light = new ClickObject("de/foebot/images/forgePoints/light.png", 0, 0, 10);
            useForgePoint = new ClickObject("de/foebot/images/forgePoints/useForgePoint.png", 0, 0, 10);
            unlock = new ClickObject("de/foebot/images/forgePoints/unlock.png", 0, 0, 10);

            treasureHunt = new ClickObject("de/foebot/images/treasureHunt/treasureHunt.png", -30, 0, 10);
            open = new ClickObject("de/foebot/images/treasureHunt/open.png", 50, 15, 10);
            ok = new ClickObject("de/foebot/images/treasureHunt/ok.png", 50, 10, 10);
            ok2 = new ClickObject("de/foebot/images/treasureHunt/ok2.png", 50, 10, 10);

            moon = new ClickObject("de/foebot/images/moon.png", 0, 75, 30);
            moonList = new ArrayList<>();
            moonList.add(new ClickObject("de/foebot/images/produce.png", 50, 10, 10));
            moonList.add(new ClickObject("de/foebot/images/produce2.png", 50, 10, 10));
            moonList.add(new ClickObject("de/foebot/images/recruit.png", 50, 10, 10));

        temp = new ArrayList<>();
            temp.add(new ClickObject("de/foebot/images/close.png", 0, 0, 4));
            temp.add(new ClickObject("de/foebot/images/close1.png", 0, 0, 4));
            temp.add(new ClickObject("de/foebot/images/close2.png", 0, 0, 4));
            temp.add(new ClickObject("de/foebot/images/close3.png", 0, 0, 4));
            closes = Collections.unmodifiableList(temp);

            temp = new ArrayList<>();
            temp.add(new ClickObject("de/foebot/images/coin.png", 0, 70, 10));
            temp.add(new ClickObject("de/foebot/images/box.png", 0, 70, 10));
            temp.add(new ClickObject("de/foebot/images/swords.png", 0, 70, 30));
            temp.add(new ClickObject("de/foebot/images/hammer.png", 0, 90, 10));
            temp.add(new ClickObject("de/foebot/images/thunder.png", 0, 70, 30));
            correctable = Collections.unmodifiableList(temp);

            refreshes = new ArrayList<>();
            refreshes.add(new ClickObject("de/foebot/images/refresh_opera.png", 0, 0, 0));
            refreshes.add(new ClickObject("de/foebot/images/refresh_chrome.png", 0, 0, 0));

    }

    //pseudo constructors
    synchronized public static ClickObjects getInstance() throws IOException {
        if (instance == null) {
            instance = new ClickObjects();
        }
        return instance;
    }



}

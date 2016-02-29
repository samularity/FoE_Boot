import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janis on 24.02.2016.
 */
public class ClickObjects {

    private static ClickObjects instance;
    //images

    //to get clickable area corners in game
    public ClickObject muted;
    public ClickObject logoff;

    //forge points
    public ClickObject forgePoint;
    public ClickObject science;
    public ClickObject light;
    public ClickObject useForgePoint;
    public ClickObject unlock;

    //treasure hunt
    public ClickObject treasureHunt;
    public ClickObject open;
    public ClickObject ok;
    public ClickObject ok2;

    //handleMoon
    public ClickObject moon;
    public List<ClickObject> moonList;

    //list of click objects
    public List<ClickObject> correctable;

    //doClose
    public List<ClickObject> closes;

    //refresh button from opera or chrome
    public List<ClickObject> refreshes;


    //constructor
    private ClickObjects() {
        try {

            muted = new ClickObject("images/muted.png", 0, 0, 0);
            logoff = new ClickObject("images/logoff.png", 0, 0, 0);

            forgePoint = new ClickObject("images/forgePoints/forgePoint.png", 0, 0, 0);
            science = new ClickObject("images/forgePoints/science.png", 0, 0, 10);
            light = new ClickObject("images/forgePoints/light.png", 0, 0, 10);
            useForgePoint = new ClickObject("images/forgePoints/useForgePoint.png", 0, 0, 10);
            unlock = new ClickObject("images/forgePoints/unlock.png", 0, 0, 10);

            treasureHunt = new ClickObject("images/treasureHunt/treasureHunt.png", -30, 0, 10);
            open = new ClickObject("images/treasureHunt/open.png", 50, 15, 10);
            ok = new ClickObject("images/treasureHunt/ok.png", 50, 10, 10);
            ok2 = new ClickObject("images/treasureHunt/ok2.png", 50, 10, 10);

            moon = new ClickObject("images/moon.png", 0, 75, 30);
            moonList = new ArrayList<>();
            moonList.add(new ClickObject("images/produce.png", 50, 10, 10));
            moonList.add(new ClickObject("images/produce2.png", 50, 10, 10));
            moonList.add(new ClickObject("images/recruit.png", 50, 10, 10));

            closes = new ArrayList<>();
            closes.add(new ClickObject("images/close.png", 0, 0, 4));
            closes.add(new ClickObject("images/close1.png", 0, 0, 4));
            closes.add(new ClickObject("images/close2.png", 0, 0, 4));
            closes.add(new ClickObject("images/close3.png", 0, 0, 4));

            correctable = new ArrayList<>();
            correctable.add(new ClickObject("images/coin.png", 0, 70, 10));
            correctable.add(new ClickObject("images/box.png", 0, 70, 10));
            correctable.add(new ClickObject("images/swords.png", 0, 70, 30));
            correctable.add(new ClickObject("images/hammer.png", 0, 90, 10));
            correctable.add(new ClickObject("images/thunder.png", 0, 70, 30));

            refreshes = new ArrayList<>();
            refreshes.add(new ClickObject("images/refresh_opera.png", 0, 0, 0));
            refreshes.add(new ClickObject("images/refresh_chrome.png", 0, 0, 0));

        } catch (IOException ex){
            Log.getInstance().print("IOException:" + ex.getMessage() + "\r\n" + ex.toString(), Log.LOGLEVEL.FAIL);
        }
    }

    //pseudo constructors
    synchronized public static ClickObjects getInstance() {
        if (instance == null) {
            instance = new ClickObjects();
        }
        return instance;
    }



}

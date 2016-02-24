import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janis on 24.02.2016.
 */
public class ClickObjects {

    private static ClickObjects instance;
    //images

    //if this isn't on screen the program will stop
    public ClickObject head;
    public ClickObject head2;

    //refresh button from opera or chrome
    public ClickObject refresh0;
    public ClickObject refresh1;

    //close
    public ClickObject close;
    public ClickObject close1;
    public ClickObject close2;
    public ClickObject close3;

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
    public List<ClickObject> moonList = new ArrayList<>();

    //list of click objects
    public List<ClickObject> icons = new ArrayList<>();


    //constructor
    private ClickObjects() {
        try {

            head = new ClickObject("images/head.png", false, 0, 0, 0);
            head2 = new ClickObject("images/head2.png", false, 0, 0, 0);

            refresh0 = new ClickObject("images/refresh_opera.png", false, 0, 0, 0);
            refresh1 = new ClickObject("images/refresh_chrome.png", false, 0, 0, 0);

            close = new ClickObject("images/close.png", false, 0, 0, 4);
            close1 = new ClickObject("images/close1.png", false, 0, 0, 4);
            close2 = new ClickObject("images/close2.png", false, 0, 0, 4);
            close3 = new ClickObject("images/close3.png", false, 0, 0, 4);

            forgePoint = new ClickObject("images/forgePoints/forgePoint.png", false, 0, 0, 0);
            science = new ClickObject("images/forgePoints/science.png", false, 0, 0, 10);
            light = new ClickObject("images/forgePoints/light.png", false, 0, 0, 10);
            useForgePoint = new ClickObject("images/forgePoints/useForgePoint.png", false, 0, 0, 10);
            unlock = new ClickObject("images/forgePoints/unlock.png", false, 0, 0, 10);

            treasureHunt = new ClickObject("images/treasureHunt/treasureHunt.png", false, -30, 0, 10);
            open = new ClickObject("images/treasureHunt/open.png", false, 50, 15, 10);
            ok = new ClickObject("images/treasureHunt/ok.png", false, 50, 10, 10);
            ok2 = new ClickObject("images/treasureHunt/ok2.png", false, 50, 10, 10);

            moon = new ClickObject("images/moon.png", true, 0, 75, 30);
            moonList = new ArrayList<>();
            moonList.add(new ClickObject("images/produce.png", false, 50, 10, 10));
            moonList.add(new ClickObject("images/produce2.png", false, 50, 10, 10));
            moonList.add(new ClickObject("images/produce3.png", false, 50, 10, 10));
            moonList.add(new ClickObject("images/recrute.png", false, 50, 10, 10));
            moonList.add(new ClickObject("images/recrute2.png", false, 50, 10, 10));


            //list of click objects
            icons = new ArrayList<>();
            icons.add(new ClickObject("images/coin.png", true, 0, 70, 10));
            icons.add(new ClickObject("images/box.png", true, 0, 70, 10));
            icons.add(new ClickObject("images/swords.png", true, 0, 70, 30));
            icons.add(new ClickObject("images/hammer.png", true, 0, 90, 10));
            icons.add(new ClickObject("images/thunder.png", true, 0, 70, 30));

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

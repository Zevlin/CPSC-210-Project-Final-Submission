package utilities;


import model.Timestamp;
import java.util.ArrayList;

public class TimeLogger extends Thread {

    // FIELDS
    private static final int INTERVAL = 3000;
    private ArrayList<Timestamp> timeLog;

    // CONSTRUCTOR
    public TimeLogger() {
        timeLog = new ArrayList<>();
    }

    // METHODS

    //MODIFIES: this
    //EFFECTS: adds a new timestamp to the bottom of the ArrayList
    public void newStamp(Timestamp t) {
        timeLog.add(t);
    }

    //MODIFIES: Timestamp
    //EFFECTS: calls the update() method to update Timestamp values
    public void updateStamp() {
        timeLog.get(timeLog.size() - 1).update();;
    }

    //EFFECTS: prints a log of the current timeLog to console
    public void printLog() {
        for (Timestamp t: timeLog) {
            System.out.println(t.toString());
        }
    }

}

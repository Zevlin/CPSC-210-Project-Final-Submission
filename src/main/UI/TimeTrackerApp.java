package ui;

import utilities.TimeLogger;

import java.util.*;
import java.util.Timer;

public class TimeTrackerApp {

    // FIELDS
    private TimeLogger timeLogger;
    private Timer timer;
    private TimestampUpdater updater;
    MainFrame window;


    // PRIMARY METHODS
    public static void main(String[] args) throws Exception {
        System.out.println("main method");
        TimeTrackerApp tta = new TimeTrackerApp();
        tta.start();
    } // end of main()

    private void start() throws Exception {
        try {
            System.out.println("start method");
            timeLogger = new TimeLogger();
            window = new MainFrame(timeLogger);
            updater = new TimestampUpdater();
            timer = new Timer(true);
            timer.scheduleAtFixedRate(updater, 0, timeLogger.getUpdateInterval());
        } catch (IllegalArgumentException iae) {
            timer.scheduleAtFixedRate(updater, 0, 1000);
        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////

    // TimerTask Class
    private class TimestampUpdater extends TimerTask {
        //MODIFIES: timeLogger, window
        //EFFECTS: calls timeLogger.updateStamp() to update TimeStamp data, then calls window.update() to reflect new
        //      data in GUI as appropriate
        @Override
        public void run() {
            try {
                System.out.println("updating GUI");
                timeLogger.updateStamp();
                window.update();
            } catch (Exception e) {
                System.out.println("error updating GPU with exception " + e.getMessage());
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////


}

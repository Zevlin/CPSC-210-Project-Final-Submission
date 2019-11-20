package ui;

import utilities.TimeLogger;
import java.util.*;
import java.util.Timer;
import java.io.*;

public class TimeTrackerApp {

    // FIELDS
    private TimeLogger timeLogger;
    private Timer timer;
    private TimestampUpdater updater;


    // PRIMARY METHODS
    public static void main(String[] args) throws IOException {
        System.out.println("main method");
        MainFrame window = new MainFrame();
    } // end of main()


////////////////////////////////////////////////////////////////////////////////////////////////////

    // TimerTask Class
    private class TimestampUpdater extends TimerTask {

        @Override
        public void run() {
            try {
                System.out.println("updating GUI");
                timeLogger.updateStamp();
//                updateGUI();
            } catch (Exception e) {
                System.out.println("error updating GPU with exception " + e.getMessage());
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////


}

package utilities;


import model.Activity;
import model.Rest;
import model.Timestamp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TimeLogger extends Thread implements Saveable, Loadable {

    // FIELDS
    private static final int INTERVAL = 3000;
    private ArrayList<Timestamp> timeLog;
    private transient File file = new File(".\\Data\\timeLog.csv");

    // CONSTRUCTOR
    public TimeLogger() throws Exception {
        timeLog = new ArrayList<>();
        loadData();
        newRest(new Rest("Rest"));
    }

    // METHODS

    //MODIFIES: this
    //EFFECTS: adds a new activity to the bottom of the ArrayList
    public void newActivity(Activity t) {
        System.out.println("creating new timestamp: " + t.toString());
        timeLog.add(t);
    }

    //MODIFIES: this
    //EFFECTS: adds a new timestamp to the bottom of the ArrayList
    public void newRest(Rest t) {
        System.out.println("creating new timestamp: " + t.toString());
        timeLog.add(t);
    }

    //MODIFIES: Timestamp
    //EFFECTS: calls the update() method to update Timestamp values
    public void updateStamp() throws Exception {
        timeLog.get(timeLog.size() - 1).update();
        saveData(timeLog);
    }

//    //EFFECTS: prints a log of the current timeLog to console
//    public void printLog() {
//        for (Timestamp t: timeLog) {
//            System.out.println(t.toString());
//        }
//    }

    //REQUIRES: timeLog has a stamp
    //EFFECTS: returns the name of the current stamp as an int
    public String getStampName() {
        return timeLog.get(timeLog.size() - 1).getName();
    }

    //REQUIRES: timeLog has a stamp
    //EFFECT: returns the duration of the current stamp as an int
    public int getStampDuration() {
        return timeLog.get(timeLog.size() - 1).getDuration();
    }

    //REQUIRES: timeLog has a stamp
    //EFFECT: returns the day value of the stamp
    public String getStampStart() {
        return timeLog.get(timeLog.size() - 1).getDay().toString();
    }

    //MODIFIES: timeLog.csv
    //EFFECTS: saves lines to csv file
    @Override
    public void saveData(ArrayList l) throws Exception {
        PrintWriter out = new PrintWriter(new FileOutputStream(file));
        for (Timestamp e: timeLog) {
            out.println(e.toString());
        }
        out.close();
    }

    //MODIFIES:
    public ArrayList loadData() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        if (file.exists()) {
            Scanner in = new Scanner(new FileInputStream(file));
            while (in.hasNext()) {
                list.add(in.nextLine());
            }
            in.close();
        }

        System.out.println("Loaded Data:");
        for (String s: list) {
            System.out.println(s);
        }
        return list;
    }
}

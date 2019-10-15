package utilities;


import model.Activity;
import model.IntervalTooSmallException;
import model.Rest;
import model.Timestamp;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TimeLogger extends Thread implements Saveable, Loadable {

    // FIELDS
    private int updateInterval = 999;
    private ArrayList<Timestamp> timeLog;
    private transient File dir = new File("Data");
    private transient File file = new File(".\\" + dir.getPath() + "\\" + "timeLog.csv");

    // CONSTRUCTOR
    public TimeLogger() throws Exception {
        timeLog = new ArrayList<>();
        loadData();
        newRest(new Rest("Rest"));
    }

    // METHODS

    //EFFECTS: returns update interval if it is 1000 or more, else throws an exception
    public int getUpdateInterval() throws IntervalTooSmallException {
        if (updateInterval >= 1000) {
            return updateInterval;
        } else {
            throw new IntervalTooSmallException("updateInterval " + updateInterval + " is less than 1000.");
        }
    }

    //REQUIRES: i is a positive integer greater than 1000
    //MODIFIES: this
    public void setUpdateInterval(int i) {
        updateInterval = i;
    }

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
    public void saveData(ArrayList l) {
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(file));
            for (Timestamp e : timeLog) {
                out.println(e.toString());
            }
            out.close();
        } catch (FileNotFoundException fnf) {
            if (!dir.exists()) {
                System.out.println("Creating directory " + dir.getPath());
                dir.mkdir();
                saveData(l);
            }
        }
    }

    //MODIFIES:
    public ArrayList loadData() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Scanner in = new Scanner(new FileInputStream(file));
            while (in.hasNext()) {
                list.add(in.nextLine());
            }
            in.close();
            System.out.println("Loaded Data:");
            for (String s: list) {
                System.out.println(s);
            }
            return list;
        } catch (FileNotFoundException fnf) {
            System.out.println("File " + file + " does not exist.");
        } finally {
            return list;
        }
    }
}

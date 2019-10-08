package model;

import java.io.*;
import java.util.Date;

public abstract class Timestamp implements Serializable {

    //FIELDS
    private Date day;
    private long start;
    private long end;
    private int duration;

    //CONSTRUCTOR
    public Timestamp() {
        day = new Date();
        start = System.currentTimeMillis();
        end = System.currentTimeMillis();
        setDuration();
    }

    //METHODS

    //EFFECTS: returns the day value
    public Date getDay() {
        return day;
    }

    //REQUIRES: start is a positive integer representing milliseconds
    //EFFECTS: returns the start value
    private long getStart() {
        return start;
    }

    //REQUIRES: end is a positive integer representing milliseconds
    //EFFECTS: returns the end value
    private long getEnd() {
        return end;
    }

    //MODIFIES: this
    //EFFECTS: updates end field to current system time in milliseconds
    private void setEnd() {
        end = System.currentTimeMillis();
    }

    //MODIFIES: this
    //REQUIRES: start and end are positive integers representing milliseconds
    //EFFECTS: update duration to match elapsed time in seconds { (end - start) / 1000 }
    private void setDuration() {
        duration = (int)((getEnd() - getStart()) / 1000);
    }

    //EFFECTS: returns duration value
    public int getDuration() {
        return duration;
    }


    //MODIFIES: this
    //EFFECTS: updates end time of timestamp and calls elapsedTime
    public void update() {
        setEnd();
        setDuration();
    }

    //EFFECTS: produces a string representation of the class in csv line format
    public abstract String toString();

    //EFFECTS: return the name value, if applicable
    public abstract String getName();

    //EFFECT: return the type value, if applicable
    public abstract String getType();

}

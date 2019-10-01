package utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Timestamp implements Saveable, Loadable, Serializable {

    //FIELDS
    public int saveInterval = 30000;
    private String activityType;
    private String activityName;
    private Date localTime;
    private ArrayList<Timestamp> timeLog;
    private transient File df;
    private transient ObjectOutputStream out;
    private transient ObjectInputStream in;

    //CONSTRUCTOR
    public Timestamp(String t, String n) throws Exception {
        setActivityType(t);
        setActivityName(n);
        setLocalTime(new Date());
        df = new File(".\\data\\timestamps.data");
        if (df.isFile()) {
            System.out.println("df exists");
            timeLog = loadData();
        } else {
            timeLog = new ArrayList<>();
        }
    }

    //METHODS
    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Date getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Date localTime) {
        this.localTime = localTime;
    }

    public ArrayList<Timestamp> getTimeLog() {
        return timeLog;
    }

    public int getTimestampCount() {
        return timeLog.size();
    }

    public void logData() throws Exception {
        //timeLog.add(new Timestamp(getActivityType(), getActivityName(), false));
        timeLog.add(this);
        saveData(timeLog);
    }

    public void saveData(ArrayList l) throws Exception {
        out = new ObjectOutputStream(new FileOutputStream(df));
        out.writeObject(l);
        out.close();
    }

    public ArrayList loadData() throws Exception {
        in = new ObjectInputStream(new FileInputStream(df));
        timeLog = (ArrayList<Timestamp>) (in.readObject());
        System.out.println("timelog size is " + timeLog.size());
        in.close();
        return timeLog;
    }


}

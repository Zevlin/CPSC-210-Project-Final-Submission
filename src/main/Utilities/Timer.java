package utilities;

public class Timer extends Thread {
    // Tracks the current activity and calls logs the results periodically

    // VARIABLES
    private String activityName;    // name of actual activity. ex work, study, game, etc.
    private String activityType;  // type of activity (the broader category). ex productivity, leisure, etc.
    private long startSysTime;  // system time at start of recording (milliseconds)
    private long endSysTime;    // system time at end of recording (milliseconds)
    private int elapsedTime;    // elapsed time after recording (seconds)
    private boolean isRecording;    // tracks whether timer should be recording or not

    // CONSTRUCTOR
    public Timer() {
        setActivityName("other");
        setActivityType("other");
        setStartSysTime();
        setEndSysTime();
        setElapsedTime();
    }

    // METHODS
    public void setIsRecording(boolean b) {
        System.out.println("set isRecording method");
        isRecording = b;
    }

    public boolean getIsRecording() {
        System.out.println("get isRecording method");
        return isRecording;
    }

    public void setActivityName(String s) {
        System.out.println("set activity name method");
        activityName = s;

    }

    public String getActivityName() {
        System.out.println("get activity name method");
        return activityName;
    }

    public void setActivityType(String s) {
        System.out.println("set activity type method");
        activityType = s;
    }

    public String getActivityType() {
        System.out.println("get activity type method");
        return activityType;
    }

    public void setStartSysTime() {
        System.out.println("set start sys time method");
        startSysTime = System.currentTimeMillis();
    }

    public long getStartSysTime() {
        System.out.println("get start sys time method");
        return startSysTime;
    }

    public void setEndSysTime() {
        System.out.println("set end sys time method");
        endSysTime = System.currentTimeMillis();
    }

    public long getEndSysTime() {
        System.out.println("get end sys time method");
        return endSysTime;
    }

    public void setElapsedTime() {
        System.out.println("set elapsed time method");
        elapsedTime = (int) ((getEndSysTime() - getStartSysTime()) / 1000.0);
    }

    public int getElapsedTime() {
        System.out.println("get elapsed time method");
        return elapsedTime;
    }

}

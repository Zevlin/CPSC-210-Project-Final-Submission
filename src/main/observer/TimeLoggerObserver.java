package observer;

import java.util.Observable;
import java.util.Observer;

public class TimeLoggerObserver implements Observer {
    private int newTaskCount;

    @Override
    public void update(Observable o, Object arg) {
        newTaskCount++;
        if (newTaskCount % 5 == 0) {
            System.out.println("Great job! You've updated your task " + newTaskCount + " times this session!");
        }
    }
}

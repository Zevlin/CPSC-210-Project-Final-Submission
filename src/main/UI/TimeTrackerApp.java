package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utilities.TimeLogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import model.Activity;
import model.Rest;

public class TimeTrackerApp extends Application implements EventHandler<ActionEvent> {

    // FIELDS
    private Button workBtn;
    private Button restBtn;
    private TimeLogger timeLogger;
    private Timer timer;
    private TimestampUpdater updater;
    private Text dateText;
    private Text startText;
    private Text durationText;
    private Text newTaskTxt;
    private Text dateUnderlineTxt;
    private Text currentActivityTxt;

    // PRIMARY METHODS
    public static void main(String[] args) {
        System.out.println("main method");
        launch(args);
    } // end of main()

    // HELPER METHODS
    // METHODS
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.println("start method");
            timeLogger = new TimeLogger();
            initializeGUI();
            sortGUI(primaryStage);
            updater = new TimestampUpdater();
            timer = new Timer(true);
            timer.scheduleAtFixedRate(updater, 0, timeLogger.getUpdateInterval());
        } catch (IllegalArgumentException iae) {
            timer.scheduleAtFixedRate(updater, 0, 1000);
        }
    } // End of start()

    @Override
    public void handle(ActionEvent event) {
        System.out.println("handle method");

        if (event.getSource() == workBtn) {
            timeLogger.newActivity(new Activity("Study", "Work"));
        } else if (event.getSource() == restBtn) {
            timeLogger.newRest(new Rest("Rest"));
        }
    }  // End of handle()


    // HELPER METHODS

    //MODIFIES: this
    //EFFECTS: forms the initial layout of the GUI
    public void initializeGUI() {
        System.out.println("form GUI method");

        // current activity elements
        currentActivityTxt = new Text("-- Current Activity --");
        startText = new Text("Start: --/--");
        durationText = new Text("Duration: 00:00:00");

        // new task elements
        newTaskTxt = new Text("-------  Start New Task  -------");
        workBtn = new Button("work");
        workBtn.setOnAction(this);
        restBtn = new Button("rest");
        restBtn.setOnAction(this);

        // activity log
        dateText = new Text(new SimpleDateFormat("dd/mm/yyyy").format(new Date()));
        dateUnderlineTxt = new Text("-----------------");
    }

    // MODIFIES: this
    // EFFECTS: creates layout pattern for GUI and places initialized elements into it, then calls loadGUI()
    public void sortGUI(Stage primaryStage) {
        // current activity VBox
        VBox currentActivityBox = new VBox();
        currentActivityBox.setSpacing(10);
        currentActivityBox.getChildren().addAll(currentActivityTxt, startText, durationText);

        // start new task VBox
        VBox newTaskBox = new VBox();
        newTaskBox.setSpacing(10);
        newTaskBox.getChildren().addAll(newTaskTxt, workBtn, restBtn);

        // time log VBox
        VBox timeLogBox = new VBox();
        timeLogBox.getChildren().addAll(dateText, dateUnderlineTxt);

        // wrapper boxes
        VBox rightSideBox = new VBox();
        rightSideBox.getChildren().addAll(timeLogBox);

        VBox leftSideBox = new VBox();
        leftSideBox.setSpacing(30);
        leftSideBox.getChildren().addAll(currentActivityBox, newTaskBox);

        HBox wrapperBox = new HBox();
        wrapperBox.getChildren().addAll(leftSideBox, rightSideBox);

        loadGUI(primaryStage, wrapperBox);
    }

    // MODIFIES: this
    // EFFECTS: displays a window with the GUI inside of it
    private void loadGUI(Stage primaryStage, HBox wrapperBox) {
        primaryStage.setTitle("Time Tracker App");  // set the window title
        Scene scene = new Scene(wrapperBox, 500, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //
    private void updateGUI() {
        currentActivityTxt.setText("-- " + timeLogger.getStampName() + " --");
        startText.setText("Start: " + timeLogger.getStampStart());
        durationText.setText("Duration: " + formatTime(timeLogger.getStampDuration()));

    }

    // TimerTask Class
    private class TimestampUpdater extends TimerTask {

        @Override
        public void run() {
            try {
                System.out.println("updating GUI");
                timeLogger.updateStamp();
                updateGUI();
            } catch (Exception e) {
                System.out.println("error updating GPU with exception " + e.getMessage());
            }
        }
    }

    private String formatTime(int n) {
        String timerTextStr = "";
        //Set hours if hrs are 10 or over
        if (n / 3600 >= 10) {
            timerTextStr += n / 3600 + ":";
        } else {
            timerTextStr += "0" + n / 3600 + ":";
        }
        n -= n / 3600 * 3600;   // get rid of the hours we already processed
        //Set minutes if mins are 10 or over
        if (n / 60 >= 10) {
            timerTextStr += n / 60 + ":";
        } else {
            timerTextStr += "0" + n / 60 + ":";
        }
        //n -= n / 60 * 60;   // get rid of the minutes we already processed
        //Set hours if hrs are 10 or over
        if (n - (n / 60 * 60) >= 10) {
            timerTextStr += n - (n / 60 * 60);
        } else {
            timerTextStr += "0" + (n - (n / 60 * 60));
        }
        return timerTextStr;
    }   // End of formatTime()

}

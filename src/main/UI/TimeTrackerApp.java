package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import utilities.TimeLogger;

public class TimeTrackerApp extends Application implements EventHandler<ActionEvent> {

    // OBJECTS
    private Button workBtn;
    private Button restBtn;
    private TimeLogger timeLogger;
    private Text dateText;
    private Text startText;
    private Text durationText;
    private Text newTaskTxt;
    private Text dateUnderlineTxt;
    private Text currentActivityTxt;

    // PRIMARY METHODS
    public static void main(String[] args) {
        System.out.println("main method");
        launch(args);   // Set up JavaFX and call start()
    } // end of main()

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("start method");
        timeLogger = new TimeLogger();
        initializeGUI();
        sortGUI(primaryStage);
//        timeText = new Text("00:00:00"); // used elapsed time as timer text (hh:mm:ss)
//        timestampCount = new Text("Timestamp Count: " + timestamp.getTimestampCount());
//        primaryStage.setTitle("Time Tracker App");  // set the window title
//        recordBtn = new Button("Record");
//        recordBtn.setOnAction(this);    // look for handle() in this class
//
//        StackPane layout = new StackPane();
//        layout.getChildren().add(recordBtn);
//        layout.getChildren().add(timeText);
//        layout.getChildren().add(timestampCount);
//
//        Scene scene = new Scene(layout, 500, 200);
//        primaryStage.setScene(scene);
//        timeText.setTranslateY(50);
//        timestampCount.setTranslateY(90);
//        primaryStage.show();
    } // End of start()

    @Override
    public void handle(ActionEvent event) {
        System.out.println("handle method");

    }  // End of handle()


    // HELPER METHODS

    //MODIFIES: this
    //EFFECTS: forms the initial layout of the GUI
    public void initializeGUI() {
        System.out.println("form GUI method");

        // current activity elements
        currentActivityTxt = new Text("-- Current Activity --");
        startText = new Text("Start: --/--");
        durationText = new Text("Duration: 00h 00m 00s");

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

    }

//    //REQUIRES: timer, recordBtn objects is not null
//    //MODIFIES: timer object.
//    private void startRecording() {
//        timer.setIsRecording(true);
//        System.out.println("Start recording time");
//        Thread recordThread = new Thread() {
//            public void run() {
//                timer.setStartSysTime();
//                while (timer.getIsRecording()) {
//                    manageTimestamp(timer.getElapsedTime());
//                    timer.setEndSysTime();
//                    timer.setElapsedTime();
//                    timeText.setText(formatTime(timer.getElapsedTime()));
//                }
//            }
//        };
//        recordThread.setDaemon(true);
//        recordBtn.setText("Stop");
//        recordThread.start();
//    }
//
//    private void stopRecording() {
//        timer.setIsRecording(false);
//        System.out.println("Stop recording time");
//        recordBtn.setText("Record");
//    }   // End of stopRecording()
//
//    private String formatTime(int n) {
//        String timerTextStr = "";
//        //Set hours if hrs are 10 or over
//        if (n / 3600 >= 10) {
//            timerTextStr += n / 3600 + ":";
//        } else {
//            timerTextStr += "0" + n / 3600 + ":";
//        }
//        n -= n / 3600 * 3600;   // get rid of the hours we already processed
//        //Set minutes if mins are 10 or over
//        if (n / 60 >= 10) {
//            timerTextStr += n / 60 + ":";
//        } else {
//            timerTextStr += "0" + n / 60 + ":";
//        }
//        //n -= n / 60 * 60;   // get rid of the minutes we already processed
//        //Set hours if hrs are 10 or over
//        if (n - (n / 60 * 60) >= 10) {
//            timerTextStr += n - (n / 60 * 60);
//        } else {
//            timerTextStr += "0" + (n - (n / 60 * 60));
//        }
//        return timerTextStr;
//    }   // End of formatTime()
//
//    private void manageTimestamp(long elapsedTime) {
//        // check interval for saving
//        timestampCount.setText("Timestamp Count: " + timestamp.getTimestampCount());
//        if (elapsedTime % timestamp.saveInterval == 0) {
//            try {
//                timestamp.setActivityType(timer.getActivityType());
//                timestamp.setActivityName(timer.getActivityName());
//                timestamp.setLocalTime(new Date());
//                timestamp.logData();
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }
}

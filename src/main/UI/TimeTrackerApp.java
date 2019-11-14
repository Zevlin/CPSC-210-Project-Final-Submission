package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import network.ReadWebPage;
import utilities.TimeLogger;

import java.text.SimpleDateFormat;
import java.util.*;

import model.Activity;
import model.Rest;


public class TimeTrackerApp extends Application implements EventHandler<ActionEvent> {

    // FIELDS
    private static final int WWIDTH = 896;
    private static final int WHEIGHT = 560;
    ImageView iv;
    private Button closeBtn;
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
            new ReadWebPage().rwp();
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
        } else if (event.getSource() == closeBtn) {
            System.exit(0);
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
        HBox topBar = makeTopBar();

        HBox bodyWrapper = makeBodyWrapper(topBar);

        VBox innerWrapperBox = new VBox();
        innerWrapperBox.setMinHeight(WHEIGHT);
        innerWrapperBox.setMinWidth(WWIDTH);
        innerWrapperBox.getChildren().addAll(topBar, bodyWrapper);

        HBox wrapperBox = new HBox();
        wrapperBox.getChildren().add(innerWrapperBox);
        wrapperBox.minWidth(WWIDTH);
        wrapperBox.minHeight(WHEIGHT);
        wrapperBox.setPadding(new Insets(0,0,0,0));
        loadGUI(primaryStage, wrapperBox);
    }

    private HBox makeBodyWrapper(HBox topBar) {
        HBox bodyWrapper = new HBox();
        bodyWrapper.setMinHeight(WHEIGHT - topBar.getHeight());
        bodyWrapper.setMaxHeight(WHEIGHT - topBar.getHeight());
        bodyWrapper.setMinWidth(WWIDTH);
        bodyWrapper.setMaxWidth(WWIDTH);
        bodyWrapper.setStyle("-fx-background-color: rgba(46, 45, 71, 1.0);");

        VBox sideBar = makeSideBar(bodyWrapper);

        bodyWrapper.getChildren().addAll(sideBar);
        return bodyWrapper;
    }

    private VBox makeSideBar(HBox bodyWrapper) {
        VBox sideBar = new VBox();
        sideBar.setMinWidth(200);
        sideBar.setMaxWidth(200);
        sideBar.setMinHeight(bodyWrapper.getHeight());
        sideBar.setStyle("-fx-background-color: rgba(60, 56, 128, 1.0);");
        return sideBar;
    }

    private HBox makeTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(0, 30, 0, 30));
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPrefWidth(WWIDTH);
        topBar.setMinHeight(70);
        topBar.setMaxHeight(70);
        String topBarCSS = "-fx-background-color: rgba(67, 63, 145, 1.0);";
        topBar.setStyle(topBarCSS);
        makeCloseBtn();
        topBar.getChildren().add(closeBtn);
        return topBar;
    }

    private void makeCloseBtn() {
        closeBtn = new Button();
        closeBtn.setOnAction(this);
        closeBtn.setMinWidth(20);
        closeBtn.setMaxWidth(20);
        closeBtn.setMinHeight(20);
        closeBtn.setMaxHeight(20);
        closeBtn.setStyle("-fx-border-color: transparent;"
                + "-fx-border-width: 0;"
                + "-fx-background-radius: 0;"
                + "-fx-background-color: transparent;"
                + "-fx-background-color: rgba(67, 63, 145, 1.0);"
                + "-fx-background-position: center center;"
                + "-fx-background-repeat: no-repeat;"
                + "-fx-background-image: url(\"images/close.png\");");
    }

    // MODIFIES: this
    // EFFECTS: displays a window with the GUI inside of it
    private void loadGUI(Stage primaryStage, HBox wrapperBox) {
        primaryStage.setTitle("Time Tracker App");  // set the window title
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(wrapperBox, WWIDTH, WHEIGHT);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //
    private void updateGUI() {
        currentActivityTxt.setText("-- " + timeLogger.getStampName() + " --");
        startText.setText("Start: " + timeLogger.getStampStart());
        durationText.setText("Duration: " + formatTime(timeLogger.getStampDuration()));

    }

////////////////////////////////////////////////////////////////////////////////////////////////////

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

////////////////////////////////////////////////////////////////////////////////////////////////////

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

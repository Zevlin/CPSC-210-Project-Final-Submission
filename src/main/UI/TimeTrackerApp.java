package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    private static final double TRANSPARENCY = 1.0;
    private int topBarHeight = 55;
    private int sideBarWidth = 195;
    ImageView iv;
    private int taskBtnWidth = 40;
    private int taskBtnHeight = 30;
    private Button closeBtn;
    private Button workBtn;
    private Button travelBtn;
    private Button eatBtn;
    private Button hobbyBtn;
    private Button shopBtn;
    private Button studyBtn;
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
    }

    // MODIFIES: this
    // EFFECTS: creates layout pattern for GUI and places initialized elements into it, then calls loadGUI()
    public void sortGUI(Stage primaryStage) {
        HBox topBar = makeTopBar();

        HBox bodyWrapper = makeBodyWrapper(topBar);

        VBox innerWrapperBox = new VBox();
        innerWrapperBox.setStyle("-fx-background-color: transparent;");
        innerWrapperBox.setMinHeight(WHEIGHT);
        innerWrapperBox.setMinWidth(WWIDTH);
        innerWrapperBox.getChildren().addAll(topBar, bodyWrapper);

        HBox wrapperBox = new HBox();
        wrapperBox.setStyle("-fx-background-color: transparent;");
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
        bodyWrapper.setStyle("-fx-background-image: url(\"images/bg.png\");");

        VBox sideBar = makeSideBar(bodyWrapper);

        bodyWrapper.getChildren().addAll(sideBar);
        return bodyWrapper;
    }

    private VBox makeSideBar(HBox bodyWrapper) {
        HBox greeting = new HBox();
        greeting.setMinWidth(45);
        greeting.setMaxWidth(45);
        greeting.setMinHeight(35);
        greeting.setMaxHeight(35);
        greeting.setStyle("-fx-background-image: url(\"images/hello.png\");"
                + "-fx-background-position: center;"
                + "-fx-background-repeat: no-repeat;");

        Text hello = new Text("hello  ");
        hello.setFill(Paint.valueOf("white"));

        Text name = new Text("Dhaher");
        name.setTextAlignment(TextAlignment.LEFT);
        name.setFill(Paint.valueOf("white"));
        name.setWrappingWidth(80);

        HBox greetingWrapper = new HBox();
        greetingWrapper.setAlignment(Pos.CENTER);
        greetingWrapper.setMinWidth(sideBarWidth);
        greetingWrapper.setMaxWidth(sideBarWidth);
        greetingWrapper.setMinHeight(70);
        greetingWrapper.setMaxHeight(70);
        greetingWrapper.getChildren().addAll(greeting, hello, name);

        Text tasks = new Text("tasks");
        tasks.setFill(Paint.valueOf("white"));
        VBox tasksTitle = new VBox();
        tasksTitle.setAlignment(Pos.CENTER);
        tasksTitle.setMinWidth(sideBarWidth);
        tasksTitle.setMaxWidth(sideBarWidth);
        tasksTitle.setMinHeight(40);
        tasksTitle.setMaxHeight(40);
        tasksTitle.setStyle("-fx-background-image: url(\"images/tasks_noText.png\");"
                + "-fx-background-position: center;"
                + "-fx-background-repeat: no-repeat;");
        tasksTitle.getChildren().add(tasks);

        workBtn = makeTaskButton("work");
        studyBtn = makeTaskButton("study");
        travelBtn = makeTaskButton("travel");
        eatBtn = makeTaskButton("eat");
        shopBtn = makeTaskButton("shop");
        hobbyBtn = makeTaskButton("hobby");
        restBtn = makeTaskButton("rest");

        HBox taskRow1 = new HBox();
        taskRow1.setPadding(new Insets(0,0,0, 15));
        taskRow1.setAlignment(Pos.CENTER_LEFT);
        taskRow1.setMinWidth(sideBarWidth);
        taskRow1.setMaxWidth(sideBarWidth);
        taskRow1.setMinHeight(36);
        taskRow1.setMaxHeight(36);
        taskRow1.getChildren().addAll(workBtn, studyBtn, travelBtn, eatBtn);

        HBox taskRow2 = new HBox();
        taskRow2.setPadding(new Insets(0,0,0, 15));
        taskRow2.setAlignment(Pos.CENTER_LEFT);
        taskRow2.setMinWidth(sideBarWidth);
        taskRow2.setMaxWidth(sideBarWidth);
        taskRow2.setMinHeight(36);
        taskRow2.setMaxHeight(36);
        taskRow2.getChildren().addAll(shopBtn, hobbyBtn, restBtn);

        VBox tasksWrapper = new VBox();
        tasksWrapper.setAlignment(Pos.CENTER);
        tasksWrapper.setMinWidth(sideBarWidth);
        tasksWrapper.setMaxWidth(sideBarWidth);
        tasksWrapper.setMinHeight(75);
        tasksWrapper.setMaxHeight(75);
        tasksWrapper.setStyle("-fx-background-color: rgba(53, 49, 115, " + TRANSPARENCY + ");");
        tasksWrapper.getChildren().addAll(taskRow1, taskRow2);

        VBox sideBar = new VBox();
        sideBar.setAlignment(Pos.TOP_CENTER);
        sideBar.setMinWidth(sideBarWidth);
        sideBar.setMaxWidth(sideBarWidth);
        sideBar.setMinHeight(bodyWrapper.getHeight());
        sideBar.setStyle("-fx-background-color: rgba(60, 56, 128, " + TRANSPARENCY + ");");
        sideBar.getChildren().addAll(greetingWrapper, tasksTitle, tasksWrapper);
        return sideBar;
    }

    private Button makeTaskButton(String name) {
        Button temp = new Button();
        temp.setMinWidth(taskBtnWidth);
        temp.setMaxWidth(taskBtnWidth);
        temp.setMinHeight(taskBtnHeight);
        temp.setMaxHeight(taskBtnHeight);
        temp.setStyle("-fx-background-image: url(\"images/" + name + "_active.png\");"
                + "-fx-border-color: transparent;"
                + "-fx-border-width: 0;"
                + "-fx-background-radius: 0;"
                + "-fx-background-color: transparent;"
                + "-fx-background-position: center center;"
                + "-fx-background-repeat: no-repeat;");
        temp.setOnAction(this);
        return temp;
    }

    private HBox makeTopBar() {
        makeCloseBtn();

        VBox logo = new VBox();
        logo.setMinHeight(31);
        logo.setMaxHeight(31);
        logo.setMinWidth(182);
        logo.setMaxWidth(182);
        logo.setStyle("-fx-background-image: url(\"images/logo.png\");" + "-fx-background-repeat: no-repeat;");

        VBox logoBuffer = new VBox();
        logoBuffer.setMinWidth(650);

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(0, 30, 0, 30));
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPrefWidth(WWIDTH);
        topBar.setMinHeight(55);
        topBar.setMaxHeight(55);
        topBar.setStyle("-fx-background-color: rgba(67, 63, 145, " + TRANSPARENCY + ");");
        topBar.getChildren().addAll(logo, logoBuffer, closeBtn);
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

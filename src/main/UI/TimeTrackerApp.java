package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import utilities.Timer;

public class TimeTrackerApp extends Application implements EventHandler<ActionEvent> {

    // OBJECTS
    private Button recordBtn;
    private Timer timer;
    private Text timeText;

    // PRIMARY METHODS
    public static void main(String[] args) {
        System.out.println("main method");
        launch(args);   // Set up JavaFX and call start()
    } // end of main()

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("start method");
        timer = new Timer();
        timeText = new Text("00:00:00"); // used elapsed time as timer text (hh:mm:ss)
        primaryStage.setTitle("Time Tracker App");  // set the window title
        recordBtn = new Button("Record");
        recordBtn.setOnAction(this);    // look for handle() in this class

        StackPane layout = new StackPane();
        layout.getChildren().add(recordBtn);
        layout.getChildren().add(timeText);

        Scene scene = new Scene(layout, 500, 200);
        primaryStage.setScene(scene);
        timeText.setTranslateY(50);
        primaryStage.show();
    } // End of start()

    @Override
    public void handle(ActionEvent event) {
        System.out.println("handle method");

        if (event.getSource() == recordBtn) {
            if (!timer.getIsRecording()) {
                startRecording();
            } else {
                stopRecording();
            }
        }
    }  // End of handle()


    // HELPER METHODS
    private void startRecording() {
        timer.setIsRecording(true);
        System.out.println("Start recording time");
        Thread recordThread = new Thread() {
            public void run() {
                timer.setStartSysTime();
                while (timer.getIsRecording()) {
                    timer.setEndSysTime();
                    timer.setElapsedTime();
                    timeText.setText(formatTime(timer.getElapsedTime()));
                }
            }
        };
        recordThread.setDaemon(true);
        recordBtn.setText("Stop");
        recordThread.start();
    }

    private void stopRecording() {
        timer.setIsRecording(false);
        System.out.println("Stop recording time");
        recordBtn.setText("Record");
    }   // End of stopRecording()

    public String formatTime(int n) {
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

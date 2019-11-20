package ui;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainFrame {

    // FIELDS
    private static final int FWIDTH = 1280;
    private static final int FHEIGHT = 800;
    private int topBarHeight = 80;
    private int sideBarWidth = 275;
    private int taskBtnWidth = 45;
    private int taskBtnHeight = 45;
    private int taskBtnGap = 5;
    private JFrame window;
    private JPanel topBar;
    private JPanel sideBar;
    private int topBarAlpha = 255;
    private int sideBarAlpha = 255;
    private String profileName = "Dhaher";

    private JButton closeBtn;
    private JButton workBtn = new JButton();
    private JButton studyBtn = new JButton();
    private JButton travelBtn = new JButton();
    private JButton eatBtn = new JButton();
    private JButton shopBtn = new JButton();
    private JButton hobbyBtn = new JButton();
    private JButton restBtn = new JButton();

    // PRIMARY METHODS
    public MainFrame() throws IOException {
        formWindow();
        formTopBar();
        formSideBar();

        window.setVisible(true);
    }

    // HELPER METHODS

    // Creates JFrame and adds background image in form of JLabel
    private void formWindow() throws IOException {
        window = new JFrame("Time Tracker");
        window.setSize(FWIDTH, FHEIGHT);
        window.setUndecorated(true);
        window.setResizable(false);
        window.setLayout(null);
//        File f = new File("src/main/images/bg.png");
//        System.out.println(f.getCanonicalPath());
        ImageIcon img = new ImageIcon("src/main/images/bg.png");
        JLabel bg = new JLabel("", img, JLabel.CENTER);
        bg.setSize(FWIDTH, FHEIGHT);
        window.setContentPane(bg);
    }

    private void formTopBar() {
        topBar = new JPanel();
        topBar.setBorder(new EmptyBorder(12,35,10,10));
        topBar.setBackground(new Color(44, 50, 115, topBarAlpha));
        topBar.setSize(FWIDTH, topBarHeight);

        ImageIcon logoImg = new ImageIcon("src/main/images/logo.png");
        JLabel logo = new JLabel("", logoImg, JLabel.CENTER);
        topBar.add(logo);

        topBar.add(Box.createRigidArea(new Dimension(900, 0)));

        ImageIcon closeImg = new ImageIcon("src/main/images/close.png");
        closeBtn = new JButton();
        closeBtn.setSize(22, 22);
        closeBtn.setOpaque(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setIcon(closeImg);
        topBar.add(closeBtn);

        window.add(topBar);
    }

    private void formSideBar() {
        sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setSize(sideBarWidth, FHEIGHT);
        sideBar.setBackground(new Color(41, 45, 95, sideBarAlpha));

        sideBar.add(Box.createRigidArea(new Dimension(0, 120)));

        formSideBarName();

        sideBar.add(Box.createRigidArea(new Dimension(0, 25)));

        formSideBarTasks();

        sideBar.add(Box.createRigidArea(new Dimension(0, FHEIGHT)));

        window.add(sideBar);
    }

    private void formSideBarName() {
        ImageIcon hello = new ImageIcon("src/main/images/hello.png");
        JLabel name = new JLabel("<html><p>hello   <b>" + profileName + "</b></p></html>", hello, JLabel.LEFT);
        name.setIconTextGap(15);
        name.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        name.setForeground(new Color(255, 255, 255, 255));
        name.setSize(sideBarWidth, 50);
        JPanel nameWrapper = new JPanel();
        nameWrapper.setSize(sideBarWidth, 50);
        nameWrapper.setBackground(new Color(39, 43, 85, sideBarAlpha));
        nameWrapper.add(name);
        nameWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
        nameWrapper.setBorder(new EmptyBorder(0,25,0,0));
        sideBar.add(nameWrapper);
    }

    private void formSideBarTasks() {
        JPanel tasksWrapper = new JPanel();
        tasksWrapper.setSize(sideBarWidth, 160);
        tasksWrapper.setOpaque(false);

        ImageIcon tasksTitleBG = new ImageIcon("src/main/images/tasks.png");
        JLabel tasksTitle = new JLabel("", tasksTitleBG, JLabel.CENTER);
        tasksTitle.setSize(sideBarWidth, 20);
        tasksWrapper.add(tasksTitle);
        tasksWrapper.add(formSideBarTasksList());

        sideBar.add(tasksWrapper);
    }

    private JPanel formSideBarTasksList() {
        JPanel tasksList = new JPanel();
        tasksList.setPreferredSize(new Dimension(sideBarWidth, 115));
        tasksList.setLayout(new BoxLayout(tasksList, BoxLayout.Y_AXIS));
        tasksList.setBackground(new Color(39, 43, 85, sideBarAlpha));

        JPanel taskRow1 = new JPanel();
        taskRow1.setSize(sideBarWidth, 50);
        taskRow1.setOpaque(false);
        workBtn.setName("work");
        studyBtn.setName("study");
        travelBtn.setName("travel");
        eatBtn.setName("eat");
        formatTaskButton(workBtn, false);
        formatTaskButton(studyBtn, false);
        formatTaskButton(travelBtn, false);
        formatTaskButton(eatBtn, false);
        taskRow1.add(workBtn);
        taskRow1.add(Box.createRigidArea(new Dimension(taskBtnGap, 0)));
        taskRow1.add(studyBtn);
        taskRow1.add(Box.createRigidArea(new Dimension(taskBtnGap, 0)));
        taskRow1.add(travelBtn);
        taskRow1.add(Box.createRigidArea(new Dimension(taskBtnGap, 0)));
        taskRow1.add(eatBtn);
        tasksList.add(taskRow1);

        JPanel taskRow2 = new JPanel();
        taskRow2.setSize(sideBarWidth, 50);
        taskRow2.setOpaque(false);
        shopBtn.setName("shop");
        hobbyBtn.setName("hobby");
        restBtn.setName("rest");
        formatTaskButton(shopBtn, false);
        formatTaskButton(hobbyBtn, false);
        formatTaskButton(restBtn, true);
        taskRow2.add(shopBtn);
        taskRow2.add(Box.createRigidArea(new Dimension(taskBtnGap, 0)));
        taskRow2.add(hobbyBtn);
        taskRow2.add(Box.createRigidArea(new Dimension(taskBtnGap, 0)));
        taskRow2.add(restBtn);
        tasksList.add(taskRow2);

        tasksList.add(Box.createRigidArea(new Dimension(0, 10)));

        return tasksList;
    }

    private void formatTaskButton(JButton btn, Boolean active) {
        ImageIcon img;
        btn.setForeground(new Color(0,0,0,0));
        btn.setPreferredSize(new Dimension(taskBtnWidth, taskBtnHeight));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        if (active) {
            img = new ImageIcon("src/main/images/" + btn.getName() + "_active.png");
        } else {
            img = new ImageIcon("src/main/images/" + btn.getName() + "_inactive.png");
        }
        btn.setIcon(img);
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

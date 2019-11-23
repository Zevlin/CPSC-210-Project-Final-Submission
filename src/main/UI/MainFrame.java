package ui;


import model.Activity;
import model.Rest;
import model.TimeStamp;
import utilities.TimeLogger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainFrame {

    // FIELDS
    private static final int FWIDTH = 1280;
    private static final int FHEIGHT = 800;
    private JPanel namescreen;
    private int nameScreenWidth = FWIDTH;
    private int nameScreenHeight = FHEIGHT;
    private int topBarHeight = 80;
    private int sideBarWidth = 275;
    private int bodyWidth = 1000;
    private int bodyHeight = 700;
    private int taskBtnWidth = 45;
    private int taskBtnHeight = 45;
    private int taskBtnGap = 5;
    private JFrame window;
    private JPanel topBar;
    private JPanel sideBar;
    private JPanel body;
    private int topBarAlpha = 255;
    private int sideBarAlpha = 255;
    private String profileName = "";
    private int totTime = 0;
    private int sidestatsTotalTimeLogged = 1;
    private int sidestatsAverageTaskLength = 0;

    private JButton closeBtn;
    private JButton workBtn = new JButton();
    private JButton studyBtn = new JButton();
    private JButton travelBtn = new JButton();
    private JButton eatBtn = new JButton();
    private JButton shopBtn = new JButton();
    private JButton hobbyBtn = new JButton();
    private JButton restBtn = new JButton();
    private JButton activeBtn;
    private JLabel tot;
    private JLabel totalTimeLogged;
    private JLabel averageTaskLength;
    private JTextField nameField;
    private JLabel name;
    private Pie basePie;
    private Pie donutPie;
    private Pie workPie;
    private Pie studyPie;
    private Pie travelPie;
    private Pie eatPie;
    private Pie shopPie;
    private Pie hobbyPie;
    private Pie restPie;
    private JLabel piechart;
    private int workSum = 0;
    private int studySum = 0;
    private int travelSum = 0;
    private int eatSum = 0;
    private int shopSum = 0;
    private int hobbySum = 0;
    private int restSum = 0;
    private double nameShrinkRate = 0.15;

    private TimeLogger timeLogger;

    // PRIMARY METHODS
    public MainFrame(TimeLogger timeLogger) throws Exception {
        this.timeLogger = timeLogger;
        formWindow();
        formTopBar();
        formNameScreen();
        formBody();
        formSideBar();

        window.setVisible(true);
    }

    // REQUIRES: TimeLogger is valid and contains LogList.
    // MODIFIES: this
    // EFFECTS: Retrieves data from timeLogger's log list and calculates values related to time log, then feeds those
    //      values into the appropriate GUI nodes and fields to update the app visually.
    public void update() {
        totTime = timeLogger.getStampDuration();
        tot.setText(formatTime(totTime));
        int tempTTL = 0;
        int taskDurationSum = 0;
        int taskCount = 0;
        for (TimeStamp s : timeLogger.getLogList()) {
            tempTTL += s.getDuration();
            taskDurationSum += s.getDuration();
            taskCount++;
        }
        totalTimeLogged.setText(formatTime(tempTTL));
        sidestatsTotalTimeLogged = tempTTL;
        averageTaskLength.setText(formatTime(taskDurationSum / taskCount));
        sidestatsAverageTaskLength = taskDurationSum / taskCount;
        calculateTaskSums();
        updatePieValues();
        resetTaskSums();
    }

    // HELPER METHODS

    // Creates JFrame and adds background image in form of JLabel
    private void formWindow() throws IOException {
        window = new JFrame("Time Tracker");
        window.setSize(FWIDTH, FHEIGHT);
        window.setBounds(0,0, FWIDTH, FHEIGHT);
        window.setUndecorated(true);
        window.setResizable(false);
        window.setLayout(null);
        window.setLocationRelativeTo(null);
//        File f = new File("src/main/images/bg.png");
//        System.out.println(f.getCanonicalPath());
        ImageIcon img = new ImageIcon("src/main/images/bg.png");
        JLabel bg = new JLabel("", img, JLabel.CENTER);
        bg.setSize(FWIDTH, FHEIGHT);
        window.setContentPane(bg);
    }

    private void formNameScreen() throws Exception {
        namescreen = new JPanel();
        namescreen.setLayout(new BoxLayout(namescreen, BoxLayout.Y_AXIS));
        namescreen.setSize(nameScreenWidth, nameScreenHeight);
        namescreen.setBackground(new Color(44, 50, 115, topBarAlpha));

        JLabel nameScreenText = new JLabel("profile name");
        nameScreenText.setSize(FWIDTH, 200);
        nameScreenText.setFont(new Font("Century Gothic", Font.PLAIN, 40));
        nameScreenText.setForeground(new Color(255,255,255,255));

        nameField = new JTextField(15);
        formNameField(nameScreenText);

        window.add(namescreen);
    }

    private void formNameField(JLabel nameScreenText) {
        nameField.setFont(new Font("Century Gothic", Font.PLAIN, 40));
        nameField.setForeground(new Color(255,255,255,255));
        nameField.setBackground(new Color(49, 55, 130, topBarAlpha));
        nameField.setBorder(null);
        nameField.setHorizontalAlignment(0);
        namescreen.add(Box.createRigidArea(new Dimension(0, 350)));
        namescreen.add(nameScreenText);
        namescreen.add(nameField);
        namescreen.add(Box.createRigidArea(new Dimension(0, 400)));
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profileName = nameField.getText();
                name.setText("<html><p>hello   <b>" + profileName + "</b></p></html>");
                Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(MainFrame.this::shrinkNameScreen,
                        0, 17, TimeUnit.MILLISECONDS);
            }
        });
    }

    private void shrinkNameScreen() {
        if ((int)(namescreen.getWidth() * nameShrinkRate) >= 1) {
            namescreen.setSize((int)(namescreen.getWidth() - namescreen.getWidth() * nameShrinkRate),
                    namescreen.getHeight());
            nameShrinkRate -= 0.0025;
        } else {
            namescreen.setSize(namescreen.getWidth() - 1, namescreen.getHeight());
        }
        window.repaint();
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

        formCloseBtn();

        window.add(topBar);
    }

    private void formCloseBtn() {
        ImageIcon closeImg = new ImageIcon("src/main/images/close.png");
        closeBtn = new JButton();
        closeBtn.setSize(22, 22);
        closeBtn.setOpaque(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setIcon(closeImg);
        topBar.add(closeBtn);
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
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
        name = new JLabel("<html><p>hello   <b>" + profileName + "</b></p></html>", hello, JLabel.LEFT);
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

        formTaskRow1(tasksList);

        formTaskRow2(tasksList);

        tasksList.add(Box.createRigidArea(new Dimension(0, 10)));

        buttonListeners();

        return tasksList;
    }

    private void formTaskRow2(JPanel tasksList) {
        JPanel taskRow2 = new JPanel();
        taskRow2.setSize(sideBarWidth, 50);
        taskRow2.setOpaque(false);
        shopBtn.setName("shop");
        hobbyBtn.setName("hobby");
        restBtn.setName("rest");
        formatTaskButton(shopBtn, false);
        formatTaskButton(hobbyBtn, false);
        formatTaskButton(restBtn, true);
        addBtnsTaskRow2(taskRow2, shopBtn, hobbyBtn, restBtn);
        tasksList.add(taskRow2);
    }

    private void addBtnsTaskRow2(JPanel taskRow2, JButton shopBtn, JButton hobbyBtn, JButton restBtn) {
        taskRow2.add(shopBtn);
        taskRow2.add(Box.createRigidArea(new Dimension(taskBtnGap, 0)));
        taskRow2.add(hobbyBtn);
        taskRow2.add(Box.createRigidArea(new Dimension(taskBtnGap, 0)));
        taskRow2.add(restBtn);
    }

    private void formTaskRow1(JPanel tasksList) {
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
        addBtnsTaskRow1(taskRow1);
        tasksList.add(taskRow1);
    }

    private void addBtnsTaskRow1(JPanel taskRow1) {
        taskRow1.add(workBtn);
        taskRow1.add(Box.createRigidArea(new Dimension(taskBtnGap, 0)));
        taskRow1.add(studyBtn);
        taskRow1.add(Box.createRigidArea(new Dimension(taskBtnGap, 0)));
        taskRow1.add(travelBtn);
        taskRow1.add(Box.createRigidArea(new Dimension(taskBtnGap, 0)));
        taskRow1.add(eatBtn);
    }

    private void buttonListeners() {
        workStudyBtnListeners();

        travelEatBtnListeners();

        hobbyShopBtnListeners();

        restBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formatTaskButton(activeBtn, false);
                formatTaskButton(restBtn, true);
            }
        });
    }

    private void hobbyShopBtnListeners() {
        shopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formatTaskButton(activeBtn, false);
                formatTaskButton(shopBtn, true);
            }
        });

        hobbyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formatTaskButton(activeBtn, false);
                formatTaskButton(hobbyBtn, true);
            }
        });
    }

    private void travelEatBtnListeners() {
        travelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formatTaskButton(activeBtn, false);
                formatTaskButton(travelBtn, true);
            }
        });

        eatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formatTaskButton(activeBtn, false);
                formatTaskButton(eatBtn, true);
            }
        });
    }

    private void workStudyBtnListeners() {
        workBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formatTaskButton(activeBtn, false);
                formatTaskButton(workBtn, true);
            }
        });

        studyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formatTaskButton(activeBtn, false);
                formatTaskButton(studyBtn, true);
            }
        });
    }

    private void formatTaskButton(JButton btn, Boolean active) {
        ImageIcon img;
        basicButtonFormatting(btn);
        if (active) {
            activeBtn = btn;
            if (btn.getName().equals("rest")) {
                timeLogger.newRest(new Rest(btn.getName()));
            } else {
                timeLogger.newActivity(new Activity(btn.getName(), btn.getName()));
            }
            img = new ImageIcon("src/main/images/" + btn.getName() + "_active.png");
        } else {
            img = new ImageIcon("src/main/images/" + btn.getName() + "_inactive.png");
        }
        btn.setIcon(img);
    }

    private void basicButtonFormatting(JButton btn) {
        btn.setForeground(new Color(0,0,0,0));
        btn.setPreferredSize(new Dimension(taskBtnWidth, taskBtnHeight));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
    }

    private void formBody() {
        formBodyPanel();
        formTot();
        formTimeLogged();
        formAverageTaskLength();

        ImageIcon sidestatsEmptyBG = new ImageIcon("src/main/images/emptyBG.png");
        JLabel sideStatsEnd = new JLabel("", sidestatsEmptyBG, JLabel.LEFT);


        ImageIcon timelogBG = new ImageIcon("src/main/images/timelogBG.png");
        JLabel timelog = new JLabel("", timelogBG, JLabel.LEFT);

        ImageIcon piechartBG = new ImageIcon("src/main/images/piechartBG.png");
        piechart = new JLabel("", piechartBG, JLabel.LEFT);
        buildChart();

        body.add(tot);
        body.add(totalTimeLogged);
        body.add(averageTaskLength);
        body.add(sideStatsEnd);
//        body.add(timelog);
        body.add(piechart);
        window.add(body);
    }

    private void formBodyPanel() {
        body = new JPanel();
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(topBarHeight + 5,sideBarWidth + 5,0,0));
        body.setSize(FWIDTH, FHEIGHT);
        body.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    }

    private void formAverageTaskLength() {
        ImageIcon averagetasklengthBG = new ImageIcon("src/main/images/averagetasklengthBG.png");
        averageTaskLength = new JLabel();
        averageTaskLength.setIcon(averagetasklengthBG);
        averageTaskLength.setFont(new Font("Century Gothic", Font.PLAIN, 30));
        averageTaskLength.setForeground(new Color(210, 102, 49, 255));
        averageTaskLength.setText("<html><p>" + formatTime(sidestatsAverageTaskLength) + "</p></html>");
        averageTaskLength.setHorizontalTextPosition(JLabel.CENTER);
    }

    private void formTimeLogged() {
        ImageIcon timeloggedBG = new ImageIcon("src/main/images/timeloggedBG.png");
        totalTimeLogged = new JLabel();
        totalTimeLogged.setIcon(timeloggedBG);
        totalTimeLogged.setFont(new Font("Century Gothic", Font.PLAIN, 30));
        totalTimeLogged.setForeground(new Color(98, 183, 104, 255));
        totalTimeLogged.setText("<html><p>" + formatTime(sidestatsTotalTimeLogged) + "</p></html>");
        totalTimeLogged.setHorizontalTextPosition(JLabel.CENTER);
    }

    private void formTot() {
        ImageIcon totBG = new ImageIcon("src/main/images/totBG.png");
        tot = new JLabel();
        tot.setIcon(totBG);
        tot.setFont(new Font("Century Gothic", Font.PLAIN, 30));
        tot.setForeground(new Color(44, 82, 115, 255));
        tot.setText("<html><p>" + formatTime(totTime) + "</p></html>");
        tot.setHorizontalTextPosition(JLabel.CENTER);
    }

    private void buildChart() {
        buildBasePie();
        buildDonutPie();
        buildWorkPie();
        buildStudyPie();
        buildTravelPie();
        buildEatPie();
        buildShopPie();
        buildHobbyPie();
        buildRestPie();

        piechart.add(donutPie);
        piechart.add(workPie);
        piechart.add(studyPie);
        piechart.add(travelPie);
        piechart.add(eatPie);
        piechart.add(shopPie);
        piechart.add(hobbyPie);
        piechart.add(restPie);
        piechart.add(basePie);
    }

    private void buildRestPie() {
        restPie = new Pie();
        restPie.setSize(580, 580);
        restPie.startAngle = hobbyPie.arc + shopPie.arc + eatPie.arc + travelPie.arc + studyPie.arc + workPie.arc;
        restPie.arc = 0;
        restPie.col = new Color(214, 191, 0, 255);
    }

    private void buildHobbyPie() {
        hobbyPie = new Pie();
        hobbyPie.setSize(580, 580);
        hobbyPie.startAngle = shopPie.arc + eatPie.arc + travelPie.arc + studyPie.arc + workPie.arc;
        hobbyPie.arc = 0;
        hobbyPie.col = new Color(13, 199, 105, 255);
    }

    private void buildShopPie() {
        shopPie = new Pie();
        shopPie.setSize(580, 580);
        shopPie.startAngle = eatPie.arc + travelPie.arc + studyPie.arc + workPie.arc;
        shopPie.arc = 0;
        shopPie.col = new Color(49, 170, 210, 255);
    }

    private void buildEatPie() {
        eatPie = new Pie();
        eatPie.setSize(580, 580);
        eatPie.startAngle = travelPie.arc + studyPie.arc + workPie.arc;
        eatPie.arc = 0;
        eatPie.col = new Color(87, 49, 210, 255);
    }

    private void buildTravelPie() {
        travelPie = new Pie();
        travelPie.setSize(580, 580);
        travelPie.startAngle = studyPie.arc + workPie.arc;
        travelPie.arc = 0;
        travelPie.col = new Color(210, 49, 185, 255);
    }

    private void buildStudyPie() {
        studyPie = new Pie();
        studyPie.setSize(580, 580);
        studyPie.startAngle = workPie.arc;
        studyPie.arc = 0;
        studyPie.col = new Color(210, 49, 49, 255);
    }

    private void buildWorkPie() {
        workPie = new Pie();
        workPie.setSize(580, 580);
        workPie.startAngle = 0;
        workPie.arc = 0;
        workPie.col = new Color(210, 102, 49, 255);
    }

    private void buildDonutPie() {
        donutPie = new Pie();
        donutPie.setSize(580, 580);
        donutPie.startAngle = 0;
        donutPie.arc = 360;
        donutPie.size = 250;
        donutPie.xoffset = donutPie.xoffset + (donutPie.defaultSize - donutPie.size) / 2;
        donutPie.yoffset = donutPie.yoffset + (donutPie.defaultSize - donutPie.size) / 2;
        donutPie.col = new Color(255,255,255,255);
    }

    private void buildBasePie() {
        basePie = new Pie();
        basePie.setSize(580, 580);
        basePie.startAngle = 0;
        basePie.arc = 360;
        basePie.col = new Color(210, 102, 49, 255);
    }

    private void calculateTaskSums() {
        for (TimeStamp t: timeLogger.getLogList()) {
            switch (t.getType()) {
                case "work": workSum += t.getDuration();
                break;
                case "study": studySum += t.getDuration();
                break;
                case "travel": travelSum += t.getDuration();
                break;
                case "eat": eatSum += t.getDuration();
                break;
                case "shop": shopSum += t.getDuration();
                break;
                case "hobby": hobbySum += t.getDuration();
                break;
                default: restSum += t.getDuration();
                break;
            }
        }
    }

    private void updatePieValues() {
        workPie.arc = 360 * workSum / sidestatsTotalTimeLogged;

        studyPie.startAngle = workPie.arc;
        studyPie.arc = 360 * studySum / sidestatsTotalTimeLogged;
//
        travelPie.startAngle = studyPie.arc + workPie.arc;
        travelPie.arc = 360 * travelSum / sidestatsTotalTimeLogged;
//
        eatPie.startAngle = travelPie.arc + studyPie.arc + workPie.arc;
        eatPie.arc = 360 * eatSum / sidestatsTotalTimeLogged;
//
        shopPie.startAngle = eatPie.arc + travelPie.arc + studyPie.arc + workPie.arc;
        shopPie.arc = 360 * shopSum / sidestatsTotalTimeLogged;
//
        hobbyPie.startAngle = shopPie.arc + eatPie.arc + travelPie.arc + studyPie.arc + workPie.arc;
        hobbyPie.arc = 360 * hobbySum / sidestatsTotalTimeLogged;
//
        restPie.startAngle = hobbyPie.arc + shopPie.arc + eatPie.arc + travelPie.arc + studyPie.arc + workPie.arc;
        restPie.arc = 360 * restSum / sidestatsTotalTimeLogged;
    }

    private void resetTaskSums() {
        workSum = studySum = travelSum = eatSum = shopSum = hobbySum = restSum = 0;
    }

    private String formatTime(int n) {
        String timerTextStr = "";
        //Set hours if hrs are 10 or over
        if (n / 3600 >= 10) {
            timerTextStr += n / 3600 + "h ";
        } else {
            timerTextStr += "" + n / 3600 + "h ";
        }
        n -= n / 3600 * 3600;   // get rid of the hours we already processed
        //Set minutes if mins are 10 or over
        if (n / 60 >= 10) {
            timerTextStr += n / 60 + "m";
        } else {
            timerTextStr += "" + n / 60 + "m ";
        }
        //n -= n / 60 * 60;   // get rid of the minutes we already processed
        //Set hours if hrs are 10 or over
        if (n - (n / 60 * 60) >= 10) {
            timerTextStr += n - (n / 60 * 60) + "s";
        } else {
            timerTextStr += "" + (n - (n / 60 * 60)) + "s";
        }
        return timerTextStr;
    }   // End of formatTime()

}

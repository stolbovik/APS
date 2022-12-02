package org.stolbov.svyatoslav.UI;

import org.stolbov.svyatoslav.Statistics.HomeDeviceStatistic;
import org.stolbov.svyatoslav.Statistics.ProcessingDeviceStatistic;
import org.stolbov.svyatoslav.Statistics.StatisticController;
import org.stolbov.svyatoslav.System.GeneralSystem;
import org.stolbov.svyatoslav.Utils.Action;
import org.stolbov.svyatoslav.Utils.ActionType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class UI {

    static JFrame startFrame;
    static JFrame modeFrame;
    static JFrame autoModeFrame;
    static JFrame stepModeFrame;

    private ArrayList<JTextField> startDataFields = new ArrayList<>();
    private GeneralSystem generalSystem;
    StatisticController statisticController;

    public void execute() throws InterruptedException {
        startFrame = getStartFrame();
        int i = 0;
        while (startFrame.isVisible())
        {
            Thread.sleep(100);
        }
        modeFrame = getModeFrame();
    }

    class SetDataAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            generalSystem = new GeneralSystem(Integer.parseInt(startDataFields.get(1).getText()),
                    Integer.parseInt(startDataFields.get(0).getText()),
                    Integer.parseInt(startDataFields.get(3).getText()),
                    Double.parseDouble(startDataFields.get(6).getText()),
                    Integer.parseInt(startDataFields.get(5).getText()),
                    Integer.parseInt(startDataFields.get(4).getText()),
                    Integer.parseInt(startDataFields.get(2).getText()));
            statisticController = generalSystem.getStatisticController();
            startFrame.setVisible(false);
            startFrame.revalidate();
        }
    }

    class StartAutoModeAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            modeFrame.setVisible(false);
            generalSystem.execute();
            autoModeFrame = getAutoModeFrame();
        }
    }

    class StartStepModeAction extends AbstractAction
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            modeFrame.setVisible(false);
            stepModeFrame = getStepModeFrame();
            stepModeFrame.setSize(1000, 300);
        }
    }

    class NextStepAction extends StartStepModeAction
    {
        static Action action = null;
        static double time = 0;
        static int temp = 0;
        static String tempString;
        static ActionType prevActionType;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (temp == 0) {
                action = generalSystem.startAction();
            }
            GUI.prevFirstFreeIndex = generalSystem.getBuffer().getFirstFreeIndex();
            if (generalSystem.getBuffer().getOldestRequestIndex()!= -1) {
                GUI.oldestRequest = generalSystem.getBuffer().getBuffer()
                        .get(generalSystem.getBuffer().getOldestRequestIndex()).getHomeDeviceNum() + "."
                        + generalSystem.getBuffer().getBuffer().get(generalSystem.getBuffer()
                        .getOldestRequestIndex()).getRequestNum();
            }
            if (generalSystem.getNextAction().getActionType() == ActionType.REQUEST_COMPLETE &&
                action.getActionType() != ActionType.REQUEST_COMPLETE) {
                GUI.completeRequest = generalSystem.getCompanySelectionManager()
                        .getProcessingDevice(generalSystem.getNextAction().getSourceOrDeviceNum()).getHomeRequestNow()
                        .getHomeDeviceNum() + "." + generalSystem.getCompanySelectionManager()
                        .getProcessingDevice(generalSystem.getNextAction().getSourceOrDeviceNum()).getHomeRequestNow()
                        .getRequestNum();
            }
            if (action.getActionType() == ActionType.REQUEST_COMPLETE && prevActionType == ActionType.REQUEST_COMPLETE) {
                GUI.completeRequest = tempString;
            }
            if (generalSystem.getNextAction().getActionType() == ActionType.REQUEST_COMPLETE) {
                tempString = generalSystem.getCompanySelectionManager()
                        .getProcessingDevice(generalSystem.getNextAction().getSourceOrDeviceNum()).getHomeRequestNow()
                        .getHomeDeviceNum() + "." + generalSystem.getCompanySelectionManager()
                        .getProcessingDevice(generalSystem.getNextAction().getSourceOrDeviceNum()).getHomeRequestNow()
                        .getRequestNum();
            }
            prevActionType = action.getActionType();
            if (temp != 0) {
                action = generalSystem.startAction();
            }
            if (action.getSourceOrDeviceNum() != -1 ) {
                GUI.currentRequest = String.valueOf(action.getSourceOrDeviceNum()) + "." + String
                        .valueOf(statisticController.getHomeDeviceStatistics()
                                .get(action.getSourceOrDeviceNum())
                                .getCountAllGeneratedHomeRequests() - 1
                        );

            }
            temp++;
            time = generalSystem.getTimeNow();
            Graphics g = stepModeFrame.getContentPane().getGraphics();
            stepModeFrame.getContentPane().print(g);
        }
    }

    private JFrame getFrame(String title) {
        JFrame frame = new JFrame() {};
        frame.setVisible(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        frame.setBounds(toolkit.getScreenSize().width/4, toolkit.getScreenSize().height/4, 400, 500);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private JFrame getStartFrame()
    {
        JPanel panel = new JPanel();

        JFrame frame = getFrame("Начать");
        frame.add(panel);

        JLabel label1 = new JLabel("Количество девайсов");
        label1.setPreferredSize(new Dimension(200, 40));

        JLabel label2 = new JLabel("Количество источников");
        label2.setPreferredSize(new Dimension(200, 40));

        JLabel label3 = new JLabel("Количество действий");
        label3.setPreferredSize(new Dimension(200, 40));

        JLabel label4 = new JLabel("Размер буффера");
        label4.setPreferredSize(new Dimension(200, 40));

        JLabel label5 = new JLabel("Минимум для распределения");
        label5.setPreferredSize(new Dimension(200, 40));

        JLabel label6 = new JLabel("Максимум для распределения");
        label6.setPreferredSize(new Dimension(200, 40));

        JLabel label7 = new JLabel("Лямбда для распределения");
        label7.setPreferredSize(new Dimension(200, 40));

        panel.add(label1);
        JTextField text1 = new JTextField("5", 20);
        panel.add(text1);

        panel.add(label2);
        JTextField text2 = new JTextField("10", 20);
        panel.add(text2);

        panel.add(label3);
        JTextField text3 = new JTextField("20000", 20);
        panel.add(text3);

        panel.add(label4);
        JTextField text4 = new JTextField("4", 20);
        panel.add(text4);

        panel.add(label5);
        JTextField text5 = new JTextField("0", 20);
        panel.add(text5);

        panel.add(label6);
        JTextField text6 = new JTextField("1", 20);
        panel.add(text6);

        panel.add(label7);
        JTextField text7 = new JTextField("3", 20);
        panel.add(text7);

        JButton button = new JButton(new SetDataAction());
        button.setText("submit");

        panel.add(button);
        frame.setSize(500, 400);
        frame.revalidate();

        startDataFields.add(text1);
        startDataFields.add(text2);
        startDataFields.add(text3);
        startDataFields.add(text4);
        startDataFields.add(text5);
        startDataFields.add(text6);
        startDataFields.add(text7);

        return frame;
    }

    private JFrame getModeFrame()
    {

        JFrame frame = getFrame("Режим работы");
        JPanel panel = new JPanel();

        JButton autoModeButton = new JButton(new StartAutoModeAction());
        autoModeButton.setText("Автоматический");

        JButton stepModeButton = new JButton(new StartStepModeAction());
        stepModeButton.setText("Пошаговый");

        JLabel label = new JLabel("Выбери режим работы");
        label.setPreferredSize(new Dimension(panel.getWidth(), 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label);
        panel.add(autoModeButton);
        panel.add(stepModeButton);

        frame.add(panel);
        frame.setSize(500, 400);
        frame.revalidate();

        return frame;
    }

    private JFrame getAutoModeFrame()
    {
        JFrame frame = getFrame("Автоматический режим");
        String[] columnNames = {"Источник", "Кол. заявок", "P(отк)", "Ср. t заявки в системе",
                "Ср. t ожидания", "Ср. t обслуживания", "Дисперсия t(ср.ож.)", "Дисперсия t(ср.об.)", "Прибор", "К(исп)"};




        String[][] data = new String[Math.max(generalSystem.getHomeDeviceCount(), generalSystem.getProcessingDeviceCount())][10];
        for(int i = 0; i < Math.max(generalSystem.getHomeDeviceCount(), generalSystem.getProcessingDeviceCount()); i++) {
            if (i < generalSystem.getHomeDeviceCount()) {
                HomeDeviceStatistic homeDeviceStatistic = statisticController.getHomeDeviceStatistics().get(i);
                data[i][0] = String.valueOf(i);
                data[i][1] = String.valueOf(homeDeviceStatistic.getCountAllGeneratedHomeRequests());
                data[i][2] = String.valueOf((double) homeDeviceStatistic.getCountAllCanceledHomeRequests()
                        / homeDeviceStatistic.getCountAllGeneratedHomeRequests());
                data[i][3] = String.valueOf(homeDeviceStatistic.getTimeInSystem() / homeDeviceStatistic.getCountAllGeneratedHomeRequests());
                data[i][4] = String.valueOf(homeDeviceStatistic.getTimeInBuffer() / homeDeviceStatistic.getCountAllGeneratedHomeRequests());
                data[i][5] = String.valueOf(homeDeviceStatistic.getTimeInDevice() / homeDeviceStatistic.getCountAllGeneratedHomeRequests());
                data[i][6] = String.valueOf(homeDeviceStatistic.getBufferTimeDispersion());
                data[i][7] = String.valueOf(homeDeviceStatistic.getDeviceTimeDispersion());
            }
            if (i < generalSystem.getProcessingDeviceCount()) {
                ProcessingDeviceStatistic processingDeviceStatistic = statisticController.getProcessingDeviceStatistics().get(i);
                data[i][8] = String.valueOf(i);
                data[i][9] = String.valueOf(processingDeviceStatistic.getWorkTime() / generalSystem.getTimeNow());
            }
        }

        JTable table = new JTable(data, columnNames);

        JScrollPane scroll = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(700, 200));
        frame.getContentPane().add(scroll);
        frame.setSize(1000, 500);
        frame.revalidate();

        return frame;
    }

    private JFrame getStepModeFrame()
    {
        JFrame frame = getFrame("Пошаговый режиим");
        GUI gui = new GUI(generalSystem, statisticController);
        gui.setBounds(0, 0, 1000, 400);
        JButton button = new JButton(new NextStepAction());
        button.setText("Шаг");
        button.setBounds(850, 25, 100, 30);
        frame.add(button);
        frame.add(gui);
        frame.revalidate();
        return frame;
    }

}
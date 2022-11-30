package org.stolbov.svyatoslav.GUI;

import org.stolbov.svyatoslav.Statistics.HomeDeviceStatistic;
import org.stolbov.svyatoslav.Statistics.StatisticController;
import org.stolbov.svyatoslav.System.Dates.Buffer;
import org.stolbov.svyatoslav.System.Dates.HomeRequest;
import org.stolbov.svyatoslav.System.Devices.ProcessingDevice;
import org.stolbov.svyatoslav.System.GeneralSystem;
import org.stolbov.svyatoslav.Utils.Action;
import org.stolbov.svyatoslav.Utils.ActionType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

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
/*            StatController.distributionQuality = Integer.parseInt(startDataFields.get(7).getText());*/
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
            stepModeFrame.setSize(1000, 600);
        }
    }

    class NextStepAction extends StartStepModeAction
    {
        static Action action = null;
        static double time = 0;
        @Override
        public void actionPerformed(ActionEvent e) {

//            while(time == controller.getCurrentTime())
//            {
            action = generalSystem.startAction();
//            }
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

        JFrame frame = getFrame("Start");
        frame.add(panel);

        JLabel label1 = new JLabel("Devices count");
        label1.setPreferredSize(new Dimension(100, 40));

        JLabel label2 = new JLabel("Sources count");
        label2.setPreferredSize(new Dimension(100, 40));

        JLabel label3 = new JLabel("<html>Required tasks<br />count<html>");
        label3.setPreferredSize(new Dimension(100, 40));

        JLabel label4 = new JLabel("Buffer size");
        label4.setPreferredSize(new Dimension(100, 40));

        JLabel label5 = new JLabel("Minimum");
        label5.setPreferredSize(new Dimension(100, 40));

        JLabel label6 = new JLabel("Maximum");
        label6.setPreferredSize(new Dimension(100, 40));

        JLabel label7 = new JLabel("lambda");
        label7.setPreferredSize(new Dimension(100, 40));

/*
        JLabel label8 = new JLabel("distribution");
        label8.setPreferredSize(new Dimension(100, 40));
*/

        panel.add(label1);
        JTextField text1 = new JTextField("5", 20);
        panel.add(text1);

        panel.add(label2);
        JTextField text2 = new JTextField("50", 20);
        panel.add(text2);

        panel.add(label3);
        JTextField text3 = new JTextField("20000", 20);
        panel.add(text3);

        panel.add(label4);
        JTextField text4 = new JTextField("4", 20);
        panel.add(text4);

        panel.add(label5);
        JTextField text5 = new JTextField("6", 20);
        panel.add(text5);

        panel.add(label6);
        JTextField text6 = new JTextField("7", 20);
        panel.add(text6);

        panel.add(label7);
        JTextField text7 = new JTextField("3", 20);
        panel.add(text7);

/*        panel.add(label8);
        JTextField text8 = new JTextField("1", 20);
        panel.add(text8);*/

        JButton button = new JButton(new SetDataAction());
        button.setText("submit");

        panel.add(button);
        frame.revalidate();

        startDataFields.add(text1);
        startDataFields.add(text2);
        startDataFields.add(text3);
        startDataFields.add(text4);
        startDataFields.add(text5);
        startDataFields.add(text6);
        startDataFields.add(text7);
/*        startDataFields.add(text8);*/

        return frame;
    }

    private JFrame getModeFrame()
    {

        JFrame frame = getFrame("Mode");
        JPanel panel = new JPanel();

        JButton autoModeButton = new JButton(new StartAutoModeAction());
        autoModeButton.setText("Auto Mode");

        JButton stepModeButton = new JButton(new StartStepModeAction());
        stepModeButton.setText("Step By Step");

        JLabel label = new JLabel("Choose your mode");
        label.setPreferredSize(new Dimension(panel.getWidth(), 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label);
        panel.add(autoModeButton);
        panel.add(stepModeButton);

        frame.add(panel);
        frame.revalidate();

        return frame;
    }

    private JFrame getAutoModeFrame()
    {
        JFrame frame = getFrame("Auto mode");
        String[] columnNames = {"Tasks count", "Failure probability", "Total time in system",
                "Time in buffer", "Buffer dispersion", "Maintenance dispersion"};

/*        for (int i = 0; i < generalSystem.getHomeDeviceCount(); i++)
        {
            System.out.println(Controller.stat.getSources().get(i).getCanceledTasksCount());
        }*/
        String data[][] = new String[generalSystem.getHomeDeviceCount()][6];
        int i = 0;
        for (HomeDeviceStatistic s: statisticController.getHomeDeviceStatistics())
        {
            data[i][0] = String.valueOf(s.getCountAllGeneratedHomeRequests());
            data[i][1] = String.valueOf((double)s.getCountAllCanceledHomeRequests() / s.getCountAllGeneratedHomeRequests());
            data[i][2] = String.valueOf(s.getTotalTime());
            data[i][3] = String.valueOf(s.getTotalBufferTime());
            data[i][4] = String.valueOf(s.getBufferTimeDispersion());
            data[i][5] = String.valueOf(s.getTotalTimeDispersion());
            i++;
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
        JFrame frame = getFrame("Step by step mode");
        GUI gui = new GUI();
        gui.setBounds(0, 0, 1000, 400);
        JButton button = new JButton(new NextStepAction());
        button.setText("next step");
        button.setBounds(850, 25, 100, 30);
        frame.add(button);
        frame.add(gui);
        frame.revalidate();
        return frame;
    }

    class GUI extends JPanel
    {
        public void paint(Graphics g)
        {
            this.setBackground(Color.WHITE);
            for (int i = 10; i <=60; i += 50)
            {
                g.drawRect(20, i, 100, 30);
                g.drawLine(120, i + 15, 150, i + 15);
            }
            for (int i = 115; i <=165; i+= 25)
            {
                g.drawOval(65, i, 1, 1);
            }
            g.drawRect(20, 190, 100, 30);
            g.drawString("И0", 30, 30);
            g.drawString("И1", 30, 80);
            g.drawString("И" + String.valueOf(generalSystem.getHomeDeviceCount() - 1), 30, 210);

            int dc = generalSystem.getProcessingDeviceCount();
            for (int i = 0; i < dc; i++)
            {
                g.drawRect(600, 10 + (i) * 50, 100, 30);
                g.drawLine(570, 25 + i * 50 , 600,25 + i * 50);
                g.drawLine(700, 25 + i * 50 , 730,25 + i * 50);
                g.drawChars(new char[]{'Д', String.valueOf(i).charAt(0)}, 0, 2, 610, 30 + (i) * 50);

            }
            g.drawLine(570, 25 + 50 * (dc - 1), 570, 25);
            g.drawLine(730, 25 + 50 * (dc - 1), 730, 25);
            g.drawLine(730, 25, 800, 25);

            int bs = (int) generalSystem.getBufferSize();
            for (int i = 0; i < bs; i++)
            {
                g.drawRect(310, 10 + (i + 1) * 50, 100, 30);
                g.drawLine(360, 40 + i * 50 , 360,40 + i * 50 + 20);
                g.drawChars(new char[]{'Б', String.valueOf(i).charAt(0)}, 0, 2, 320, 25 + (i + 1) * 50);

            }

            g.drawLine(120, 25, 180, 25);
            g.drawLine(120, 195, 150, 195);
            g.drawLine(150, 195, 150, 25);

            g.drawRect(180, 10, 100, 30);
            g.drawLine(280, 25, 310, 25);

            g.drawRect(310, 10, 100, 30);
            g.drawLine(410, 25, 440, 25);

            g.drawRect(440, 10, 100, 30);
            g.drawLine(540, 25, 570, 25);

            g.drawRect(180, 190, 100, 30);
            g.drawLine(230, 40, 230, 190);

            g.drawString("ДП", 190, 30);
            g.drawString("БП", 320, 30);
            g.drawString("ДВ", 450, 30);

            g.drawString("Отказ", 190, 210);
            g.drawString("Выход", 750, 20);
            String time = String.valueOf(generalSystem.getTimeNow());
            time = time.substring(0, time.indexOf('.') + 2);
            g.drawString("Время: " + time, 750, 50);
        }

        public void print(Graphics g)
        {
            g.clearRect(0, 0, 1000, 400);
            this.paint(g);
            List<ProcessingDevice> d = generalSystem.getCompanySelectionManager().getProcessingDevices();
            Buffer b = generalSystem.getBuffer();

            String current;
            Action action = NextStepAction.action;

            if (action.getActionType() == ActionType.NEW_REQUEST)
            {
                current = String.valueOf(action.getSourceOrDeviceNum()) + "." + String
                        .valueOf(statisticController.getHomeDeviceStatistics()
                                .get(action.getSourceOrDeviceNum())
                                .getCountAllGeneratedHomeRequests() - 1
                        );
                if (b.getFirstFreeIndex() == -1) {
                    g.setColor(Color.red);
                    g.fillRect(181, 191, 99, 29);
                    g.setColor(Color.black);
                    g.drawString(current, 210, 210);
                }
                else {
                    g.setColor(Color.yellow);
                    g.fillRect(311, 11 + (b.getFirstFreeIndex()) * 50, 99, 29);
                    g.setColor(Color.black);
                }
                g.drawString(current, 220, 30);
            }

            if (action.getActionType() == ActionType.REQUEST_COMPLETE)
            {
                current = String.valueOf(action.getSourceOrDeviceNum()) + "." + String
                        .valueOf(statisticController.getHomeDeviceStatistics()
                                .get(action.getSourceOrDeviceNum())
                                .getCountAllGeneratedHomeRequests());
                g.setColor(Color.green);
                g.fillRect(601, 11 + (action.getSourceOrDeviceNum()) * 50, 99, 29);
                g.setColor(Color.black);
                g.drawString("Свободный", 650, 30 + ((action.getSourceOrDeviceNum()) * 50));

                g.drawString(current, 790, 20);
            }

            for (int i = 0; i < generalSystem.getProcessingDeviceCount(); i++)
            {
                ProcessingDevice temp = d.get(i);
                if (!temp.isFree())
                {
                    g.drawString(temp.getHomeRequestNow().getSourceNum() + "." + temp.getHomeRequestNow().getRequestNum(), 650, 30 + (i) * 50);
                }
            }

            for (int i = 0; i < generalSystem.getBufferSize(); i++) {
                ArrayList<HomeRequest> buffer = b.getBuffer();
                if (!(buffer.get(i) == null))
                {
                    String id = buffer.get(i).getSourceNum() + "." + (buffer.get(i).getRequestNum());
                    g.drawString(id, 360, 30 + (i + 1) * 50);
                }
            }
        }
    }

}
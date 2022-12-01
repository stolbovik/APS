package org.stolbov.svyatoslav.UI;

import org.stolbov.svyatoslav.Statistics.StatisticController;
import org.stolbov.svyatoslav.System.Dates.Buffer;
import org.stolbov.svyatoslav.System.Dates.HomeRequest;
import org.stolbov.svyatoslav.System.Devices.ProcessingDevice;
import org.stolbov.svyatoslav.System.GeneralSystem;
import org.stolbov.svyatoslav.Utils.Action;
import org.stolbov.svyatoslav.Utils.ActionType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class GUI extends JPanel
{
    private GeneralSystem generalSystem;
    private StatisticController statisticController;
    public static String currentRequest;
    public static String oldestRequest;
    public static int prevFirstFreeIndex;

    public GUI(GeneralSystem generalSystem,
               StatisticController statisticController) {
        this.generalSystem = generalSystem;
        this.statisticController = statisticController;
    }

    public void paint(Graphics g)
    {
        this.setBackground(Color.WHITE);
        for (int i = 10; i <=60; i += 50) {
            g.drawRect(20, i, 100, 30);
            g.drawLine(120, i + 15, 150, i + 15);
        }
        for (int i = 115; i <=165; i+= 25) {
            g.drawOval(65, i, 1, 1);
        }
        g.drawRect(20, 190, 100, 30);
        g.drawString("И0", 30, 30);
        g.drawString("И1", 30, 80);
        g.drawString("И" + String.valueOf(generalSystem.getHomeDeviceCount() - 1), 30, 210);

        int dc = generalSystem.getProcessingDeviceCount();
        for (int i = 0; i < dc; i++) {
            g.drawRect(600, 10 + (i) * 50, 100, 30);
            g.drawLine(570, 25 + i * 50 , 600,25 + i * 50);
            g.drawLine(700, 25 + i * 50 , 730,25 + i * 50);
            g.drawChars(new char[]{'Д', String.valueOf(i).charAt(0)}, 0, 2, 610, 30 + (i) * 50);

        }
        g.drawLine(570, 25 + 50 * (dc - 1), 570, 25);
        g.drawLine(730, 25 + 50 * (dc - 1), 730, 25);
        g.drawLine(730, 25, 800, 25);

        int bs = generalSystem.getBufferSize();
        for (int i = 0; i < bs; i++) {
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
        Action action = UI.NextStepAction.action;

        if (action.getActionType() == ActionType.NEW_REQUEST)
        {
            if (prevFirstFreeIndex == -1) {
                g.setColor(Color.red);
                g.fillRect(181, 191, 99, 29);
                g.setColor(Color.black);
                g.drawString(oldestRequest, 210, 210);
            }
            g.setColor(Color.yellow);
            if (b.getFirstFreeIndex() == -1) {
                g.fillRect(311, 11 + (b.getLastRequestIndex() + 1) * 50, 99, 29);
            } else {
                g.fillRect(311, 11 + (b.getFirstFreeIndex()) * 50, 99, 29);
            }
            g.setColor(Color.black);
            g.drawString(currentRequest, 220, 30);
        }

        if (action.getActionType() == ActionType.REQUEST_COMPLETE)
        {
            currentRequest = String.valueOf(action.getSourceOrDeviceNum()) + "." + String
                    .valueOf(statisticController.getHomeDeviceStatistics()
                            .get(action.getSourceOrDeviceNum())
                            .getCountAllGeneratedHomeRequests());
            g.setColor(Color.green);
            g.fillRect(601, 11 + (action.getSourceOrDeviceNum()) * 50, 99, 29);
            g.setColor(Color.black);
            g.drawString("Свобод.", 650, 30 + ((action.getSourceOrDeviceNum()) * 50));

            g.drawString(currentRequest, 790, 20);
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
package org.stolbov.svyatoslav.UI.Frames;

import org.stolbov.svyatoslav.Statistics.HomeDeviceStatistic;
import org.stolbov.svyatoslav.Statistics.ProcessingDeviceStatistic;
import org.stolbov.svyatoslav.Statistics.StatisticController;
import org.stolbov.svyatoslav.System.GeneralSystem;

import javax.swing.*;
import java.awt.*;

public class AutoModeFrame extends BaseFrame {

    private final GeneralSystem generalSystem;
    private final StatisticController statisticController;

    public AutoModeFrame(GeneralSystem generalSystem) {
        super("Автоматический режим");
        this.generalSystem = generalSystem;
        this.statisticController = generalSystem.getStatisticController();
        addPanelToFrame();
    }

    @Override
    protected void addPanelToFrame() {
        String[] columnNames = {"Источник", "Кол. заявок", "P(отк)", "Ср. t заявки в системе",
                "Ср. t ожид.", "Ср. t обслуж.", "Дисперсия t(ср.ож.)", "Дисперсия t(ср.об.)", "Прибор", "К(исп)"};
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
        getContentPane().add(scroll);
        setSize(1000, 500);
        revalidate();
    }

}

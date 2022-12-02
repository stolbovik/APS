package org.stolbov.svyatoslav.UI;

import lombok.Getter;
import lombok.Setter;
import org.stolbov.svyatoslav.Statistics.StatisticController;
import org.stolbov.svyatoslav.System.GeneralSystem;
import org.stolbov.svyatoslav.UI.Frames.ModeFrame;
import org.stolbov.svyatoslav.UI.Frames.StartFrame;
import javax.swing.*;
@Getter
@Setter
public class MainGUI {

    private StartFrame startFrame;
    private ModeFrame modeFrame;
    private JFrame stepModeFrame;
    private GeneralSystem generalSystem;
    StatisticController statisticController;

    public void execute() throws InterruptedException {
        startFrame = new StartFrame();
        while (startFrame.isVisible()) {
            Thread.sleep(100);
        }
        generalSystem = startFrame.getGeneralSystem();
        statisticController = startFrame.getStatisticController();
        modeFrame = new ModeFrame(this);
    }

}
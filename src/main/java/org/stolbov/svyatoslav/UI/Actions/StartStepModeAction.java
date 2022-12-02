package org.stolbov.svyatoslav.UI.Actions;

import org.stolbov.svyatoslav.UI.Frames.StepModeFrame;
import org.stolbov.svyatoslav.UI.MainGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StartStepModeAction extends AbstractAction {

    private final MainGUI mainGUI;

    public StartStepModeAction(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainGUI.getModeFrame().setVisible(false);
        mainGUI.setStepModeFrame(new StepModeFrame(mainGUI));
        mainGUI.getStepModeFrame().setSize(1000, 300);
    }
}
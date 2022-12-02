package org.stolbov.svyatoslav.UI.Actions;

import org.stolbov.svyatoslav.UI.Frames.AutoModeFrame;
import org.stolbov.svyatoslav.UI.MainGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StartAutoModeAction extends AbstractAction {
    private final MainGUI mainGUI;

    public StartAutoModeAction(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainGUI.getModeFrame().setVisible(false);
        mainGUI.getGeneralSystem().execute();
        new AutoModeFrame(mainGUI.getGeneralSystem());
    }
}
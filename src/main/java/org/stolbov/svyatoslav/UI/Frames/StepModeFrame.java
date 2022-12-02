package org.stolbov.svyatoslav.UI.Frames;

import org.stolbov.svyatoslav.UI.Actions.NextStepAction;

import org.stolbov.svyatoslav.UI.MainGUI;
import org.stolbov.svyatoslav.UI.Panels.StepByStepPanel;

import javax.swing.*;

public class StepModeFrame extends BaseFrame {

    private final MainGUI mainGUI;

    public StepModeFrame(MainGUI mainGUI) {
        super("Пошаговый режим");
        this.mainGUI = mainGUI;
        addPanelToFrame();
    }

    @Override
    protected void addPanelToFrame() {
        StepByStepPanel stepByStepPanel = new StepByStepPanel(mainGUI);
        stepByStepPanel.setBounds(0, 0, 1000, 400);
        JButton button = new JButton(new NextStepAction(mainGUI));
        button.setText("Шаг");
        button.setBounds(850, 25, 100, 30);
        add(button);
        add(stepByStepPanel);
        revalidate();
    }


}

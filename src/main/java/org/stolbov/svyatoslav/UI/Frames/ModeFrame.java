package org.stolbov.svyatoslav.UI.Frames;

import org.stolbov.svyatoslav.UI.Actions.StartAutoModeAction;
import org.stolbov.svyatoslav.UI.Actions.StartStepModeAction;
import org.stolbov.svyatoslav.UI.MainGUI;

import javax.swing.*;
import java.awt.*;

public class ModeFrame extends BaseFrame{

    private final MainGUI mainGUI;

    public ModeFrame(MainGUI mainGUI) {
        super("Режим работы");
        this.mainGUI = mainGUI;
        addPanelToFrame();
    }

    @Override
    protected void addPanelToFrame() {
        JPanel panel = new JPanel();
        JButton autoModeButton = new JButton(new StartAutoModeAction(mainGUI));
        autoModeButton.setText("Автоматический");
        JButton stepModeButton = new JButton(new StartStepModeAction(mainGUI));
        stepModeButton.setText("Пошаговый");
        JLabel label = new JLabel("Выбери режим работы");
        label.setPreferredSize(new Dimension(panel.getWidth(), 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        panel.add(autoModeButton);
        panel.add(stepModeButton);
        add(panel);
        setSize(500, 400);
        revalidate();
    }

}

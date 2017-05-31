package main.java.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NEWGUI extends NEWSUBGUI{
    private JTabbedPane tabbedPane1;
    private JList list1;
    private JButton createButton;

    public NEWGUI() {
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { destroy();

            }
        });
    }
}

package gui;

import org.mnm.ipv4.subnet.IPv4Subnet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * SubnetFrame is a JFrame, that is used to create a new Subnet from TextFields, etc.
 * Dispose on close action
 */
@SuppressWarnings("serial")
public class SubnetFrame extends JFrame {

    private MainFrame mainFrame;
    private SubnetPanel subnetPanel;

    private JTabbedPane tabbedPane;

    private JPanel panel_7;

    private static int increment = 0;

    /**
     * &lt;pre&gt;
     * constructor creating the frame
     * &#64;param mainFrame
     * &lt;/pre&gt;
     */
    public SubnetFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setTitle("Create Subnet");
        setResizable(false);
        setSize(new Dimension(300, 500));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new BorderLayout(0, 0));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane);

        panel_7 = new JPanel();
        panel_7.setBackground(Color.WHITE);
        tabbedPane.addTab("Subnet 1", null, panel_7, null);
        panel_7.setLayout(null);
        panel_7.add(subnetPanel = new SubnetPanel(mainFrame, this));
        setVisible(true);
    }

    public SubnetFrame(MainFrame mainFrame, IPv4Subnet subnet) {
        this.mainFrame = mainFrame;
        setTitle("Edit Subnet");
        setResizable(false);
        setSize(new Dimension(300, 500));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new BorderLayout(0, 0));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane);

        panel_7 = new JPanel();
        panel_7.setBackground(Color.WHITE);
        tabbedPane.addTab("Subnet 1", null, panel_7, null);
        panel_7.setLayout(null);
        panel_7.add(subnetPanel = new SubnetPanel(mainFrame, this, subnet));
        setVisible(true);
    }

    public void addTab(JPanel panel) {
        increment++;
        this.tabbedPane.addTab("SubSubNet" + increment, panel);

    }

    /**
     * &lt;pre&gt;
     * method used to dispose the frame on button presses
     * &lt;/pre&gt;
     */
    public void closeFrame() {
        this.dispose();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public ArrayList<SubSubNetPanel> getSubSubNetPanels() {
        ArrayList<SubSubNetPanel> subSubNetPanels = new ArrayList<>();
        for (int i = 0; i < this.tabbedPane.getTabCount(); i++) {
            if(tabbedPane.getComponentAt(i).getClass() == SubSubNetPanel.class)
                subSubNetPanels.add((SubSubNetPanel) tabbedPane.getComponentAt(i));
        }
        return subSubNetPanels;
    }
}

package gui;

import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.subnet.ipv4SubnetUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

/**
 * &lt;pre&gt;
 * JPanel that is held by the JTabbedPane of the SubnetFrame
 * the idea is to reuse it when one wants to create a Subnet in a subnet
 * &lt;/pre&gt;
 */
@SuppressWarnings("serial")
public class SubnetPanel extends JPanel {

    private JScrollPane scrollPane;

    private JFormattedTextField txtNetworkID1;
    private JFormattedTextField txtNetworkID2;
    private JFormattedTextField txtNetworkID3;
    private JFormattedTextField txtNetworkID4;

    private JFormattedTextField txtSubnetMask1;
    private JFormattedTextField txtSubnetMask2;
    private JFormattedTextField txtSubnetMask3;
    private JFormattedTextField txtSubnetMask4;

    private JTextField txtSubnetName;

    private JPanel panel_3;
    private JPanel panel_4;
    private JPanel netIDPanel;
    private JPanel panel_1;
    private JPanel subnetPanel;
    private JPanel panel_5;
    private JPanel hostPanel;
    private JPanel hostPanelRootPane;
    private JPanel hostPanelButtonPane;

    private JPanel scrollPaneViewPortPane;
    private JPanel hostPanelScrollPaneAncor;

    private MainFrame mainFrame;
    private SubnetFrame subnetFrame;

    private JButton btnNext;
    private JButton btnCreate;
    private JButton btnAddHost;

    private String name = "", subnetMask = "", netID = "";

    private Color textColor = new Color(51, 153, 255);

    /**
     * &lt;pre&gt;
     * constructor creating the frame
     * &#64;param mainFrame the mainFrame
     * &#64;param subnetFrame the subnetFrame
     * &lt;/pre&gt;
     */
    public SubnetPanel(MainFrame mainFrame, SubnetFrame subnetFrame){
        this.run();
    }

    private void run() {
        this.setBorder(new LineBorder(new Color(0, 0, 0)));
        this.mainFrame = mainFrame;
        this.subnetFrame = subnetFrame;
        this.setSize(new Dimension(300, 400));
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        panel_4 = new JPanel();
        panel_4.setBackground(Color.WHITE);
        panel_4.setBounds(5, 5, 285, 329);
        this.add(panel_4);
        panel_4.setLayout(null);

        panel_3 = new JPanel();
        panel_3.setBounds(0, 0, 285, 41);
        panel_4.add(panel_3);
        panel_3.setLayout(new BorderLayout(0, 0));

        panel_1 = new JPanel();
        panel_3.add(panel_1, BorderLayout.CENTER);
        panel_1.setBorder(createTitledBorder("Subnet Name"));
        panel_1.setBackground(Color.WHITE);
        panel_1.setLayout(new BorderLayout(0, 0));

        txtSubnetName = new JTextField();
        txtSubnetName.setHorizontalAlignment(SwingConstants.LEFT);
        panel_1.add(txtSubnetName, BorderLayout.CENTER);
        txtSubnetName.setToolTipText("The name of the subnet");
        txtSubnetName.setColumns(10);

        netIDPanel = new JPanel();
        netIDPanel.setBounds(0, 52, 285, 41);
        panel_4.add(netIDPanel);
        netIDPanel.setBackground(Color.WHITE);
        netIDPanel.setBorder(createTitledBorder("Network ID"));
        netIDPanel.setLayout(new BoxLayout(netIDPanel, BoxLayout.X_AXIS));

        txtNetworkID1 = new JFormattedTextField();
        txtNetworkID1.setDocument(new TxtFieldFormatter());
        txtNetworkID2 = new JFormattedTextField();
        txtNetworkID2.setDocument(new TxtFieldFormatter());
        txtNetworkID3 = new JFormattedTextField();
        txtNetworkID3.setDocument(new TxtFieldFormatter());
        txtNetworkID4 = new JFormattedTextField();
        txtNetworkID4.setDocument(new TxtFieldFormatter());

        netIDPanel.add(txtNetworkID1);
        netIDPanel.add(txtNetworkID2);
        netIDPanel.add(txtNetworkID3);
        netIDPanel.add(txtNetworkID4);

        subnetPanel = new JPanel();
        subnetPanel.setBounds(0, 104, 285, 41);
        panel_4.add(subnetPanel);
        subnetPanel.setBorder(createTitledBorder("Subnet Mask"));
        subnetPanel.setBackground(Color.WHITE);
        subnetPanel.setLayout(new BoxLayout(subnetPanel, BoxLayout.X_AXIS));

        txtSubnetMask1 = new JFormattedTextField();
        txtSubnetMask1.setDocument(new TxtFieldFormatter());
        txtSubnetMask2 = new JFormattedTextField();
        txtSubnetMask2.setDocument(new TxtFieldFormatter());
        txtSubnetMask3 = new JFormattedTextField();
        txtSubnetMask3.setDocument(new TxtFieldFormatter());
        txtSubnetMask4 = new JFormattedTextField();
        txtSubnetMask4.setDocument(new TxtFieldFormatter());

        subnetPanel.add(txtSubnetMask1);
        subnetPanel.add(txtSubnetMask2);
        subnetPanel.add(txtSubnetMask3);
        subnetPanel.add(txtSubnetMask4);

        panel_5 = new JPanel();
        panel_5.setBounds(5, 345, 285, 33);
        add(panel_5);
        panel_5.setBackground(Color.WHITE);

        btnCreate = new JButton("Create");
        btnCreate.setToolTipText("Create the Subnet");
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                sendSubnet();
                subnetFrame.closeFrame();
            }
        });
        panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel_5.add(btnCreate);

        btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (sendSubnet())
                    clearFields();
            }
        });
        panel_5.add(btnNext);

        hostPanel = new JPanel();
        hostPanel.setBounds(0, 156, 285, 174);
        hostPanel.setBorder(createTitledBorder("Host Addresses"));
        hostPanel.setBackground(Color.WHITE);
        hostPanel.setLayout(new BorderLayout(0, 0));

        hostPanelRootPane = new JPanel(new BorderLayout());
        hostPanel.add(hostPanelRootPane);

        hostPanelButtonPane = new JPanel();
        hostPanelButtonPane.setBackground(Color.WHITE);
        hostPanelRootPane.add(hostPanelButtonPane, BorderLayout.NORTH);
        hostPanelButtonPane.setLayout(new BorderLayout(0, 0));

        btnAddHost = new JButton("");
        btnAddHost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                scrollPaneViewPortPane.add(new HostLabel(new IPv4HostAddress("192.168.0.0")));
                repaintScrollPaneViewPortPane();
            }
        });
        hostPanelButtonPane.add(btnAddHost, BorderLayout.EAST);
        btnAddHost.setToolTipText("Add a Host Address");
        btnAddHost.setIcon(new ImageIcon("resources/add.png"));
        btnAddHost.setBorderPainted(false);
        btnAddHost.setMargin(new Insets(0, 0, 0, 0));
        btnAddHost.setContentAreaFilled(false);

        hostPanelScrollPaneAncor = new JPanel();
        hostPanelScrollPaneAncor.setBackground(Color.WHITE);
        hostPanelRootPane.add(hostPanelScrollPaneAncor, BorderLayout.CENTER);
        hostPanelScrollPaneAncor.setLayout(new BorderLayout(0, 0));

        scrollPane = new JScrollPane();
        hostPanelScrollPaneAncor.add(scrollPane, BorderLayout.CENTER);

        scrollPaneViewPortPane = new JPanel();
        scrollPaneViewPortPane.setBackground(Color.WHITE);
        scrollPaneViewPortPane.setLayout(new BoxLayout(scrollPaneViewPortPane, BoxLayout.Y_AXIS));
        scrollPaneViewPortPane.setAlignmentX(LEFT_ALIGNMENT);
        scrollPane.setViewportView(scrollPaneViewPortPane);

        panel_4.add(hostPanel);
        this.setBackground(Color.WHITE);
    }

    public void fucusTxtSubnetName() {
        txtSubnetName.requestFocus();
    }

    /**
     * &lt;pre&gt;
     * Method creating a default TitledBorder with the provided Title
     * &#64;param title
     * &#64;return TitledBorder
     * &lt;/pre&gt;
     */
    private TitledBorder createTitledBorder(String title) {
        return new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
                title, TitledBorder.LEADING, TitledBorder.TOP, null, textColor);
    }

    /**
     * &lt;pre&gt;
     * sending the subnet specified in the JTextFields to the MainFrame.content_pane, after testing the params
     * &#64;return boolean
     * &lt;/pre&gt;
     */
    private boolean sendSubnet() {
        this.transferFields();
        boolean testPassed = true;
        if (this.name.isEmpty()) {
            this.updateTextArea(this.txtSubnetName);
            testPassed = false;
        }

        if (!ipv4SubnetUtils.isValidIP(this.netID)) {
            this.updateTextArea(this.txtNetworkID1);
            testPassed = false;
        }

        if (!ipv4SubnetUtils.isValidSubnetMask(this.subnetMask)) {
            this.updateTextArea(this.txtSubnetMask1);
            testPassed = false;
        }

        if (testPassed)
            mainFrame.addSubnet(this);
        return testPassed;
    }

    /**
     * &lt;pre&gt;
     * revalidating and repainting the specified JTextField
     * &#64;param jTextField
     * &lt;/pre&gt;
     */
    private void updateTextArea(JTextField jTextField) {
        jTextField.setForeground(Color.RED);
        jTextField.setBorder(new LineBorder(Color.RED));
        jTextField.revalidate();
        jTextField.repaint();
    }

    /**
     * &lt;pre&gt;
     * getting the text from the JTextFields and storing them in variables
     */
    private void transferFields() {
        this.name = this.txtSubnetName.getText();
        this.netID = getNetIDInput();
        this.subnetMask = getSubnetMaskInput();
    }

    private String getSubnetMaskInput() {
        String mask = "";
        mask += txtSubnetMask1.getText();
        mask += "." + txtSubnetMask2.getText();
        mask += "." + txtSubnetMask3.getText();
        mask += "." + txtSubnetMask4.getText();
        return mask;
    }

    private String getNetIDInput() {
        String netID = "";
        netID += txtNetworkID1.getText();
        netID += "." + txtNetworkID2.getText();
        netID += "." + txtNetworkID3.getText();
        netID += "." + txtNetworkID4.getText();
        return netID;
    }

    /**
     * &lt;pre&gt;
     * clearing the JTextFields
     * &lt;/pre&gt;
     */
    private void clearFields() {
        this.txtSubnetName.setText("");

        this.txtSubnetMask1.setText("");
        this.txtSubnetMask2.setText("");
        this.txtSubnetMask3.setText("");
        this.txtSubnetMask4.setText("");

        this.txtNetworkID1.setText("");
        this.txtNetworkID2.setText("");
        this.txtNetworkID3.setText("");
        this.txtNetworkID4.setText("");

        this.scrollPaneViewPortPane.removeAll();
        this.repaintScrollPaneViewPortPane();
    }

                /**
                 * &lt;pre&gt;
                 * repainting and revalidating the scrollPaneViewPortPane
                 * &lt;/pre&gt;
                 */

    private void repaintScrollPaneViewPortPane() {
        this.scrollPaneViewPortPane.revalidate();
        this.scrollPaneViewPortPane.repaint();
    }

    public String getName() {
        return this.name;
    }

    public String getSubnetMask() {
        return this.subnetMask;
    }

    public String getNetID() {
        return this.netID;
    }

    /**
     * &lt;pre&gt;
     * private class describing an ipv4 host address
     * &lt;/pre&gt;
     */
    private class HostLabel extends JPanel {
        private IPv4HostAddress address;
        private String name;
        private JLabel nameLabel;
        private JButton btnEdit;
        private JButton btnDelete;

        public HostLabel(IPv4HostAddress address) {
            this.address = address;
            this.name = address.toString();
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setBackground(Color.WHITE);

            btnDelete = new JButton("");
            btnDelete.setToolTipText("delete this Host Address");
            btnDelete.setIcon(new ImageIcon("resources/delete.png"));
            btnDelete.setBorderPainted(false);
            btnDelete.setMargin(new Insets(0, 0, 0, 0));
            btnDelete.setContentAreaFilled(false);
            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    destroy();
                }
            });

            btnEdit = new JButton("");
            btnEdit.setToolTipText("edit this Host Address");
            btnEdit.setIcon(new ImageIcon("resources/pencil2.png"));
            btnEdit.setBorderPainted(false);
            btnEdit.setMargin(new Insets(0, 0, 0, 0));
            btnEdit.setContentAreaFilled(false);

            nameLabel = new JLabel(name);
            nameLabel.setBackground(Color.WHITE);
            nameLabel.setOpaque(true);
            this.add(nameLabel);
            this.add(Box.createRigidArea(new Dimension(100, 0)));
            this.add(btnEdit);
            this.add(Box.createRigidArea(new Dimension(25, 0)));
            this.add(btnDelete);
        }

        public String getName() {
            return this.name;
        }

        public IPv4HostAddress getAddress() {
            return address;
        }

        private void destroy() {
            scrollPaneViewPortPane.remove(this);
            repaintScrollPaneViewPortPane();
        }

    }
}

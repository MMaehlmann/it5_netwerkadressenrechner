package gui;

import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.subnet.IPv4Subnet;
import org.mnm.ipv4.subnet.SubnetBuildingError;
import org.mnm.ipv4.subnet.IPv4SubnetUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * &lt;pre&gt;
 * JPanel that is held by the JTabbedPane of the SubnetFrame
 * the idea is to reuse it when one wants to create a Subnet in a subnet
 * &lt;/pre&gt;
 */
@SuppressWarnings("serial")
public class SubnetPanel extends JPanel {

    private final SubnetPanel subnetPanel = this;
    private JScrollPane scrollPane;

    private JComboBox<IPv4HostAddress> hostAddressSelector;

    private ArrayList<IPv4HostAddress> hostAddresses = new ArrayList<>();

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
    private JPanel subnetMaskPanel;
    private JPanel buttonPanel;
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
    private JButton btnFillHostAddressSelector;
    private JButton btnAddSubSubNet;

    private String name = "", subnetMask = "", netID = "";

    private Color textColor = new Color(51, 153, 255);
    private IPv4Subnet subnet;

    /**
     * &lt;pre&gt;
     * constructor creating the frame
     * &#64;param mainFrame the mainFrame
     * &#64;param subnetFrame the subnetFrame
     * &lt;/pre&gt;
     */
    public SubnetPanel(MainFrame mainFrame, SubnetFrame subnetFrame){
        this.run(mainFrame, subnetFrame);
    }

    public SubnetPanel(MainFrame mainFrame, SubnetFrame subnetFrame, IPv4Subnet subnet) {
        run(mainFrame, subnetFrame);
        this.subnet = subnet;
        populateFields();
        populateSubSubNets();
    }

    private void populateSubSubNets() {
        for(IPv4Subnet s : this.subnet.getSubSubNets()) {
            subnetFrame.addTab(new SubSubNetPanel(mainFrame, subnetFrame, subnetPanel, this.subnet, s), s.getShortName());
        }
    }

    private void populateFields() {
        this.txtSubnetName.setText(this.subnet.getName());

        populateNetID(this.subnet.getNetID().toString());

        populateSubnetMask(this.subnet.getSubnetMask().toString());

        populateHostAddresses(this.subnet.getHostAddressList());
    }

    private void populateHostAddresses(ArrayList<IPv4HostAddress> hostAddressList) {
        hostAddressList.stream()
                .forEach(h -> {
                    this.scrollPaneViewPortPane.add(new HostLabel(h));
                    this.hostAddresses.add(h);
                });
    }

    private void populateSubnetMask(String s) {
        this.txtSubnetMask1.setText(s.split("\\.")[0]);
        this.txtSubnetMask2.setText(s.split("\\.")[1]);
        this.txtSubnetMask3.setText(s.split("\\.")[2]);
        this.txtSubnetMask4.setText(s.split("\\.")[3]);
    }

    private void populateNetID(String s) {
        this.txtNetworkID1.setText(s.split("\\.")[0]);
        this.txtNetworkID2.setText(s.split("\\.")[1]);
        this.txtNetworkID3.setText(s.split("\\.")[2]);
        this.txtNetworkID4.setText(s.split("\\.")[3]);
    }

    private void run(MainFrame mainFrame, SubnetFrame subnetFrame) {
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

        subnetMaskPanel = new JPanel();
        subnetMaskPanel.setBounds(0, 104, 285, 41);
        panel_4.add(subnetMaskPanel);
        subnetMaskPanel.setBorder(createTitledBorder("Subnet Mask"));
        subnetMaskPanel.setBackground(Color.WHITE);
        subnetMaskPanel.setLayout(new BoxLayout(subnetMaskPanel, BoxLayout.X_AXIS));

        txtSubnetMask1 = new JFormattedTextField();
        txtSubnetMask1.setDocument(new TxtFieldFormatter());
        txtSubnetMask2 = new JFormattedTextField();
        txtSubnetMask2.setDocument(new TxtFieldFormatter());
        txtSubnetMask3 = new JFormattedTextField();
        txtSubnetMask3.setDocument(new TxtFieldFormatter());
        txtSubnetMask4 = new JFormattedTextField();
        txtSubnetMask4.setDocument(new TxtFieldFormatter());

        subnetMaskPanel.add(txtSubnetMask1);
        subnetMaskPanel.add(txtSubnetMask2);
        subnetMaskPanel.add(txtSubnetMask3);
        subnetMaskPanel.add(txtSubnetMask4);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(5, 345, 285, 33);
        add(buttonPanel);
        buttonPanel.setBackground(Color.WHITE);

        btnCreate = new JButton("Create");
        btnCreate.setToolTipText("Create the Subnet");
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(assembleSubnet()){
                    if(addSubSubNets()) {
                        sendSubnet();
                        subnetFrame.closeFrame();
                    }
                }else{
                    JOptionPane.showMessageDialog(buttonPanel,
                            "One invalid subnet was detected. Please confirm the validity.",
                            "Invalid Subnet",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.add(btnCreate);

        btnAddSubSubNet = new JButton("Add SubSubNet");
        btnAddSubSubNet.setToolTipText("This button will add a subsubnet, that is part of this subnet.");
        btnAddSubSubNet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subnetFrame.addTab(new SubSubNetPanel(mainFrame, subnetFrame, subnetPanel, subnet));
            }
        });
        buttonPanel.add(btnAddSubSubNet);

        btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (assembleSubnet()) {
                    sendSubnet();
                    subnetFrame.getSubSubNetPanels();
                    clearFields();
                }
            }
        });
        buttonPanel.add(btnNext);

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

        btnFillHostAddressSelector = new JButton("Fill Hosts");
        btnFillHostAddressSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transferFields();
                try {
                    IPv4Subnet subnet = new IPv4Subnet.Builder().buildByName(netID + "/" + IPv4SubnetUtils.calcPrefixByMask(subnetMask));
                    hostAddressSelector.setModel(new DefaultComboBoxModel(IPv4SubnetUtils.getAllHosts(subnet).toArray()));
                } catch (SubnetBuildingError subnetBuildingError) {
                    JOptionPane.showMessageDialog(buttonPanel,
                            "Invalid Subnet, no Host could be generated",
                            "Invalid Subnet",
                            JOptionPane.ERROR_MESSAGE);
                    subnetBuildingError.printStackTrace();
                }
            }
        });
        hostPanelButtonPane.add(btnFillHostAddressSelector, BorderLayout.WEST);

        btnAddHost = new JButton("");
        btnAddHost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                IPv4HostAddress h =(IPv4HostAddress) hostAddressSelector.getSelectedItem();
                if(!subnetPanelContainsHosts(hostAddressSelector.getSelectedItem().toString())) {
                    scrollPaneViewPortPane.add(new HostLabel(h));
                    hostAddresses.add(h);
                    hostAddressSelector.removeItemAt(hostAddressSelector.getSelectedIndex());
                }else
                    JOptionPane.showMessageDialog(buttonPanel,
                            "This Host already exists.",
                            "Duplicate Host",
                            JOptionPane.ERROR_MESSAGE);

                repaintScrollPaneViewPortPane();
            }
        });
        hostPanelButtonPane.add(btnAddHost, BorderLayout.EAST);

        hostAddressSelector = new JComboBox<>();
        hostPanelButtonPane.add(hostAddressSelector, BorderLayout.CENTER);


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

    public boolean addSubSubNets() {
        ArrayList<SubSubNetPanel> subSubNetPanels = subnetFrame.getSubSubNetPanels();
        for(SubSubNetPanel s : subSubNetPanels){
            try {
                if(!this.subnet.addSubSubNet(s.assembleSubnet())) {
                    JOptionPane.showMessageDialog(buttonPanel,
                            "One invalid subsubnet (\"" + s.getName() + "\") was detected. Please confirm the validity.",
                            "Invalid Subnet",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (SubnetBuildingError subnetBuildingError) {
                subnetBuildingError.printStackTrace();
            }
        }
        return true;
    }

    public void sendSubnet() {
        mainFrame.addSubnet(this.subnet);
    }

    public IPv4Subnet getSubnet() {
        return this.subnet;
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
    private boolean assembleSubnet() {
        this.transferFields();
        boolean testPassed = true;
        if (this.name.isEmpty()) {
            this.updateTextArea(this.txtSubnetName);
            testPassed = false;
        }

        if (!IPv4SubnetUtils.isValidIP(this.netID)) {
            this.updateTextArea(this.txtNetworkID1);
            this.updateTextArea(this.txtNetworkID2);
            this.updateTextArea(this.txtNetworkID3);
            this.updateTextArea(this.txtNetworkID4);
            testPassed = false;
        }

        if (!IPv4SubnetUtils.isValidSubnetMask(this.subnetMask)) {
            this.updateTextArea(this.txtSubnetMask1);
            this.updateTextArea(this.txtSubnetMask2);
            this.updateTextArea(this.txtSubnetMask3);
            this.updateTextArea(this.txtSubnetMask4);

            testPassed = false;
        }

        if (testPassed){
            try {
                this.subnet = new IPv4Subnet.Builder()
                        .buildByName(this.netID + "/" + IPv4SubnetUtils.calcPrefixByMask(this.subnetMask));
                this.subnet.setName(this.name);
                this.subnet.setHostAddresses(this.hostAddresses);
            } catch (SubnetBuildingError subnetBuildingError) {
                subnetBuildingError.printStackTrace();
                return false;
            }
        }

        return testPassed;
    }

    public boolean subnetPanelContainsHosts(String textFields) {
        Component[] components = scrollPaneViewPortPane.getComponents();
        for(Component c : components){
            if(c.getClass() == HostLabel.class){
                SubnetPanel.HostLabel l = (SubnetPanel.HostLabel) c;
                if(l.getHostAddress().toString().equals(textFields))
                    return true;
            }

        }
        return false;
    }

    public void addHostLabel(IPv4HostAddress address) {
        this.scrollPaneViewPortPane.add(new HostLabel(address));
        try {
            this.subnet.addHost(address);
        } catch (SubnetBuildingError subnetBuildingError) {
            subnetBuildingError.printStackTrace();
        }
        this.repaintScrollPaneViewPortPane();
    }

    /**
     * &lt;pre&gt;
     * revalidating and repainting the specified JTextField
     * &#64;param jTextField
     * &lt;/pre&gt;
     */
    private void updateTextArea(JTextField jTextField) {
        jTextField.setForeground(Color.RED);
        //jTextField.setBorder(new LineBorder(Color.RED));
        jTextField.revalidate();
        jTextField.repaint();
    }

    /**
     * &lt;pre&gt;
     * getting the text from the JTextFields and storing them in variables
     * &lt;/pre&gt;
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
    public class HostLabel extends JPanel {
        private IPv4HostAddress address;
        private String name;
        private JLabel nameLabel;
        private JButton btnEdit;
        private JButton btnDelete;

        private HostLabel hostLabel = this;

        public HostLabel(IPv4HostAddress address) {
            setHostAddress(address);
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
                    removeHost(address);
                    destroy();
                }
            });

            btnEdit = new JButton("");
            btnEdit.setToolTipText("edit this Host Address");
            btnEdit.setIcon(new ImageIcon("resources/pencil2.png"));
            btnEdit.setBorderPainted(false);
            btnEdit.setMargin(new Insets(0, 0, 0, 0));
            btnEdit.setContentAreaFilled(false);
            btnEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JEditPane edit = new JEditPane(hostLabel, subnetPanel, hostAddresses, JEditPane.HOST_EDIT_OPERATION);
                    destroy();
                }
            });

            nameLabel = new JLabel(name);
            nameLabel.setBackground(Color.WHITE);
            nameLabel.setOpaque(true);
            this.add(nameLabel);
            this.add(Box.createRigidArea(new Dimension(100, 0)));
            this.add(btnEdit);
            this.add(Box.createRigidArea(new Dimension(25, 0)));
            this.add(btnDelete);
            this.setMaximumSize(this.getPreferredSize());
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

        public IPv4HostAddress getHostAddress() {
            return this.address;
        }

        public void setHostAddress(IPv4HostAddress address) {
            this.address = address;
            this.name = address.toString();
        }
    }

    public void removeHost(IPv4HostAddress address) {
        for (Iterator<IPv4HostAddress> it = this.hostAddresses.iterator(); it.hasNext(); ) {
            IPv4HostAddress h = it.next();
            if(h.equals(address))
                it.remove();
        }
    }
}

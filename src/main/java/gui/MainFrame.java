package gui;

import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;
import org.mnm.ipv4.subnet.IPv4Subnet;
import org.mnm.ipv4.subnet.IPv4SubnetMask;
import org.mnm.ipv4.subnet.SubnetBuildingError;
import org.mnm.ipv4.subnet.ipv4SubnetUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * &lt;pre&gt;
 * the main frame of the gui.
 * has a JTabbedPane, that holds a JPanel, holding all the other Components
 * &lt;/pre&gt;
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    private JPanel button_panel;
    private JPanel content_panel;
    private JPanel panel_2;
    private JPanel panel_3;

    private JButton btnAddSubnet;

    private Color textColor = new Color(51, 153, 255);


    /**
     * &lt;pre&gt;
     * constructor creating the frame
     * &lt;/pre&gt;
     */
    public MainFrame() {
        setSize(new Dimension(400, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new BorderLayout(5, 5));

        button_panel = new JPanel();
        button_panel.setBorder(createTitledBorder("Buttons"));
        button_panel.setBackground(Color.WHITE);
        getContentPane().add(button_panel, BorderLayout.SOUTH);

        btnAddSubnet = new JButton("Create Subnet");
        btnAddSubnet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                initSubnetFrame();
            }
        });
        btnAddSubnet.setToolTipText("opens a new window to define a new subnet");
        btnAddSubnet.setForeground(Color.BLACK);
        btnAddSubnet.setFont(new Font("Tahoma", Font.PLAIN, 13));
        button_panel.add(btnAddSubnet);

        content_panel = new JPanel();
        content_panel.setBackground(Color.WHITE);
        content_panel.setBorder(createTitledBorder("Subnets"));
        content_panel.setLayout(new BoxLayout(content_panel, BoxLayout.Y_AXIS));
        getContentPane().add(content_panel, BorderLayout.CENTER);

        panel_2 = new JPanel();
        panel_2.setBackground(Color.WHITE);
        getContentPane().add(panel_2, BorderLayout.WEST);
        panel_3 = new JPanel();
        panel_3.setBackground(Color.WHITE);
        getContentPane().add(panel_3, BorderLayout.EAST);

        //panel_4 = new JPanel();
        //panel_4.setBorder(createTitledBorder("Tabs"));
        //panel_4.setBackground(Color.WHITE);
        //getContentPane().add(panel_4, BorderLayout.NORTH);
        setForeground(Color.WHITE);
        setBackground(Color.WHITE);
        setVisible(true);
    }

    /**
     * &lt;pre&gt;
     * create a new SubnetFrame
     * &lt;/pre&gt;
     */
    private void initSubnetFrame() {
        SubnetFrame subnetFrame = new SubnetFrame(this);
    }

    /**
     * &lt;pre&gt;
     * psvm, what else to say?
     * &#64;param args
     * &lt;/pre&gt;
     */
    public static void main(String[] args) {
        MainFrame m = new MainFrame();
    }

    /**
     * &lt;pre&gt;
     * add a subnet to the content_panel, created in the SubnetFrame JFrame
     * &#64;param sPanel
     * &lt;/pre&gt;
     */
    public void addSubnet(IPv4Subnet subnet) {
        content_panel.add(new SubnetLabel(subnet));
        refreshContentPanel();
    }

    /**
     * &lt;pre&gt;
     * revalidating and repainting the content_panel
     * &lt;/pre&gt;
     */
    private void refreshContentPanel() {
        content_panel.revalidate();
        content_panel.repaint();
    }

    /**
     * &lt;pre&gt;
     * removing a child element of component_panel by object
     * &#64;param component
     * &#64;param subnetLabel
     * &lt;/pre&gt;
     */
    private void destroyChild(JPanel component, SubnetLabel subnetLabel) {
        component.remove(subnetLabel);
        refreshContentPanel();
    }

    /**
     * &lt;pre&gt;
     * creating a TitledBorder by the specified title
     * &lt;/pre&gt;
     */
    private TitledBorder createTitledBorder(String title) {
        return new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
                title, TitledBorder.LEADING, TitledBorder.TOP, null, textColor);
    }

    /**
     * &lt;pre&gt;
     * private class holding a subnet in the content_panel
     * &lt;/pre&gt;
     */
    private class SubnetLabel extends JPanel {
        private JLabel nameLabel;
        private JButton btnEdit;
        private JButton btnDelete;

        private IPv4Subnet subnet;

        public SubnetLabel(IPv4Subnet subnet) {
            this.subnet = subnet;

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

            nameLabel = buildLabel();
            nameLabel.setBackground(Color.WHITE);
            nameLabel.setOpaque(true);
            this.add(nameLabel);
            this.add(Box.createRigidArea(new Dimension(10, 0)));
            this.add(btnEdit);
            this.add(Box.createRigidArea(new Dimension(10, 0)));
            this.add(btnDelete);
            subnet.print();
        }

        /**
         * &lt;pre&gt;
         * building the label from the JTextFields
         * &#64;return JLabel
         * &lt;/pre&gt;
         */
        private JLabel buildLabel() {
            String label = "";
            label += this.subnet.getName() + " | ";
            label += this.subnet.getNetID() + "/";
            label += this.subnet.getSubnetMask().getPrefix();
            return new JLabel(label);
        }

        public String getName() {
            return this.subnet.getName();
        }

        /**
         * &lt;pre&gt;
         * destroying this SubnetLabel and removing it from the content_panel
         * &lt;/pre&gt;
         */
        private void destroy() {
            destroyChild(content_panel, this);
        }
    }
}

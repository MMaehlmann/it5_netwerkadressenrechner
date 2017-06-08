package gui;

import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.subnet.IPv4SubnetUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by martin on 06/06/17.
 */
public class JEditPane extends JFrame{

    private final Dimension dim = new Dimension(250, 100);
    private final Dimension max = new Dimension(40, 20);

    public static final int HOST_EDIT_OPERATION = 0;
    public static final int SUB_HOST_EDIT_OPERATION = 1;
    private SubSubNetPanel subSubNetPanel = null;

    private  SubnetPanel.HostLabel hostLabel = null;
    private final int operation;
    private SubnetPanel subnetPanel = null;
    private  SubSubNetPanel.HostLabel subHostLabel = null;
    private ArrayList<IPv4HostAddress> hostAddresses;

    private JFormattedTextField txtNetworkID1 = new JFormattedTextField();
    private JFormattedTextField txtNetworkID2 = new JFormattedTextField();
    private JFormattedTextField txtNetworkID3 = new JFormattedTextField();
    private JFormattedTextField txtNetworkID4 = new JFormattedTextField();

    private JButton btnUpdate = new JButton("Update");


    JFormattedTextField[] hostFields = {
            txtNetworkID1,
            txtNetworkID2,
            txtNetworkID3,
            txtNetworkID4,
    };
    private String address;

    public JEditPane(SubnetPanel.HostLabel hostLabel, SubnetPanel subnetPanel, ArrayList<IPv4HostAddress> hostAddresses, int operation) {
        this.hostAddresses = hostAddresses;
        this.setSize(dim);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Color.WHITE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.LINE_AXIS));
        this.setVisible(true);
        this.setTitle("Edit Host Address");

        this.hostLabel = hostLabel;
        this.operation = operation;
        this.subnetPanel = subnetPanel;

        chooseOperation(operation);

        initButton();
    }

    public JEditPane(SubSubNetPanel.HostLabel hostLabel, SubSubNetPanel subSubNetPanel, ArrayList<IPv4HostAddress> hostAddresses, int hostEditOperation) {
        this.hostAddresses = hostAddresses;
        this.setSize(dim);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Color.WHITE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.LINE_AXIS));
        this.setVisible(true);
        this.setTitle("Edit Host Address");

        this.subHostLabel = hostLabel;
        this.operation = hostEditOperation;
        this.subSubNetPanel = subSubNetPanel;

        chooseOperation(operation);

        initButton();
    }

    private void initButton() {
        this.btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseButtonOperation();
            }
        });

        this.getContentPane().add(btnUpdate);
    }

    private void chooseButtonOperation() {

        if(this.operation == JEditPane.HOST_EDIT_OPERATION)
            hostEditOperation();
        if(this.operation == JEditPane.SUB_HOST_EDIT_OPERATION)
            subHostEditOperation();
    }

    private void subHostEditOperation() {
        if(IPv4SubnetUtils.isHost(getTextFields(), subSubNetPanel.getSubSubnet())
           &&
           !subSubNetPanel.subnetPanelContainsHosts(getTextFields())){
            IPv4HostAddress h = new IPv4HostAddress(getTextFields());
            subSubNetPanel.removeHost(subHostLabel.getAddress());
            subSubNetPanel.addHostLabel(h);
            hostAddresses.add(h);
            dispose();
        }else{
            JOptionPane.showMessageDialog(getContentPane(),
                    "This Host is not part of the subnet, or invalid, or a duplicate.",
                    "Invalid Host",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hostEditOperation() {
        if(IPv4SubnetUtils.isHost(getTextFields(), subnetPanel.getSubnet())
           &&
           !subnetPanel.subnetPanelContainsHosts(getTextFields())){
            IPv4HostAddress h = new IPv4HostAddress(getTextFields());
            subnetPanel.removeHost(hostLabel.getAddress());
            subnetPanel.addHostLabel(h);
            hostAddresses.add(h);
            dispose();
        }else{
            JOptionPane.showMessageDialog(getContentPane(),
                    "This Host is not part of the subnet, or invalid, or a duplicate.",
                    "Invalid Host",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean subnetPanelContainsHosts(String textFields) {
        Component[] components = subnetPanel.getComponents();
        for(Component c : components){
            if(c.getClass() == SubnetPanel.HostLabel.class){
                SubnetPanel.HostLabel l = (SubnetPanel.HostLabel) c;
                if(l.getHostAddress().toString().equals(textFields))
                    return true;
            }

        }
        return false;
    }

    private void chooseOperation(int operation) {
        if(operation == 0)
            initHostOperation();
        if(operation == 1)
            initSubHostOperation();
    }

    private void initSubHostOperation() {
        for(JFormattedTextField f : hostFields){
            f.setMaximumSize(max);
            f.setDocument(new TxtFieldFormatter());
            this.getContentPane().add(f);
            this.getContentPane().add(new JLabel("."));
        }

        fillSubHostFields();
    }

    private void fillSubHostFields() {
        String[] address = subHostLabel.getHostAddress().toString().split("\\.");
        for(int i = 0; i < 4; i++)
            hostFields[i].setText(address[i]);
    }

    private void initHostOperation() {

        for(JFormattedTextField f : hostFields){
            f.setMaximumSize(max);
            f.setDocument(new TxtFieldFormatter());
            this.getContentPane().add(f);
            this.getContentPane().add(new JLabel("."));
        }

        fillHostFields();
    }

    private void fillHostFields() {
        String[] address = hostLabel.getHostAddress().toString().split("\\.");
        for(int i = 0; i < 4; i++)
            hostFields[i].setText(address[i]);
    }

    public String getTextFields() {
       return chooseGetOperation();
    }

    private String chooseGetOperation() {
        if(this.operation == 0)
            return getHostAddress();
        if(this.operation == 1)
            return getHostAddress();
        return "";
    }

    public String getHostAddress() {
        String ret = "";
        for (JFormattedTextField f : hostFields)
            ret += f.getText() + ".";
        System.out.println(ret.substring(0, ret.length()-1));
        return ret.substring(0,ret.length()-1);
    }
}

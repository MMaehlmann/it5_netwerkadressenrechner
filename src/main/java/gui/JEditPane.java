package gui;

import org.mnm.ipv4.ipv4.IPv4HostAddress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by martin on 06/06/17.
 */
public class JEditPane extends JFrame{

    private final Dimension dim = new Dimension(250, 100);
    private final Dimension max = new Dimension(40, 20);

    //public static final int SUBNET_EDIT_OPERATION = 1;
    public static final int HOST_EDIT_OPERATION = 0;

    private final SubnetPanel.HostLabel hostLabel;
    private final int operation;
    private final SubnetPanel subnetPanel;

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

    public JEditPane(SubnetPanel.HostLabel hostLabel, SubnetPanel subnetPanel, int operation) {
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

    private void initButton() {
        this.btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subnetPanel.addHostLabel(new IPv4HostAddress(getTextFields()));
                dispose();
            }
        });

        this.getContentPane().add(btnUpdate);
    }

    private void chooseOperation(int operation) {
        if(operation == 0)
            initHostOperation();
    }

    private void initHostOperation() {

        for(JFormattedTextField f : hostFields){
            f.setMaximumSize(max);
            f.setDocument(new TxtFieldFormatter());
            this.getContentPane().add(f);
            this.getContentPane().add(new JLabel("."));
        }

        fillFields();
    }

    private void fillFields() {
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

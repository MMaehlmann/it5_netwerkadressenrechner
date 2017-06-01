package org.mnm.ipv4.subnet;


import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;

import java.util.ArrayList;

/**
 * This class validates if the "subsubnet" is
 * a valid subnet of the "subnet"
 *
 * Created by martin on 01/06/17.
 * &lt;/pre&gt;
 */
public class SubSubNetValidator {

    private IPv4Subnet subnet;
    private IPv4Subnet subsubnet;


    /**
     * &lt;pre&gt;
     * The "subnet" is the root subnet of the "subsubnet". This class validates if the "subsubnet" is
     * a valid subnet of the "subnet"
     *
     * &#64;param subnet
     * &#64;param subsubnet
     * &lt;/pre&gt;
     */
    public SubSubNetValidator(IPv4Subnet subnet, IPv4Subnet subsubnet) {
        this.subnet = subnet;
        this.subsubnet = subsubnet;
    }

    /**
     * &lt;pre&gt;
     * this method validates the subsubnet
     *
     * checks are:
     *              is the NetID a valid host of "subnet"
     *              is the NetID available as a host of "subnet"
     *
     * &#64;return
     * &lt;/pre&gt;
     */
    public boolean isValid() {

        boolean valid = true;

        if(!netIDIsHost(subsubnet.getNetID()))
            valid = false;

        if(!netIDIsAvailable(subsubnet.getNetID()))
            valid = false;

        if(!subSubNetFitsSubnet(
                subsubnet.getSubnetMask().getMaxHosts(),
                subnet.getRemainingAmountOfHosts()))
            valid = false;

        if(subSubNetStealsHosts(
                ipv4SubnetUtils.getAllHosts(subsubnet),
                subnet.getHostAddressList()
        ))
            valid = false;
        return valid;
    }

    private boolean subSubNetStealsHosts(
            ArrayList<IPv4HostAddress> subsubnetHosts,
            ArrayList<IPv4HostAddress> subnetHosts) {
        return subsubnetHosts.stream()
                .anyMatch(h -> subnetHosts.contains(h));

    }

    private boolean subSubNetFitsSubnet(long amountOfHosts, long remainingAmountOfHosts) {
        return remainingAmountOfHosts >= amountOfHosts;
    }

    private boolean netIDIsAvailable(IPv4NetworkID netID) {
        return subnet.getAddressList().contains(netID);
    }

    private boolean netIDIsHost(IPv4NetworkID netID) {
        return ipv4SubnetUtils.isHost(netID.getIpv4Address(), subnet);
    }
}

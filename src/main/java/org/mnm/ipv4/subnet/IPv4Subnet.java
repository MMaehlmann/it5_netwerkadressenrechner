package org.mnm.ipv4.subnet;

import org.mnm.ipv4.ipv4.IPv4Address;
import org.mnm.ipv4.ipv4.IPv4BroadcastAddress;
import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * &lt;pre&gt;
 * Programatic representation of an IPv4Subnet. Able to be build by name+prefix, of amount of hosts it has to hold
 *
 * Created by martin on 04/05/17.
 * &lt;/pre&gt;
 */
public class IPv4Subnet {

    private ArrayList<IPv4Address> addressList = new ArrayList<>();
    private ArrayList<IPv4HostAddress> hostAddresses = new ArrayList<>();

    private IPv4SubnetMask subnetMask;
    private IPv4BroadcastAddress broadcastAddress;
    private IPv4NetworkID networkID;

    private String name;

    private long remainingAmountOfHosts;

    private ArrayList<IPv4Subnet> subSubNets = new ArrayList<>();

    public IPv4Subnet() {}

    /**
     * &lt;pre&gt;
     * constructor accepting an IPv4Subnet.Builder
     * &#64;param builder IPv4Subnet.Builder
     * &lt;/pre&gt;
     */
    public IPv4Subnet(IPv4Subnet.Builder builder) {
        this.setName(builder.name)
            .setSubnetMask(builder.subnetMask)
            .setBroadcastAddress(builder.broadcastAddress)
            .setNetworkID(builder.networkID);
        this.remainingAmountOfHosts = getSubnetMask().getMaxHosts();
    }

    /**
     * <pre>
     * adding a host address to the list of host addresses and the list of IP addresses, if it is a
     * valid host of this subnet
     *
     * &#64;param address
     * &#64;return
     * &#64;throws SubnetBuildingError
     * </pre>
     */
    public IPv4Subnet addHost(IPv4HostAddress address) throws SubnetBuildingError {
        if(ipv4SubnetUtils.isHost(address.getIpv4Address(), this)) {
            this.addressList.add(address);
            this.hostAddresses.add(address);
        }else
            throw new SubnetBuildingError("Invalid host address: " + address);

        return this;
    }

    /**
     * adds a subnet to the list of subsubnets
     *
     * &#64;param subsubnet the subsubnet to add
     * &#64;return this
     */
    public IPv4Subnet addSubSubNet(IPv4Subnet subsubnet) {
        SubSubNetValidator validator = new SubSubNetValidator(this, subsubnet);
        if(validator.isValid())
            this.subSubNets.add(subsubnet);
        return this;
    }

    /**
     * removes a subnet from the list of subsubnets
     *
     * &#64;param subsubnet the subsubnet to add
     * &#64;return this
     */
    public IPv4Subnet removeSubSubNet(IPv4Subnet subsubnet) {
        this.subSubNets.remove(subsubnet);
        return this;
    }

    /**
     * adds a IPv4HostAddress to the list of addresses and to
     * the list of host addresses
     *
     * &#64;param address the IPv4HostAddress
     * &#64;return this
     */
    public IPv4Subnet removeHost(IPv4HostAddress address){
        addressList.remove(address);
        return this;
    }

    /**
     * adds a IPv4NetworkID to the list of addresses
     * and sets its local value
     *
     * &#64;param address the IPv4NetworkID
     * &#64;return this
     */
    public IPv4Subnet addNetID(IPv4NetworkID address) {
        this.networkID = address;
        this.addressList.add(address);
        return this;
    }

    /**
     * adds a IPv4BroadcastAddress to the list of addresses
     * and sets its local value
     *
     * &#64;param address the IPv4BroadcastAddress
     * &#64;return this
     */
    public IPv4Subnet addBroadcast(IPv4BroadcastAddress address) {
        this.broadcastAddress = address;
        this.addressList.add(address);
        return this;
    }

    public IPv4BroadcastAddress getBroadcast() {
        return this.broadcastAddress;
    }

    /**
     * adds a IPv4BroadcastAddress to the list of addresses
     * and sets its local value
     *
     * &#64;param address the IPv4BroadcastAddress
     * &#64;return this
     */
    public IPv4Subnet setBroadcastAddress(IPv4BroadcastAddress address) {
        this.addressList.add(address);
        this.broadcastAddress = address;
        return this;
    }

    /**
     * adds a IPv4NetworkID to the list of addresses
     * and sets its local value
     *
     * &#64;param address the IPv4NetworkID
     * &#64;return this
     */
    public IPv4Subnet setNetworkID(IPv4NetworkID address) {
        this.addressList.add(address);
        this.networkID = address;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public IPv4Subnet setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * &lt;pre&gt;
     * print the subnet to standard out
     * &lt;/pre&gt;
     */
    public void print() {
        System.out.println(subnetMask.getClass().toString() + ": " + subnetMask.toString());
        addressList.stream()
                .filter(a -> a != null)
                .forEach((IPv4Address a) -> {
                    System.out.println(a.getClass().toString() + ": " + a);
                });
    }

    public IPv4NetworkID getNetID() {
        return this.networkID;
    }

    public ArrayList<IPv4Address> getAddressList() {
        return this.addressList;
    }

    public ArrayList<IPv4HostAddress> getHostAddressList() { return this.hostAddresses;}

    public IPv4Subnet setAddressList(ArrayList<IPv4Address> addressList) {
        this.addressList = addressList;
        return this;
    }

    public void setHostAddresses(ArrayList<IPv4HostAddress> hostAddresses) {
        this.hostAddresses = hostAddresses;
        for(IPv4HostAddress h : hostAddresses)
            this.addressList.add(h);
    }

    public void setSubSubNets(ArrayList<IPv4Subnet> subSubNets) {
        this.subSubNets = subSubNets;
    }

    public IPv4SubnetMask getSubnetMask() {
        return this.subnetMask;
    }

    public IPv4Subnet setSubnetMask(IPv4SubnetMask subnetMask) {
        this.subnetMask = subnetMask;
        return this;
    }

    public IPv4HostAddress getMinHost() {
        int[] temp = this.getNetID().getIpv4Address().clone();
        temp[3] += 1;
        return new IPv4HostAddress(temp);
    }

    public IPv4HostAddress getMaxHost() {
        int[] temp = this.getBroadcast().getIpv4Address().clone();
        temp[3] -= 1;
        return new IPv4HostAddress(temp);
    }

    /**
     * &lt;pre&gt;
     * sets the remaining amount of free hosts for this subnet
     *
     * &#64;param subsubnet the subnet that has been validated and is now added
     * &lt;/pre&gt;
     */
    public void setRemainingAmountOfHosts(IPv4Subnet subsubnet) {
        long amountOfHosts = this.subnetMask.getMaxHosts();
        this.remainingAmountOfHosts = amountOfHosts - subsubnet.getSubnetMask().getMaxHosts();
    }

    public long getRemainingAmountOfHosts() {
        return remainingAmountOfHosts;
    }

    /**
     * &lt;pre&gt;
     * private class capable of building a IPv4Subnet, either by hand, of by name or by amount of hosts
     * &lt;/pre&gt;
     */
    public static class Builder {

        private IPv4SubnetMask subnetMask;
        private IPv4BroadcastAddress broadcastAddress;
        private IPv4NetworkID networkID;
        private String name;

        public IPv4Subnet.Builder subnetMask(IPv4SubnetMask subnetMask) {
            this.subnetMask = subnetMask;
            return this;
        }

        public IPv4Subnet.Builder name(String name){
            this.name = name;
            return this;
        }

        public IPv4Subnet.Builder broadcastAddress(IPv4BroadcastAddress address) {
            this.broadcastAddress = address;
            return this;
        }

        public IPv4Subnet.Builder networkID(IPv4NetworkID address) {
            this.networkID = address;
            return this;
        }

        public IPv4Subnet build(){
            return new IPv4Subnet(this);
        }

        /**
         * &lt;pre&gt;
         * method able to build a subnet by name.
         * Name notation goes as follows: networkID/prefix
         * example: "192.168.0.0/24"
         *
         * validation is done within the mehtod
         *
         * &#64;param name String notation of networkID/prefix("192.168.0.0/24")
         * &#64;return IPv4Subnet
         * &#64;throws SubnetBuildingError
         * &lt;/pre&gt;
         */
        public IPv4Subnet buildByName(String name) throws SubnetBuildingError {
            String temp[] = name.split("/");
            int[] id = Stream.of(temp[0].split("\\.")).mapToInt(Integer::parseInt).toArray();

            this.networkID = new IPv4NetworkID(id);

            try {
                this.subnetMask = new IPv4SubnetMask.Builder().buildByPrefix(Integer.parseInt(temp[1]));
            } catch (FalsePrefixExeption falsePrefixExeption) {
                falsePrefixExeption.printStackTrace();
            }

            if (!ipv4SubnetUtils.isValidNetID(id, subnetMask))
                throw new SubnetBuildingError("An invalid netID was detected: " + this.networkID);

            this.broadcastAddress = ipv4SubnetUtils.calcBroadcast(subnetMask, networkID);

            return build();
        }

        /**
         * &lt;pre&gt;
         * method able to build a subnet by amount of hosts.
         *
         * validation is done within the mehtod
         *
         * &#64;param netID int[] notation of a networkID
         * &#64;param hosts long amount of hosts, that need to fit in the subnet
         * &#64;return IPv4Subnet
         * &#64;throws SubnetBuildingError
         * &lt;/pre&gt;
         */
        public IPv4Subnet buildByAmountOfHosts(int[] netID, long hosts) throws SubnetBuildingError {

            try {
                this.subnetMask = new IPv4SubnetMask.Builder()
                        .buildByPrefix(ipv4SubnetUtils.calcPrefixByHosts(hosts));
            } catch (FalsePrefixExeption falsePrefixExeption) {
                falsePrefixExeption.printStackTrace();
            }
            if(ipv4SubnetUtils.isNetID(netID, subnetMask))
                this.networkID = new IPv4NetworkID(netID);

            this.broadcastAddress = ipv4SubnetUtils.calcBroadcast(subnetMask, networkID);
            if(!ipv4SubnetUtils.isBroadcast(broadcastAddress.getIpv4Address(), subnetMask))
                throw new SubnetBuildingError("A false broadcastAddress was detected: " + broadcastAddress);

            return build();
        }
    }
}

package it5.p04.fileadapter;

import org.mnm.ipv4.ipv4.IPv4Address;
import org.mnm.ipv4.ipv4.IPv4BroadcastAddress;
import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;
import org.mnm.ipv4.subnet.IPv4Subnet;
import org.mnm.ipv4.subnet.IPv4SubnetMask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 16/05/17.
 */
class FileWriterTest {
    IPv4Subnet subnet;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        subnet = new IPv4Subnet();
        List<IPv4Address> addressList = new ArrayList<>();
        for(int i = 1; i < 255; i++)
            addressList.add(new IPv4HostAddress(new int[]{192,168,0,i}));
        subnet
                .setName("TestSubnet")
                .setSubnetMask(new IPv4SubnetMask.Builder().buildByPrefix(24))
                .setAddressList(addressList)
                .setBroadcastAddress(new IPv4BroadcastAddress(new int[]{192,168,0,255}))
                .setNetworkID(new IPv4NetworkID(new int[]{192,168,0,0}));
    }

    @org.junit.jupiter.api.Test
    void write() throws Exception {
        FileWriter writer = new FileWriter("TestFile", subnet);
        writer.write();
    }

}
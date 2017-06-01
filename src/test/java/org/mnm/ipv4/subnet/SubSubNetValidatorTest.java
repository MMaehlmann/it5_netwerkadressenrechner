package org.mnm.ipv4.subnet;

import org.junit.jupiter.api.Test;
import org.mnm.ipv4.ipv4.IPv4HostAddress;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by martin on 01/06/17.
 */
class SubSubNetValidatorTest {

    @Test
    void isValid() throws SubnetBuildingError {
        IPv4Subnet subnet = new IPv4Subnet.Builder().buildByName("192.168.0.0/24");
        subnet.addHost(new IPv4HostAddress("192.168.0.129"));

        IPv4Subnet subsubnet = new IPv4Subnet.Builder().buildByName("192.168.0.128/30");

        SubSubNetValidator validator = new SubSubNetValidator(subnet, subsubnet);

        assertFalse(validator.isValid());

/*

        IPv4Subnet subnet2 = new IPv4Subnet.Builder().buildByName("192.168.0.0/24");

        IPv4Subnet subsubnet2 = new IPv4Subnet.Builder().buildByName("192.168.0.128/30");

        SubSubNetValidator validator2 = new SubSubNetValidator(subnet, subsubnet);

        assertTrue(validator.isValid());
*/
    }

}
package org.mnm.ipv4.ipv4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by martin on 01/06/17.
 */
class IPv4AddressTest {


    @Test
    void toIPv6String() {
        IPv4Address a = new IPv4HostAddress("192.168.255.255");
        assertEquals("2002:0000:0000:0000:0000:0000:c0a8:ffff", a.toIPv6String());
    }

}
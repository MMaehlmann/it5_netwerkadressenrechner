package org.mnm.ipv4.ipv4;

import org.mnm.ipv4.subnet.IPv4SubnetUtils;

import java.util.stream.Stream;

/**
 * &lt;pre&gt;
 * Programatic representation of an IPv4BroadcastAddress
 * extends IPv4Address
 *
 * Created by martin on 15/04/17.
 * &lt;/pre&gt;
 */
public class IPv4BroadcastAddress extends IPv4Address {

    public IPv4BroadcastAddress(int[] ipv4Address) {
        super();
        if (IPv4SubnetUtils.isValidIP(ipv4Address))
            super.setIpv4Address(ipv4Address);
    }

    public IPv4BroadcastAddress(String ipv4Address) {
        super();
        super.setIpv4Address(Stream.of(ipv4Address.split("\\.")).mapToInt(Integer::parseInt).toArray());
    }
}

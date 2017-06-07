package org.mnm.ipv4.ipv4;

import org.mnm.ipv4.subnet.IPv4SubnetUtils;

import java.util.stream.Stream;

/**
 * &lt;pre&gt;
 * Programatic representation of an IPv4AHostAddress
 * extends IPv4Address
 *
 * Created by martin on 15/04/17.
 * &lt;/pre&gt;
 */
public class IPv4HostAddress extends IPv4Address {

    public IPv4HostAddress(int[] ipv4Address) {
        this.setIpv4Address(ipv4Address);
    }

    public IPv4HostAddress(String ipv4Address) {
        this.setIpv4Address(Stream.of(ipv4Address.split("\\.")).mapToInt(Integer::parseInt).toArray());
        if (!IPv4SubnetUtils.isValidIP(this.getIpv4Address()))
            throw new FalseIPExeption();
    }
}

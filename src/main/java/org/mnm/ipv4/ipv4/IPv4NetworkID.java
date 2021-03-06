package org.mnm.ipv4.ipv4;

import java.util.stream.Stream;
/**
 * &lt;pre&gt;
 * Programatic representation of an IPv4NetworkID
 * extends IPv4Address
 *
 * Created by martin on 15/04/17.
 * &lt;/pre&gt;
 */
public class IPv4NetworkID extends IPv4Address {

    public IPv4NetworkID(int[] ipv4Address) {
        this.setIpv4Address(ipv4Address);
    }

    public IPv4NetworkID(String ipv4Address) {
        this.setIpv4Address(Stream.of(ipv4Address.split("\\.")).mapToInt(Integer::parseInt).toArray());
    }
}

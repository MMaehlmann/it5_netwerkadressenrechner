package org.mnm.ipv6.ipv6;

import org.mnm.ipv6.subnet.ipv6SubnetUtils;

import java.util.stream.Stream;

/**
 * Created by martin on 17/05/17.
 */
public class IPv6HostAddress extends IPv6Address{


    public IPv6HostAddress (int[] ipv6Address) {
        setIpv6Address(ipv6Address);
    }

    public IPv6HostAddress (String ipv6Address) {
        if(ipv6SubnetUtils.isValidIP(ipv6Address))

        setIpv6Address(Stream.of(ipv6Address.split(":"))
            .mapToInt(i -> Integer.parseInt( i, 16 )).toArray());
    }
}
